package zaid.patel.BFit.activityservice.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import zaid.patel.BFit.activityservice.Entity.Activity;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
}
