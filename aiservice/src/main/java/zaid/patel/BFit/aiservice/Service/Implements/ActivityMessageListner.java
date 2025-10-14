package zaid.patel.BFit.aiservice.Service.Implements;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import zaid.patel.BFit.aiservice.Entity.Activity;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    private final ActivityAiService activityAiService;

    @KafkaListener(topics = "${kafka.topic.name}")
    public void processActivity(Activity activity){
        log.info("Received Activity for processing: {}",activity.getUserId());
        activityAiService.generateRecommendations(activity);
    }
}
