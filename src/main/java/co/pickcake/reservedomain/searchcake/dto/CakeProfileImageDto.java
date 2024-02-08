package co.pickcake.reservedomain.searchcake.dto;

import co.pickcake.imagedomain.entity.CakeImages;
import lombok.Data;
import lombok.Getter;

@Data
public class CakeProfileImageDto {

    private String storePath;
    public CakeProfileImageDto(CakeImages cakeImages) {
        this.storePath = cakeImages.getProfileImage().getStoreFileName();
    }
}
