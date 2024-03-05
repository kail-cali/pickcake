package co.pickcake.chatGPT.query;

import co.pickcake.chatGPT.query.event.RecommencementSpecific;
import co.pickcake.chatGPT.query.event.RecommendEventType;
import co.pickcake.chatGPT.query.event.SpecialEvent;
import co.pickcake.chatGPT.query.item.RecommendItem;
import co.pickcake.fake.MockWebController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MockWebController.class)
class RecommendQueryTest {

    @Autowired private MockMvc mockMvc;
    @Test
    @DisplayName("")
    void testMainBusinessLogic() {
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

        RecommendQuery query1 = RecommendQuery.builder()
                .recommendType(item)
                .build();
        RecommendQuery query2 = RecommendQuery.builder()
                .recommendType(item)
                .recommendEvent(eventSpecial)
                .build();
        //when
        String queryString1 = query1.toString();
        String queryString2 = query2.toString();
        //then
        Assertions.assertThat(queryString1).isNotEmpty();
        System.out.println("queryString = " + queryString1);
        Assertions.assertThat(queryString2).isNotEmpty();
        System.out.println("queryString = " + queryString2);
    }

}