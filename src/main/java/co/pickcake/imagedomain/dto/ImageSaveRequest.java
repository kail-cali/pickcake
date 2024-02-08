package co.pickcake.imagedomain.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageSaveRequest {


    private String storeName;
    private String ext;
    private MultipartFile images;

    public ImageSaveRequest(MultipartFile images, String storeName, String ext) {
        this.storeName = storeName;
        this.images = images;
        this.ext = ext;
    }

}
