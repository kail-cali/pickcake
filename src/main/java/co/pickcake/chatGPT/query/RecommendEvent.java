package co.pickcake.chatGPT.query;

import co.pickcake.chatGPT.query.event.RecommencementSpecific;
import co.pickcake.chatGPT.query.event.RecommendEventType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendEvent {
    /* LLM 기반 추천 시스템의 컨택스트를 담당하는 쿼리
    *  LLM 도 역시 생성형 모델 계열이라 추론 확률 기반의 모델로 autoregressive 방식으로 학습이 진행됨.
    *  그 결과, 문맥의 삽입 여부와 삽입 위치 따라 다른 추천 결과를 생산할 수 있음. */

    private RecommendEventType eventType;

    private RecommencementSpecific specific;


}
