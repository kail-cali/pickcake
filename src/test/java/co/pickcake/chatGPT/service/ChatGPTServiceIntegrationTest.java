package co.pickcake.chatGPT.service;

import co.pickcake.recommend.cache.ChatCPTRedisService;
import co.pickcake.recommend.chatGPT.query.RecommendEvent;
import co.pickcake.recommend.chatGPT.query.RecommendQuery;
import co.pickcake.recommend.chatGPT.query.RecommendType;
import co.pickcake.recommend.chatGPT.query.event.RecommencementSpecific;
import co.pickcake.recommend.chatGPT.query.event.RecommendEventType;
import co.pickcake.recommend.chatGPT.query.event.SpecialEvent;
import co.pickcake.recommend.chatGPT.query.item.RecommendItem;
import co.pickcake.recommend.response.ChatRecommendResponse;
import co.pickcake.recommend.response.Chooses;
import co.pickcake.test.container.AbstractIntegrationContainerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;

@AutoConfigureMockMvc
class ChatGPTServiceIntegrationTest extends AbstractIntegrationContainerTest {

    @Autowired private ChatCPTRedisService chatCPTRedisService;

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
    @DisplayName("chat gpt api reids test[success]: redis 에 정상적으로 값이 저장되는 지 테스트")
    void redisSaveTest() {
        //given
        RecommendType item = RecommendType.builder()
                .recommendItem(RecommendItem.CAKE)
                .build();

        RecommendEvent eventSpecial = RecommendEvent.builder()
                .eventType(RecommendEventType.SPECIAL)
                .specific(RecommencementSpecific.builder()
                        .specialEvent(SpecialEvent.VALENTINE).build()
                )
                .build();

        RecommendQuery query = RecommendQuery.builder()
                .queryId(10L)
                .recommendType(item)
                .recommendEvent(eventSpecial)
                .build();
        Chooses choose1 = Chooses.builder().reason("").contents("레드벨벳 케이크").index(1).build();
        ArrayList<Chooses> list = new ArrayList<>();
        list.add(choose1);
        ChatRecommendResponse responseExpected = ChatRecommendResponse.builder()
                .givenId("testuuid")
                .objectType("chat.completion")
                .created(100D)
                .chooses(list)
                .build();

        //when
        chatCPTRedisService.save(responseExpected, query);
        ChatRecommendResponse responseActual = chatCPTRedisService.findByQuery(query);
        //then
        Assertions.assertThat(responseActual.getGivenId()).isNotNull();
        Assertions.assertThat(responseActual.getChooses().getFirst().getContents()).isEqualTo("레드벨벳 케이크");

        //afterEach
        chatCPTRedisService.delete(query);
    }



}