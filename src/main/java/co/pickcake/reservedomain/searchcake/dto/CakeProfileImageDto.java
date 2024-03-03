package co.pickcake.reservedomain.searchcake.dto;

import co.pickcake.imagedomain.entity.CakeImages;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CakeProfileImageDto {

    private String storePath;
    public CakeProfileImageDto(CakeImages cakeImages) {
        this.storePath = cakeImages.getProfileImage().getStoreFileName();
    }
}
