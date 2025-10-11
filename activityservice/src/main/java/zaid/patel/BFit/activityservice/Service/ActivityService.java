package zaid.patel.BFit.activityservice.Service;

import zaid.patel.BFit.activityservice.DTO.ActivityRequestDto;
import zaid.patel.BFit.activityservice.DTO.ActivityResponseDto;

public interface ActivityService {
    ActivityResponseDto trackActivity(ActivityRequestDto request);
}
