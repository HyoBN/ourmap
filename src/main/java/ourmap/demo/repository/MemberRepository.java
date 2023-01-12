package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourmap.demo.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndProvider(String email, String provider);
    Optional<Member> findByNickname(String nickname);

}
