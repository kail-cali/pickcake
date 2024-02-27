package co.pickcake.reservedomain.searchcake.response;

import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PickCakeDocumentResponse {

    @JsonProperty("item_id")
    private Long itemId;
    @JsonProperty("item_name")
    private String name;
    @JsonProperty("price")
    private Integer price;

    public PickCakeDocumentResponse(CakeSimpleSearch o) {
        itemId = o.getItemId();
        name = o.getName();
        price = o.getPrice();
    }
    /* 테스트 용으로 만든 생성 메서드 */
    public PickCakeDocumentResponse(Long id, String name, Integer price) {
//        PickCakeDocumentResponse response = new PickCakeDocumentResponse();
        this.itemId = id;
        this.price = price;
        this.name = name;
    }

    private PickCakeDocumentResponse() {
    }
}
