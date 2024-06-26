package co.pickcake.recommend.service;

import co.pickcake.recommend.cache.ChatCPTRedisService;
import co.pickcake.recommend.chatGPT.query.RecommendQuery;
import co.pickcake.recommend.request.ChatGptV1Request;
import co.pickcake.recommend.response.ChatRecommendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGPTService implements GenerateQuestion  {

    private final ChatGPTQueryBuilderService chatGPTQueryBuilderService;
    private final RestTemplate restTemplate;
    private final ChatCPTRedisService chatCPTRedisService;

    @Value("${openapi.api.key}")
    private String openapiApiKey;

    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 1,
            backoff = @Backoff(delay = 1000)
    )
    public ChatRecommendResponse requestRecommendBart(RecommendQuery query) {

        ChatRecommendResponse byQuery = chatCPTRedisService.findByQuery(query);
        if (byQuery.getGivenId() != null) {
            log.info("[Chat GPT recommend] get By Redis");
            return byQuery;
        }
        URI uri = chatGPTQueryBuilderService.builderByDefaultQuery(query);

        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("User-Agent", "hail-mac");//Set the header for each request
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + openapiApiKey);
            return execution.execute(request, body);
        });
        ChatGptV1Request request = chatGPTQueryBuilderService.builderForRequest(query);
        ChatRecommendResponse response = restTemplate.postForObject(uri, request, ChatRecommendResponse.class);
        // save redis
        chatCPTRedisService.save(response, query);
        return response;
    }
}
