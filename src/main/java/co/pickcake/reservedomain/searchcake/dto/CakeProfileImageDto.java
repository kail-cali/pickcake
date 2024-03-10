package co.pickcake.reservedomain.searchcake.dto;

import co.pickcake.imagedomain.entity.CakeImages;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CakeProfileImageDto {
    @JsonProperty("storePath")
    private String storePath;
    public CakeProfileImageDto(CakeImages cakeImages) {
        this.storePath = cakeImages.getProfileImage().getStoreFileName();
    }
}
