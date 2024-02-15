package co.pickcake.imagedomain.entity;


import co.pickcake.policies.filename.policy.FileNamePolicy;
import co.pickcake.reservedomain.entity.item.Cake;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CakeImages {

    /* 상품 이미지 메타 정보의 형상 역할을 하는 프록시 엔티티
    *  TODO 상품 정보를 미리 등록하고 실제 이미지 저장은 이미지 서버에게 맡기는 지연 로딩 방식 구현 */

    @Id @GeneratedValue
    @Column(name = "cake_images_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "cakeImages", fetch = FetchType.LAZY)
    private Cake cake;

    private String imageName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_file_id")
    private ImageFile profileImage;

    /*TODO 상세 설명 이미지들 @OneToMany List<ImageFile> details */
    @OneToMany(mappedBy = "cakeImages", cascade = CascadeType.ALL)
    private List<ImageFile> details = new ArrayList<>();



    /* 연관 관계 편의 메서드*/
    /*SEE CAKEIMAGE-INGEFILES*/
    public void setProfileImage(ImageFile profileImage) {
        this.profileImage = profileImage;
    }

    /* CAKE-CAKEIMAGE */
    protected void setCake(Cake cake) {
        this.cake = cake;
        cake.setCakeImages(this);
    }
    /*CAKEIMAGE_IMAGEFILES*/ /* TODO 연관관계 생성 관련 수정 필요 */
    public void addImageFile(ImageFile imageFile) {
        details.add(imageFile);
        imageFile.setDetailImages(this);
    }

    /* GENERATE-DEFAULT-IMAGE-NAME */
    /* 케이크 이미지 레이어의 논리적인 이름을 디폴트로 부여하여 많은 비즈니스 로직과 서비스 로직을 분리하고 예외처리 상황에 대응하도록 함
    * CakeImage 는 이미지를 관리하는 레이어이긴 하지만 이름을 강제로 부여함으로 써 다음 두가지 예외처리를 함.
    * 실제로 대표이미지가 아직 등록되지 않은 경우를 고려함.
    * 이름을 알기 위해 이미지파일 릴레이션을 한번 더 참조하는 경우가 없도록 함.
    * */
    public static String generateDefaultImageName(FileNamePolicy fileNamePolicy) {
        return fileNamePolicy.generateDefaultName();
    }
    public static String generateImageName(FileNamePolicy fileNamePolicy, String brand, String name) {
        return fileNamePolicy.generateLogicalName(brand, name);
    }

    /* 수정 메서드 */

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /* 생성 메서드 */
    /* CakeImages 가 생성되는 시점에는 아직 이미지 정보가 없을 수 도 있다.
    *  그러나 반드시 Cake 정보는 있어야 한다. */
    public static CakeImages createCakeImages(Cake cake, FileNamePolicy fileNamePolicy) {
        CakeImages cakeImages = new CakeImages();
        cakeImages.setCake(cake);

        /* 이미지 레이어 이름은 정책에 따라 생성*/
        cakeImages.setImageName(CakeImages.generateImageName(fileNamePolicy, cake.getBrand(), cake.getName()));
        return cakeImages;
    }

    /* 버전 호환성을 위해 남겨둠, 코드에서 네이밍 정책 인터페이스를 통하지 않으면 별도로 레이어 생성을 지양함 */
    public static CakeImages createCakeImages(String imageName) {
        CakeImages cakeImages = new CakeImages();
        cakeImages.setImageName(imageName);
        return cakeImages;
    }


}
