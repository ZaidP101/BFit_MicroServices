package zaid.patel.BFit.gateaway.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {
        log.info("Calling User Service for {}", userId);

        return userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new RuntimeException("User not found: " + userId));
                    } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new RuntimeException("Invalid request for user: " + userId));
                    } else {
                        return Mono.error(new RuntimeException("Unexpected error for user: " + userId));
                    }
                });
    }

    public Mono<UserResponseDto> registerUser(RegisterRequestDto requestDto) {
        log.info("Calling user service from {},", requestDto.getEmail());
        return userServiceWebClient.post()
                .uri("/api/users/register")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(UserResponseDto.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new RuntimeException("Bad Request: " + e.getMessage()));
                    } else {
                        return Mono.error(new RuntimeException("Unexpected error for user: " + e.getMessage()));
                    }
                });
    }
}
