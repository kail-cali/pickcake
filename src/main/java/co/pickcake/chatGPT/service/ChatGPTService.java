package co.pickcake.chatGPT.service;

import co.pickcake.chatGPT.cache.ChatCPTRedisService;
import co.pickcake.chatGPT.query.RecommendQuery;
import co.pickcake.chatGPT.request.ChatGptV1Request;
import co.pickcake.chatGPT.response.ChatRecommendResponse;
import co.pickcake.recommend.service.GenerateQuestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

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

        Optional<ChatRecommendResponse> byQuery = chatCPTRedisService.findByQuery(query);
        if (byQuery.isPresent()) {
            log.info("[Chat GPT recommend] get By Redis");
            return byQuery.get();
        }
        // use redis -> 추후에 mongo db 교체
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
