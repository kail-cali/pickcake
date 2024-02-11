package co.pickcake.reservedomain.searchcake.dto;


import lombok.Getter;
import lombok.Setter;

/* XXX 파라미터 서치 필터로 나둔 건데 추후 삭제 예정 */
@Getter @Setter
public class CakeSearch {

    private String cakeName;
    private String categoryName;
    private String brand;

}
