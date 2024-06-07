package co.pickcake.reservation.searchcake.dto;

import co.pickcake.image.domain.ProfileImage;
import lombok.Data;

@Data
public class ImageFileDetailsDto {

    private String storePath;

    private Integer seq;
    public ImageFileDetailsDto(ProfileImage profileImage) {
        storePath = profileImage.getStoreFileName();
    }
}
