package co.pickcake.common.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;


/* usage for order event */
@Embeddable
@Getter
public class ItemDate {

    private LocalDateTime manufactureDate;
    private LocalDateTime expireDate;

    public ItemDate() {
    }

    public ItemDate(LocalDateTime manufactureDate, LocalDateTime expireDate) {
        this.manufactureDate = manufactureDate;
        this.expireDate = expireDate;
    }
}
