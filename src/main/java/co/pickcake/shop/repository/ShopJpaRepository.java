package co.pickcake.shop.repository;

import co.pickcake.shop.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ShopJpaRepository extends JpaRepository<Shop, Long> {

    @Query("select s from Shop s" +
            " join fetch s.reserveInfo sr" +
            " join fetch s.naver ni" +
            " where s.id = :shopId")
    Shop findByIdWithDetails(@Param("shopId") Long shopId);

    Page<Shop> findAll(Pageable pageable);




}
