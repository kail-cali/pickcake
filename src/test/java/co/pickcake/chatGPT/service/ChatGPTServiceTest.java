package co.pickcake.chatGPT.service;

import co.pickcake.recommend.chatGPT.query.RecommendEvent;
import co.pickcake.recommend.chatGPT.query.RecommendQuery;
import co.pickcake.recommend.chatGPT.query.RecommendType;
import co.pickcake.recommend.chatGPT.query.event.RecommencementSpecific;
import co.pickcake.recommend.chatGPT.query.event.RecommendEventType;
import co.pickcake.recommend.chatGPT.query.event.SpecialEvent;
import co.pickcake.recommend.chatGPT.query.item.RecommendItem;
import co.pickcake.recommend.response.ChatRecommendResponse;
import co.pickcake.recommend.service.ChatGPTService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatGPTServiceTest {
    /* Redis 연결 없이 API CALL 테스트 */
    @Autowired
    private ChatGPTService chatGPTService;

    /* TODO 현 버전 openai 코드 만료, 추후 프로젝트 위해 연장 필요 */
    @Test
    @DisplayName("v1 api call [success]: llm query dsl로 openapi 에 요청 시 응답 확인")
    void v1ChatGPTRecommendTest() {
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
        //when
        ChatRecommendResponse response = chatGPTService.requestRecommendBart(query);
        //then
        Assertions.assertThat(response.getChooses().size()).isGreaterThan(0);
        System.out.println("response.getChooses().getFirst().getContents() = " + response.getChooses().getFirst().getContents());
    }

}