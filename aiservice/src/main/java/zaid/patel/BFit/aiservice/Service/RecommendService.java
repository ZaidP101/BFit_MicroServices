package zaid.patel.BFit.aiservice.Service;

import zaid.patel.BFit.aiservice.Entity.Recommendations;

import java.util.List;
import java.util.Optional;

public interface RecommendService {
    List<Recommendations> getUserRecommendation(String userId);

    Optional<Recommendations> getRecommendations(String activityId);
}
