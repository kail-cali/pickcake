package co.pickcake.reservedomain.searchcake.repository;

import co.pickcake.reservedomain.entity.item.Cake;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CakeRepository extends JpaRepository<Cake, Long> {

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

//    @Query(value = "select c from Cake c " +
//            " join fetch c.cakeImages ci" +
//            " where c.brand = :brand")
//    Page<Cake> findByBrand(@Param("brand") String brand, Pageable pageable);

}
