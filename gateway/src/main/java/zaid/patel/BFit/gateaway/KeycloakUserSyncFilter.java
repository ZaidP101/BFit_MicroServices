package zaid.patel.BFit.gateaway;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import zaid.patel.BFit.gateaway.User.RegisterRequestDto;
import zaid.patel.BFit.gateaway.User.UserService;

import java.text.ParseException;

@Component
@Slf4j
@AllArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {
    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        RegisterRequestDto requestDto = getUserDetails(token);
        if(userId == null){
            userId = requestDto.getKeycloakId();
        }
        if (userId !=null && token != null){
            String finalUserId = userId;
            return userService.validateUser(userId)
                    .flatMap(exist -> {
                        if(!exist){
                            if(requestDto != null){
                                return userService.registerUser(requestDto)
                                        .then(Mono.empty());
                            } else {
                                return Mono.empty();
                            }
                        }else {
                            log.info("User Already Exist, Skipping sync");
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(() -> {
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-ID", finalUserId)
                                .build();
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }));
        }

        return null;
    }

    private RegisterRequestDto getUserDetails(String token) {
        try {
            String tokenWithoutBearer = token.replace("Bearer", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            RegisterRequestDto requesrDto = new RegisterRequestDto();
            requesrDto.setEmail(claimsSet.getStringClaim("email"));
            requesrDto.setKeycloakId(claimsSet.getStringClaim("sub"));
            requesrDto.setFirstName(claimsSet.getStringClaim("given_name"));
            requesrDto.setLastName(claimsSet.getStringClaim("family_name"));
            requesrDto.setPassword("dummy@123");
            return requesrDto;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
