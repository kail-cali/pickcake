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
import co.pickcake.fake.UnitTestMockWebController;

import co.pickcake.recommend.service.ChatGPTQueryBuilderService;
import co.pickcake.recommend.service.ChatGPTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@WebMvcTest(UnitTestMockWebController.class)
@AutoConfigureMockMvc
class ChatGPTServiceUnitTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private ChatGPTService chatGPTService;
    @MockBean private ChatGPTQueryBuilderService chatGPTQueryBuilderService;
    @MockBean private RestTemplate restTemplate;
    @MockBean private ChatCPTRedisService chatCPTRedisService;


    @BeforeEach
    void setup() {

        chatGPTService = new ChatGPTService(chatGPTQueryBuilderService, restTemplate, chatCPTRedisService);

    }


    @Test
    @DisplayName("chatGPT api call unit test[success]: Redis, api 호출 관련 비즈니스 로직 검증, 만약 redis에 데이터가 있다면 런타임 " +
            "분기를 타고 redis 에 있는 값을 내려주는 지 테스트  ")
    void getRedis() {
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
        Mockito.when(chatCPTRedisService.findByQuery(query)).thenReturn(responseExpected);
        ChatRecommendResponse responseActual = chatGPTService.requestRecommendBart(query);
        //then
        assertThat(responseActual.getGivenId()).isEqualTo(responseExpected.getGivenId());
        assertThat(responseActual.getChooses().size()).isEqualTo(responseExpected.getChooses().size());
        assertThat(responseActual.getChooses().getFirst().getContents())
                .isEqualTo(responseExpected.getChooses().getFirst().getContents());


    }

}