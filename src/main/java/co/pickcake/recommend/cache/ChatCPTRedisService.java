package co.pickcake.recommend.cache;

import co.pickcake.recommend.chatGPT.query.RecommendQuery;
import co.pickcake.recommend.response.ChatRecommendResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatCPTRedisService {

    private static final String CACHE_KEY = "CHAT_KEY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private String serializeRecommendResponse(ChatRecommendResponse response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response);
    }
    private ChatRecommendResponse deserializeRecommendResponse(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, ChatRecommendResponse.class);
    }

    private String getSubKey(RecommendQuery query) {
        return query.toString();
    }

    public void save(ChatRecommendResponse response, RecommendQuery query) {
        if (Objects.isNull(response) || Objects.isNull(query)) {
            log.info("[ChatGTP Redis Saved failed]");
            return;
        }
        try {
            hashOperations.put(CACHE_KEY,
                    getSubKey(query),
                    serializeRecommendResponse(response));
            log.info("[SearchCake Redis Save success]");
        } catch (Exception e ) {
            log.error("[SearchCake Redis Save failed] {}", e.getMessage());
        }
    }
    public void delete(RecommendQuery query) {
        hashOperations.delete(CACHE_KEY, getSubKey(query));
    }

    public ChatRecommendResponse findByQuery(RecommendQuery query) {
        try {
            String result = hashOperations.get(CACHE_KEY, getSubKey(query)); // 여기서 null 내려주면 아래 catch
            ChatRecommendResponse response = deserializeRecommendResponse(result);
            return response;
        } catch (Exception e) {
            log.error("[] {}", e.getMessage());
            return new ChatRecommendResponse();
        }
    }
}
