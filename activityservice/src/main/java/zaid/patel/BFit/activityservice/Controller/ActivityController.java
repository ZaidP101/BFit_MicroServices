package zaid.patel.BFit.activityservice.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zaid.patel.BFit.activityservice.DTO.ActivityRequestDto;
import zaid.patel.BFit.activityservice.DTO.ActivityResponseDto;
import zaid.patel.BFit.activityservice.Service.ActivityService;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponseDto> trackActivity(@RequestBody ActivityRequestDto request){
        return ResponseEntity.ok(activityService.trackActivity(request));
    }
}
