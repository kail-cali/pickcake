package co.pickcake.reservedomain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.time.LocalTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReserveInfo {

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
        } else {
            reserveInfo.needReservation = needReservation;
            reserveInfo.needReservationBeforeDay = needReservationBeforeDay;
        }

        reserveInfo.setOnSiteSaleOnly(onSiteSaleOnly);
        reserveInfo.openTime = openTime;
        reserveInfo.closeTime = closeTime;
        return reserveInfo;
    }

}
