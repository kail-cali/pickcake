package co.pickcake.mapapi.cache;


import co.pickcake.mapapi.response.ShopRecommendResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendRedisService {

    private static final String CACHE_KEY = "RECOMMEND_MAP";

    private final RedisTemplate<String ,Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String , String ,String > hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private String serializeRecommendResponse(ShopRecommendResponse response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response);
    }
    private ShopRecommendResponse deserializeRecommendResponse(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, ShopRecommendResponse.class);
    }

    private String makeSubKey(ShopRecommendResponse response) {
        return response.getAddressName() + "_" + response.getAddressName();
    }

    public void save(ShopRecommendResponse response) {
        if (Objects.isNull(response) ||
        Objects.isNull(response.getAddressName()) ||
        Objects.isNull(response.getShopName())) {
            log.info("Required API request have to be valid Value");
            return;
        }
        try {
            hashOperations.put(CACHE_KEY,
                                makeSubKey(response),
                                serializeRecommendResponse(response));
        } catch (Exception e) {
            log.error("[RecommendRedisService ShopRecommend save Error] {}", e.getMessage() );
        }
    }
    public void delete(String hashSubKeys) {
        hashOperations.delete(CACHE_KEY, hashSubKeys);
    }

    public List<ShopRecommendResponse> findTAKAOMapApiRequest() {
        try {
            List<ShopRecommendResponse> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                ShopRecommendResponse responseCached = deserializeRecommendResponse(value);
                list.add(responseCached);
            }
            return list;

        } catch (Exception e) {
            log.error("[RecommendRedisService ShopRecommend Get cached data Error] {}", e.getMessage() );
            return Collections.emptyList();
        }
    }

    public List<ShopRecommendResponse> findAll() {
        try {
            List<ShopRecommendResponse> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                ShopRecommendResponse responseCached = deserializeRecommendResponse(value);
                list.add(responseCached);
            }
            return list;

        } catch (Exception e) {
            log.error("[RecommendRedisService ShopRecommend Get cached data Error] {}", e.getMessage() );
            return Collections.emptyList();
        }
    }



}
