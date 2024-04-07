package co.pickcake.recommend.service;

import co.pickcake.recommend.chatGPT.query.RecommendQuery;
import co.pickcake.recommend.request.ChatGptV1Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGPTQueryBuilderService {
    private static String CHAT_GPT_LEGACY_TEXT_GENERATION_ENDPOINT = "https://api.openai.com/v1/completions"; // POST
    private static String LEGACY_MODEL = "gpt-3.5-turbo-instruct";
    private static final Integer maxTokens = 64;

    public URI builderByDefaultQuery(RecommendQuery query) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(CHAT_GPT_LEGACY_TEXT_GENERATION_ENDPOINT);
        uriBuilder.queryParam("model", LEGACY_MODEL);
        uriBuilder.queryParam("prompt", query.toString());
        uriBuilder.queryParam("max_tokens", maxTokens);
        return uriBuilder.build().encode().toUri();
    }

    public ChatGptV1Request builderForRequest(RecommendQuery query) {
        return ChatGptV1Request.builder()
                .model(LEGACY_MODEL)
                .prompt(query.toString())
                .maxTokens(maxTokens)
                .build();
    }




}
