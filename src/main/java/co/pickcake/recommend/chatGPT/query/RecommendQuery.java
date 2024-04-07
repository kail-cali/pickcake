package co.pickcake.recommend.chatGPT.query;

import co.pickcake.recommend.chatGPT.query.event.GiftEvent;
import co.pickcake.recommend.chatGPT.query.event.RecommendEventType;
import co.pickcake.recommend.chatGPT.query.event.SpecialEvent;
import co.pickcake.recommend.chatGPT.query.event.WeatherEvent;
import co.pickcake.recommend.chatGPT.query.item.RecommendItem;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendQuery {
    /* AI as a SERVICE 를 위해 사전에 tune 내용을 바탕으로 미리 쿼리 조합을 생성함 */
    private Long queryId;
    @NotEmpty
    private RecommendType recommendType;
    private RecommendEvent recommendEvent;
    private RecommendTune recommendTune; // 추후에 기능 개선 건으로 추가

    /* 핵심 비즈니스 로직으로 조합하는 곳에서 한번에 처리
    *  TODO DSL 분기 많아질수록 비즈니스 로직 이렇게 처리하는 것도 부담인데 어떻게 더 최적화 할 수 있을지 고민 필요.
    *   모델에 따라 쿼리를 튜닝 및 성능 평가 해서 정말 DSL로 만들기 애매한 부분이 있음. */
    public String addContext() {
        if (recommendEvent == null) {
            return "";
        }
        String context = "";
        if (recommendEvent.getEventType() == RecommendEventType.WEATHER) {
            context += contextWithWeather();
        } else if (recommendEvent.getEventType() == RecommendEventType.SPECIAL) {
            context += contextWithSpecial();
        } else if (recommendEvent.getEventType() == RecommendEventType.GIFT) {
            context += contextWithGift();
        }
        return context;
    }
    public String contextWithWeather() {
        String contextSource = "";
        if (recommendEvent.getSpecific().getWeatherEvent() == WeatherEvent.CLOUDY) {
            contextSource += "구름이 잔뜩 낀 날씨에 어울리는 ";
        } else if (recommendEvent.getSpecific().getWeatherEvent() == WeatherEvent.SNOWING) {
            contextSource += "눈이 내리는 날씨에 어울리는 ";
        } else if (recommendEvent.getSpecific().getWeatherEvent() == WeatherEvent.SUNNY) {
            contextSource += "화창한 날씨에 어울리는 ";
        }
        return  contextSource;
    }
    public String contextWithSpecial() {
        String contextSource = "";
        if (recommendEvent.getSpecific().getSpecialEvent() == SpecialEvent.VALENTINE) {
            contextSource += "발렌타인 데이를 기념하는 ";
        } else if (recommendEvent.getSpecific().getSpecialEvent() == SpecialEvent.CHRISTMAS) {
            contextSource += "크리스마스를 기념하는 ";
        }
        return  contextSource;
    }

    public String contextWithGift() {
        String contextSource = "";
        if (recommendEvent.getSpecific().getGiftEvent() == GiftEvent.FAMILY) {
            contextSource += "가족들에게 선물할 ";
        } else if (recommendEvent.getSpecific().getGiftEvent() == GiftEvent.LOVE) {
            contextSource += "연인에게 선물할 ";
        } else if (recommendEvent.getSpecific().getGiftEvent() == GiftEvent.FRIENDS) {
            contextSource += "친구에게 선물할 ";
        }
        return contextSource;
    }

    public String addRecommendTarget() {
        String target = "";
        if (recommendType.getRecommendItem() == RecommendItem.CAKE) {
            target += "케이크";
            target += "를 추천해줘";
        }
        return target;
    }
    @Override
    public String toString() {
        String query = "";
        query += addContext();
        query += addRecommendTarget();
        return query;
    }

}
