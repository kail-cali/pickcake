package co.pickcake.reservedomain.searchcake.dto;

import co.pickcake.imagedomain.entity.ProfileImage;
import lombok.Data;

@Data
public class ImageFileDetailsDto {

    private String storePath;

    private Integer seq;
    public ImageFileDetailsDto(ProfileImage profileImage) {
        storePath = profileImage.getStoreFileName();
    }
}
