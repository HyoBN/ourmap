package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ourmap.demo.entity.Friend;
import ourmap.demo.entity.Member;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByMember2Id(Long memberId);
    List<Friend> findByMember1Id(Long memberId);


}
