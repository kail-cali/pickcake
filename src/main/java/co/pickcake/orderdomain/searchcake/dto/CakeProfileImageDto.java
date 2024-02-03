package co.pickcake.orderdomain.searchcake.dto;

import co.pickcake.imagedomain.entity.CakeImages;
import lombok.Data;
import lombok.Getter;

@Data @Getter
public class CakeProfileImageDto {

    private String storePath;

    public CakeProfileImageDto(CakeImages cakeImages) {
        this.storePath = cakeImages.getProfileImage().getStoreFileName();
    }
}
