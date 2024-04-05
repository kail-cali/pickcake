package co.pickcake.shopdomain.repository;

import co.pickcake.shopdomain.entity.Shop;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopJpaRepository extends JpaRepository<Shop, Long> {

    @Query("select s from Shop s" +
            " join fetch s.reserveInfo sr" +
            " join fetch s.naver ni" +
            " where s.id = :shopId")
    Shop findByIdWithDetails(@Param("shopId") Long shopId);



}
