package co.pickcake.recommend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatGptV1Request {
    @JsonProperty("model")
    private String model;
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("max_tokens")
    private Integer maxTokens;

}
