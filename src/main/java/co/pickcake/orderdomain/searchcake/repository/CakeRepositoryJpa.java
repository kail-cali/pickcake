package co.pickcake.orderdomain.searchcake.repository;

import co.pickcake.orderdomain.entity.item.Cake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepositoryJpa extends JpaRepository<Cake, Long> {


}
