package co.pickcake.reservedomain.searchcake.repository;

import co.pickcake.reservedomain.entity.item.Cake;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CakeRepository extends JpaRepository<Cake, Long> {
    /*
     *  CAKE USER REPO 스펙 및 제공 API
     * - 전체 케이크 상품 조회
     * - 브랜드별 상품 조회
     * - 카테고리별 상품 조회
     * - 아이템 상세 조회
     * */

    @Query("select c from Cake c" +
            " join fetch c.cakeImages ci" +
            " join fetch ci.profileImage p" +
            " join fetch  c.shop s" +
            " join fetch s.reserveInfo ri" +
            " join fetch s.naver sn" +
            " join fetch s.instagram sii" +
            " where c.id = :itemId")
    Cake findByIdDetails(@Param("itemId") Long id);

    @Query("select c from Cake c" +
            " join fetch c.cakeImages ci" +
            " where c.brand = :brand")
    List<Cake> findByBrand(@Param("brand") String brand);

    @Query(value = "select c from Cake c " +
            " join fetch c.cakeImages ci" +
            " where c.brand = :brand")
    Page<Cake> findByBrand(@Param("brand") String brand, Pageable pageable);

    @Query("select c from Cake c" +
            " join fetch c.cakeImages ci")
    Page<Cake> findAllByPaging(Pageable pageable);

    @QueryHints(value= @QueryHint(name = "org.hibernate.readOnly", value = "true" ))
    Cake findReadOnlyByName(String name);



}
