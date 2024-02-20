package co.pickcake.shopdomain.repository;

import co.pickcake.shopdomain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopJpaRepository extends JpaRepository<Shop, Long> {


}
