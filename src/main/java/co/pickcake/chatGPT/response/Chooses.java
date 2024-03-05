package co.pickcake.chatGPT.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Chooses {

    @JsonProperty("text")
    private String contents;

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("finish_reason")
    private String reason;

    /* logprobs */
}
