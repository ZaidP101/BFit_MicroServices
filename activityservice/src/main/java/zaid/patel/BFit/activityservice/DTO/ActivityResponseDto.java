package zaid.patel.BFit.activityservice.DTO;

import lombok.Data;
import zaid.patel.BFit.activityservice.Entity.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityResponseDto {
    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
