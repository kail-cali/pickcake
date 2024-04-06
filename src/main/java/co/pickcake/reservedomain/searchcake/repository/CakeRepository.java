package co.pickcake.reservedomain.searchcake.repository;

import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.dto.CakeSearch;
import org.springframework.data.domain.Page;
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

    @Query(value = "select c from Cake c " +
            " join fetch c.cakeImages ci" +
            " where c.brand = :brand")
    Page<Cake> findByBrand(@Param("brand") String brand, Pageable pageable);

    @Query("select c from Cake c" +
            " join fetch c.cakeImages ci")
    Page<Cake> findAllByPaging(Pageable pageable);


    /* TODO 동적 쿼리는 만들다 보니 현 형태로는 이슈가 있음, 추후에 QueryDSL 로 바꿔야야 함 */
//    @Query("select c from Cake c" +
//            " join fetch c.cakeImages ci" +
//            " join fetch c.cakeCategoryList catl" +
//            " where c.brand = :brand")
//    Page<Cake> findBySpecific(CakeSearch cakeSearch, Pageable pageable);

}
