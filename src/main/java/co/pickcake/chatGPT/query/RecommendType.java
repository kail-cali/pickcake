package co.pickcake.chatGPT.query;

import co.pickcake.chatGPT.query.item.RecommendItem;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RecommendType {
    /* 어떤 아이템을 추천해줄지 결졍하는 쿼리 문구 */
    @NotEmpty
    private RecommendItem recommendItem;


}
