package co.pickcake.imagedomain.entity;


import co.pickcake.policies.policy.policy.FileNamePolicy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile {

    @Id @GeneratedValue
    @Column(name = "image_file_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="cake_images_id")
    private CakeImages cakeImages;

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
        this.cakeImages = cakeImages;
        cakeImages.setProfileImage(this);
    }
    /* SEE CAKEIMAGE_IMAGEFILES*/
    public void setDetailImages(CakeImages cakeImages) {
        this.cakeImages =cakeImages;
    }

    /* 생성 메서드 */
    public static ImageFile createImageFile(String uploadFileName, FileNamePolicy fileNamePolicy) {
        ImageFile imageFile = new ImageFile();
        imageFile.setUploadFileName(uploadFileName);
        imageFile.setStoreFileName(fileNamePolicy.concatGenExt(uploadFileName));
        return imageFile;
    }

    public static ImageFile createImageFile(CakeImages cakeImages, String uploadFileName, FileNamePolicy fileNamePolicy) {
        ImageFile imageFile = new ImageFile();
        imageFile.setUploadFileName(uploadFileName);
        imageFile.setStoreFileName(fileNamePolicy.concatGenExt(uploadFileName));
        imageFile.setProfileCakeImages(cakeImages);

        return imageFile;
    }




    /* 비즈니스 로직 */







}
