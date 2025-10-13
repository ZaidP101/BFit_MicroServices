package zaid.patel.BFit.aiservice.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import zaid.patel.BFit.aiservice.Entity.Recommendations;

import java.util.List;

@Repository
public interface RecommendRepository extends MongoRepository<Recommendations, String> {
    List<Recommendations> findByUserId(String userId);

    Recommendations findByActivityId(String activityId);
}
