package co.pickcake.reservedomain.searchcake.cache;


import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchCakeRedisService {

    private static final String CACHE_KEY = "SEARCH_CAKE";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }
    private String serializeRecommendResponse(CakeSimpleSearch response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response);
    }
    private CakeSimpleSearch deserializeRecommendResponse(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, CakeSimpleSearch.class);
    }

    private String getSubKey(CakeSimpleSearch search) {
        return search.getItemId().toString();
    }

    public void save(CakeSimpleSearch search) {
        if (Objects.isNull(search) || Objects.isNull(search.getItemId())) {
            log.info("[SearchCake Redis Saved failed]");
            return;
        }
        try {
            hashOperations.put(CACHE_KEY,
                getSubKey(search),
                serializeRecommendResponse(search));
            log.info("[SearchCake Redis Save success] from {} to {}", search.getProfile().getStorePath(), serializeRecommendResponse(search));
        } catch (Exception e ) {
            log.error("[SearchCake Redis Save failed] {}", e.getMessage());
        }
    }

    public void update(CakeSimpleSearch search) {
        try {
            hashOperations.put(CACHE_KEY,
                    getSubKey(search),
                    serializeRecommendResponse(search)
                    );
            log.info("[SearchCake Redis Update success]");

        } catch (Exception e) {
            log.error("[SearchCake Redis Update failed] {}", e.getMessage());
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
    }

    public List<CakeSimpleSearch> findAll() {
        try {
            ArrayList<CakeSimpleSearch> list = new ArrayList<>();
            for (String value: hashOperations.entries(CACHE_KEY).values()) {
                CakeSimpleSearch search = deserializeRecommendResponse(value);
                list.add(search);
                }
                return list;
            } catch (Exception e){
                log.error("[SearchCake Redis Find Failed] {}", e.getMessage());
                return Collections.emptyList();
        }
    }


}
