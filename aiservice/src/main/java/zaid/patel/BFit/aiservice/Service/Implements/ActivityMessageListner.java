package zaid.patel.BFit.aiservice.Service.Implements;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import zaid.patel.BFit.aiservice.Entity.Activity;
import zaid.patel.BFit.aiservice.Entity.Recommendations;
import zaid.patel.BFit.aiservice.Repository.RecommendRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    private final ActivityAiService activityAiService;
    private final RecommendRepository recommendRepository;

    @KafkaListener(topics = "${kafka.topic.name}")
    public void processActivity(Activity activity){
        log.info("Received Activity for processing: {}",activity.getUserId());
        Recommendations generatedRecommendations =  activityAiService.generateRecommendations(activity);
        recommendRepository.save(generatedRecommendations);
    }
}
