package co.pickcake.imagedomain.entity;


import co.pickcake.policies.filename.policy.FileNamePolicy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    @Id @GeneratedValue
    @Column(name = "profile_image_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    /* 수정 메서드 */
    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /* 연관관계 편의 메서드 */
    /* CAKEIMAGE-INGEFILES */
    public void setProfileCakeImages(CakeImages cakeImages) {
        cakeImages.setProfileImage(this);
    }

    /* 생성 메서드 */
    public static ProfileImage createImageFile(String uploadFileName, FileNamePolicy fileNamePolicy) {
        ProfileImage profileImage = new ProfileImage();
        profileImage.setUploadFileName(uploadFileName);
        profileImage.setStoreFileName(fileNamePolicy.concatGenExt(uploadFileName));
        return profileImage;
    }

    public static ProfileImage createImageFile(CakeImages cakeImages, String uploadFileName, FileNamePolicy fileNamePolicy) {
        ProfileImage profileImage = new ProfileImage();
        profileImage.setUploadFileName(uploadFileName);
        profileImage.setStoreFileName(fileNamePolicy.concatGenExt(uploadFileName));
        profileImage.setProfileCakeImages(cakeImages);
        return profileImage;
    }

    /* 비즈니스 로직 */


}
