package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ourmap.demo.entity.Tip;

import java.util.List;
import java.util.Optional;

public interface TipRepository extends JpaRepository<Tip, Long> {
//    @Query(value = "select * " +
//            "from Tip " +
//            "where post_id = :postId", nativeQuery = true)
//    List<Tip> findByPostID(@Param("postId") Long postId);

    List<Tip> findByPost_Id(@Param(value="postId") Long postId);

    List<Tip> findByWriterId(Long writerId);
}
