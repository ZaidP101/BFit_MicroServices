package zaid.patel.BFit.aiservice.Service.Implements;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zaid.patel.BFit.aiservice.Entity.Recommendations;
import zaid.patel.BFit.aiservice.Repository.RecommendRepository;
import zaid.patel.BFit.aiservice.Service.RecommendService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecommendServiceImp implements RecommendService {
    private final RecommendRepository recommendRepository;

    @Override
    public List<Recommendations> getUserRecommendation(String userId) {
        return recommendRepository.findByUserId(userId);
    }

    @Override
    public Optional<Recommendations> getRecommendations(String activityId) {
        try {
            return Optional.ofNullable(recommendRepository.findByActivityId(activityId));
        } catch (Exception e) {
            System.out.println("Error fetching recommendation for activity: " + activityId);
            return Optional.empty();
        }
    }
}
