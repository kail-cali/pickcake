package co.pickcake.image.repository;


import co.pickcake.image.domain.CakeImages;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CakeImageRepository extends JpaRepository<CakeImages, Long> {

}
