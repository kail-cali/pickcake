package co.pickcake.reservation.domain;

import co.pickcake.aop.datetime.AuditOnTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReserveInfo extends AuditOnTime {

    @Id
    @GeneratedValue
    @Column(name="reserve_info_id")
    private Long reserveInfoId;
    private Boolean onSiteSaleOnly;
    private Boolean needReservation;
    private Integer needReservationBeforeDay;
    private LocalTime openTime;
    private LocalTime closeTime;

    /* 수정 메서드 */

    public void setOnSiteSaleOnly(Boolean onSiteSaleOnly) {
        this.onSiteSaleOnly = onSiteSaleOnly;
    }

    public static ReserveInfo create(Boolean onSiteSaleOnly, Boolean needReservation, Integer needReservationBeforeDay, LocalTime openTime, LocalTime closeTime) {
        ReserveInfo reserveInfo = new ReserveInfo();
        if (onSiteSaleOnly) {
            reserveInfo.needReservation = false;
            reserveInfo.needReservationBeforeDay = 0;
        } else if (needReservation) {
            reserveInfo.needReservation = true;
            reserveInfo.needReservationBeforeDay = needReservationBeforeDay;
        } else {
            reserveInfo.needReservation = false;
            reserveInfo.needReservationBeforeDay = 0;
        }

        reserveInfo.setOnSiteSaleOnly(onSiteSaleOnly);
        reserveInfo.openTime = openTime;
        reserveInfo.closeTime = closeTime;
        return reserveInfo;
    }

}
