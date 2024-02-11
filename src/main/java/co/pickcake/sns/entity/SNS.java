package co.pickcake.sns.entity;

import co.pickcake.shopdomain.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public abstract class SNS {
    @Id
    @GeneratedValue
    @Column(name="sns_id")
    private Long snsId;

    @Enumerated(EnumType.STRING)
    private Platform platform;
    private String url;
    /* dba 입장에서 FK 없이 site url 정보가 중첩해서 여러개 있으면 db 파일 깨졌을 때 최후의 수동 복구가 불가능함 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private Shop shop;

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
