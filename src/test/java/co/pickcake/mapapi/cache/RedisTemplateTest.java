package co.pickcake.mapapi.cache;


import co.pickcake.test.container.AbstractIntegrationContainerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import java.util.Map;


public class RedisTemplateTest extends AbstractIntegrationContainerTest {

    public static final String TEST_KEY = "testHashKey";
    @Autowired private RedisTemplate redisTemplate;
    /* FIXME */
    @TestConfiguration
    public static class TestRedisConfig {
        /* test 용 config, redis 를 바꿔치기 해야하여 이렇게 별도로 생성함 */
        @Value("${spring.data.test.redis.host}")
        private String redisHost;

        @Value("${spring.data.test.redis.port}")
        private int redisPort;

        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory(redisHost, redisPort);
        }
        @Bean
        public RedisTemplate<String, Object> redisTemplate() {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory());
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashValueSerializer(new StringRedisSerializer());
            return redisTemplate;
        }

    }


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
