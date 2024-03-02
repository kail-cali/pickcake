package co.pickcake.reservedomain.searchcake.repository;

import co.pickcake.reservedomain.entity.item.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CakeAdminRepository extends JpaRepository<Cake, Long> {

}
