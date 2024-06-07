package co.pickcake.image.controller;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageForm {

    private Long id;

    private Long cakeId;

    private String itemName;

    private MultipartFile profileImage;

    private List<MultipartFile> imageFiles;

//    public ImageForm(ProfileImage f) {
//        id = f.getId();
////        itemName = f.getI
////        cakeId = f.getCakeId();
////        itemName = f.getItemName();
////        profileImage = f.getProfileImage();
////        imageFiles = f.getImageFiles();
//    }


}
