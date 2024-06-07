package co.pickcake.image.domain;

import jakarta.persistence.*;

@Entity
public class DetailImage {

    @Id
    @GeneratedValue
    @Column(name = "detail_image")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="cake_images_id")
    private CakeImages cakeImages;
    private String uploadFileName;
    private String storeFileName;

    /* SEE CAKEIMAGEIMAGEFILES */
    public void setDetailImages(CakeImages cakeImages) {
        this.cakeImages =cakeImages;
    }

}
