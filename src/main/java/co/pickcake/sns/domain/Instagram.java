package co.pickcake.sns.domain;

import co.pickcake.shop.domain.Shop;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="I")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instagram extends SNS {

    public static Instagram create(Shop shop, String url) {
        Instagram instar = new Instagram();
        instar.setShop(shop);
        instar.setPlatform(Platform.INSTAGRAM);
        instar.setUrl(url);
        return instar;
    }
}
