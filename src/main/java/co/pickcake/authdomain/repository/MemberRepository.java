package co.pickcake.authdomain.repository;


import co.pickcake.authdomain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findByUsername(String username);

    Optional<Member> findByUserID(String loginID);
}
