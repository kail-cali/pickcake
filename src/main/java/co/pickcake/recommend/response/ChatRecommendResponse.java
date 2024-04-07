package co.pickcake.recommend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecommendResponse {
    @JsonProperty("id")
    private String givenId;

    @JsonProperty("object")
    private String objectType;
    @JsonProperty("created")
    private Double created;

    @JsonProperty("choices")
    private List<Chooses> chooses;

}
