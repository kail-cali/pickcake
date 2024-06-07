package co.pickcake.auth.repository;


import co.pickcake.auth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findByUsername(String username);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByUserIdAndPassword(String userId, String password);
}
