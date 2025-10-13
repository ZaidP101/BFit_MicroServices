package zaid.patel.BFit.aiservice.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zaid.patel.BFit.aiservice.Entity.Recommendations;
import zaid.patel.BFit.aiservice.Service.RecommendService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendations")
public class RecommendationController {
    private final RecommendService recommendService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendations>> getUserRecommendations(@PathVariable String userId){
        return ResponseEntity.ok(recommendService.getUserRecommendation(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Optional<Recommendations>> getRecommendation(@PathVariable String activityId){
        return ResponseEntity.ok(recommendService.getRecommendations(activityId));
    }
}
