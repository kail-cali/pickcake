package co.pickcake.sns.domain;

import co.pickcake.shop.domain.Shop;
import jakarta.persistence.DiscriminatorColumn;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="N")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Naver extends SNS {

    public static Naver create(Shop shop, String url) {
        Naver naver = new Naver();
        naver.setShop(shop);
        naver.setPlatform(Platform.NAVER);
        naver.setUrl(url);
        return naver;
    }

}
