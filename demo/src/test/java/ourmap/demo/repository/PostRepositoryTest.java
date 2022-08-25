package ourmap.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void testPost() {
        Post post = new Post("postA");
        Post savedPost = postRepository.save(post);

        Post findPost = postRepository.findById(savedPost.getId()).get();

        assertThat(findPost.getId()).isEqualTo(post.getId());
        assertThat(findPost.getStoreName()).isEqualTo(post.getStoreName());
        assertThat(findPost).isEqualTo(post);
    }

    @Test
    public void basicCURD(){
        Post post1 = new Post("탐앤탐스");
        Post post2 = new Post(("맥도날드"));
        postRepository.save(post1);
        postRepository.save(post2);

        //단건 조회 검증
        Post findPost1 = postRepository.findById(post1.getId()).get();
        Post findPost2 = postRepository.findById(post2.getId()).get();
        assertThat(findPost1).isEqualTo(post1);
        assertThat(findPost2).isEqualTo(post2);

        //리스트 조회 검증
        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = postRepository.count();
        assertThat(count).isEqualTo(2);

        postRepository.delete(post1);
        postRepository.delete(post2);

        long deletedCount = postRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
}