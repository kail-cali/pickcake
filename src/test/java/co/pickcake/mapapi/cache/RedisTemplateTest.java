package co.pickcake.mapapi.cache;


import co.pickcake.test.container.AbstractIntegrationContainerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;


import java.util.Map;

@SpringBootTest
public class RedisTemplateTest extends AbstractIntegrationContainerTest {

    public static final String TEST_KEY = "testHashKey";
    @Autowired private RedisTemplate redisTemplate;

    @Test
    @DisplayName("redis Template 검증[success] key, value 가 제대로 값이 들어가는 지 테스트")
    void redisTemplateTest() {
        //given
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //when
        valueOperations.set("testKey", "testValue");
        //then
        Assertions.assertThat(valueOperations.get("testKey").toString()).isEqualTo("testValue");
    }

    @Test
    @DisplayName("redis Template 검증[success] set operation 정상 동작 테스트")
    void redisSetOperationTest() {
        //given
        SetOperations setOperations = redisTemplate.opsForSet();
        String testKey = "testSetKey";
        //when
        setOperations.add(testKey, "a", "e", "i", "o", "u");
        //then
        Assertions.assertThat(setOperations.size(testKey)).isEqualTo(5);
    }

    @Test
    @DisplayName("redis Template 검증[success] hash operation 정상 동작 테스트")
    void redisHashOperationTest() {
        //given
        HashOperations hashOperations = redisTemplate.opsForHash();
        String TEST_KEY = "testHashKey";
        //when
        hashOperations.put(TEST_KEY, "subKey", "testValue");
        //then
        Assertions.assertThat(hashOperations.get(TEST_KEY, "subKey")).isEqualTo("testValue");
        Map entries = hashOperations.entries(TEST_KEY);
        Assertions.assertThat(entries.keySet()).contains("subKey");
        Assertions.assertThat(entries.values()).contains("testValue");

        Assertions.assertThat(hashOperations.size(TEST_KEY)).isEqualTo(entries.size());
    }


}
