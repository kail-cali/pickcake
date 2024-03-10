package co.pickcake.chatGPT.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chooses {

    @JsonProperty("text")
    private String contents;

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("finish_reason")
    private String reason;

    /* logprobs */
}
