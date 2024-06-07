package co.pickcake.reservation.searchcake.repository;

import co.pickcake.reservation.domain.item.EventCakeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeCategoryRepository extends JpaRepository<EventCakeCategory, Long> {


}
