package zaid.patel.BFit.activityservice.Service.Implements;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@AllArgsConstructor
@Slf4j
public class UserValidartionService {
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId){
        try {
            log.info("Calling User Service for {}", userId);
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate",userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        }catch (WebClientResponseException e){
            e.printStackTrace();
        }
        return false;
    }
}
