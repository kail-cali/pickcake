package co.pickcake.reservation.searchcake.repository;

import co.pickcake.reservation.domain.item.Cake;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CakeAdminRepository extends JpaRepository<Cake, Long> {

}
