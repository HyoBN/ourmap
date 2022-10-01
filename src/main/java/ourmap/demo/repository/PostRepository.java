package ourmap.demo.repository;

import org.springframework.boot.autoconfigure.session.StoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByStoreName(String name);

    List<Post> findByStoreNameContains(String name);

    List<Post> findByWriterId(Long writerId);

    List<Post> findByStoreType(StoreTypes type);

}