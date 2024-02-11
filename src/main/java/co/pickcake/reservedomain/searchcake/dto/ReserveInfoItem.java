package co.pickcake.reservedomain.searchcake.dto;

import co.pickcake.reservedomain.entity.ReserveInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ReserveInfoItem {

    private Boolean onSiteSaleOnly;
    @NotEmpty
    private Boolean needReservation;
    @PositiveOrZero
    private Integer needReservationBeforeDay;
    private LocalTime openTime;
    private LocalTime closeTime;

    public ReserveInfoItem(ReserveInfo r) {
        onSiteSaleOnly = r.getOnSiteSaleOnly();
        needReservation = r.getNeedReservation();
        needReservationBeforeDay = r.getNeedReservationBeforeDay();
        openTime = r.getOpenTime();
        closeTime = r.getCloseTime();
    }

}
