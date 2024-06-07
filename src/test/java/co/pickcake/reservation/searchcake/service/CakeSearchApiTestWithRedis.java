package co.pickcake.reservation.searchcake.service;

import co.pickcake.policies.filename.policy.FileNamePolicy;
import co.pickcake.reservation.searchcake.cache.SearchCakeRedisService;
import co.pickcake.reservation.searchcake.dto.CakeProfileImageDto;
import co.pickcake.reservation.searchcake.dto.CakeSimpleSearch;
import co.pickcake.test.container.AbstractIntegrationContainerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;



@AutoConfigureMockMvc
public class CakeSearchApiTestWithRedis extends AbstractIntegrationContainerTest {
    /* redis 설정 시 유닛 테스트는 이곳에서 함 */

    @Autowired private SearchCakeRedisService searchCakeRedisService;

    @Autowired private FileNamePolicy fileNamePolicy;
    @Autowired private ObjectMapper objectMapper;

    /* FIXME
    *   통합 테스트 환경 테스트에서 Configuration 을 TEST 용으로 override 해야하는데
    *   Annotation 이 상속보다 먼저 적용되면서 제대로 TestConfiguration 적용되지 않는 문제
    *   우선은 해당 config 를 클래스안에 static 으로 설정하여 해결했으나 통합 테스트 때마다 코드를 이렇게 늘릴 수 는 없기 떄문에
    *   수정이 필요. */
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
    @BeforeEach
    void setUp() {
        searchCakeRedisService.findAll().forEach(response -> searchCakeRedisService.delete(response.getItemId()));
    }

    @Test
    @DisplayName("uuid Serialize Test[success]:")
    void serializeUUID() throws JsonProcessingException {
        //given
        String imageOrigName = "XXX-TEST-XXX.1.png";
        String storeName = fileNamePolicy.concatGenExt(imageOrigName);
        CakeProfileImageDto profile1 = new CakeProfileImageDto();
        profile1.setStorePath(storeName);
        CakeSimpleSearch item1 = CakeSimpleSearch.builder()
                .itemId(1L)
                .brand("신라 호텔")
                .profile(profile1)
                .name("화이트 홀레데이 케이크")
                .price(1000000)
                .build();
        //when

        String written = objectMapper.writeValueAsString(item1);
        CakeSimpleSearch search = objectMapper.readValue(written, CakeSimpleSearch.class);

        //then
        Assertions.assertThat(search.getProfile().getStorePath()).isEqualTo(storeName);

    }

    @Test
    @DisplayName("상품 조회 redis test[success]: redis에 정상적으로 겂이 저장되는 지 테스트")
    void redisSaveTest() {
        //given
        String imageOrigName = "XXX-TEST-XXX.1.png";
        String storeName = fileNamePolicy.concatGenExt(imageOrigName);
        CakeProfileImageDto profile1 = new CakeProfileImageDto();
        profile1.setStorePath(storeName);
        CakeSimpleSearch item1 = CakeSimpleSearch.builder()
                .itemId(1L)
                .brand("신라 호텔")
                .profile(profile1)
                .name("화이트 홀레데이 케이크")
                .price(1000000)
                .build();
        //when
        searchCakeRedisService.save(item1);
        List<CakeSimpleSearch> response = searchCakeRedisService.findAll();
        //then
        Assertions.assertThat(response.size()).isEqualTo(1);
        Assertions.assertThat(response.getFirst().getPrice()).isEqualTo(1000000);
        Assertions.assertThat(response.getFirst().getBrand()).isEqualTo("신라 호텔");
        Assertions.assertThat(response.getFirst().getProfile().getStorePath()).isNotEmpty();
        Assertions.assertThat(response.getFirst().getProfile().getStorePath()).isEqualTo(storeName);
    }

    @Test
    @DisplayName("상품 조회 redis test[fail]: redis 에 잘못된 값을 넣었을 때 저장이 되지 않아야 한다.")
    void redisSaveFailTest() {
        //given
        CakeSimpleSearch item1 = CakeSimpleSearch.builder()
                .brand("신라 호텔")
                .name("화이트 홀레데이 케이크")
                .price(1000000)
                .build();
        //when
        searchCakeRedisService.save(item1);
        List<CakeSimpleSearch> response = searchCakeRedisService.findAll();
        //then
        Assertions.assertThat(response.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("상품 조회 redis test[success]: redis에 저장된 값을 삭제할 수 있어야 한다.")
    void redisDeleteTest() {
        //given
        CakeProfileImageDto profile1 = new CakeProfileImageDto();
        profile1.setStorePath("XXX-TEST-XXX.1.png");
        CakeSimpleSearch item1 = CakeSimpleSearch.builder()
                .itemId(1L)
                .brand("신라 호텔")
                .profile(profile1)
                .name("화이트 홀레데이 케이크")
                .price(1000000)
                .build();
        //when
        searchCakeRedisService.save(item1);
        List<CakeSimpleSearch> response = searchCakeRedisService.findAll();
        Assertions.assertThat(response.size()).isEqualTo(1);
        searchCakeRedisService.delete(1L);
        List<CakeSimpleSearch> responseAfter = searchCakeRedisService.findAll();
        //then
        Assertions.assertThat(responseAfter.size()).isEqualTo(0);



    }

}
