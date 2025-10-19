package zaid.patel.BFit.aiservice.Service.Implements;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zaid.patel.BFit.aiservice.Entity.Activity;
import zaid.patel.BFit.aiservice.Entity.Recommendations;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ActivityAiService {
    private final GeminiService geminiService;

    public Recommendations generateRecommendations(Activity activity){
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getRecommendations(prompt);
        log.info("Response from AI", aiResponse);
        return processedResponse(activity, aiResponse);
    }

    private Recommendations processedResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```", "")
                    .trim();
            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories:");

            List<String> improvement = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractsafetyGuideline(analysisJson.path("safety"));

            return Recommendations.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().toString())
                    .recommendation(fullAnalysis.toString())
                    .improvements(improvement)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendations createDefaultRecommendation(Activity activity) {
        return Recommendations.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType().toString())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider Consulting a Professional"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Lisen to tour body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractsafetyGuideline(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if (safetyNode.isArray()){
            safetyNode.forEach(item ->{
                safety.add(item.asText());
            });
        }
        return safety.isEmpty() ?
                Collections.singletonList("Follow general Safety Guidelines") :
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionNode) {
        List<String> suggestions = new ArrayList<>();
        if (suggestionNode.isArray()){
            suggestionNode.forEach(suggestion ->{
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description ").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No Suggestions provided") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementNode) {
        List<String> improvements = new ArrayList<>();
        if (improvementNode.isArray()){
            improvementNode.forEach(improvement ->{
                String area = improvement.path("area").asText();
                String details = improvement.path("details ").asText();
                improvements.add(String.format("%s: %s", area, details));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No Recommendations provided") :
                improvements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()){
            fullAnalysis.append(prefix)
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                            Analyze this fitness activity and provide recommendations in the following EXACT JSON Formate:
                            {
                                "analysis": {
                                "overall": "Overall analysis here",
                                "pace": "Pace analysis here",
                                "heartRate": "Heart rate analysis here",
                                "caloriesBurned": "Calories analysis here"
                            },
                            "improvements": [
                                {
                                    "area": "Area name",
                                    "recommendation": "Detailed recommendation"
                                }
                            ],
                            "suggestions":[
                                {
                                    "workout": "Workout name",
                                    "description": "Detailed workout description"
                                }
                            ],
                        
                            "safety": [
                                "Safety point 1",
                                "Safety point 2"
                            ]
                        }
                        Analyze this activity:
                        Activity Type: %s
                        Duration: %d minutes
                        Calories Burned: %d
                        Additional Metrics: %s
                        Provide detailed analysis focusing on performance, improvements, next workout suggestions and safty guidlines.
                        Ensure the response follows the EXACT JSON format shown above.
                        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}
