package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ourmap.demo.entity.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post save(Post post);
    Optional<Post> findById(Long id);
    Post findByStoreName(String name);

}
