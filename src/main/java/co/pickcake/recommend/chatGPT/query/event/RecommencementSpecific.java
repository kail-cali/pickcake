package co.pickcake.recommend.chatGPT.query.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommencementSpecific {

    private WeatherEvent weatherEvent;
    private SpecialEvent specialEvent;
    private GiftEvent giftEvent;
}
