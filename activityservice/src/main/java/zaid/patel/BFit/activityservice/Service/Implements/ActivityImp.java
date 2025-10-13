package zaid.patel.BFit.activityservice.Service.Implements;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zaid.patel.BFit.activityservice.DTO.ActivityRequestDto;
import zaid.patel.BFit.activityservice.DTO.ActivityResponseDto;
import zaid.patel.BFit.activityservice.Entity.Activity;
import zaid.patel.BFit.activityservice.Repository.ActivityRepository;
import zaid.patel.BFit.activityservice.Service.ActivityService;

@Service
@RequiredArgsConstructor
public class ActivityImp implements ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidartionService userValidartionService;

    @Override
    public ActivityResponseDto trackActivity(ActivityRequestDto request) {
        boolean isValid = userValidartionService.validateUser(request.getUserId());
        if(!isValid){
            throw new RuntimeException("Invalid User: "+ request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponseDto mapToResponse(Activity activity) {
        ActivityResponseDto response = new ActivityResponseDto();
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }
}
