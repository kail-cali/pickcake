package co.pickcake.reservation.searchcake.dto;

import co.pickcake.image.domain.CakeImages;
import com.fasterxml.jackson.annotation.JsonProperty;
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
