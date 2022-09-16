package ourmap.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.repository.PostRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    public void testPost() {
        Post post = new Post("postA", StoreTypes.CAFE);
        Long uploadedPostId = postService.upload(post);
        Post findPost = postService.findPostById(uploadedPostId);
        Post findPostByName = postService.findPostByName(post.getStoreName());

        assertThat(findPost.getId()).isEqualTo(post.getId());
        assertThat(findPost.getStoreName()).isEqualTo(post.getStoreName());
        assertThat(findPost).isEqualTo(post);
        assertThat(findPostByName).isEqualTo(post);

        postService.deletePost(post.getId());
        assertThat(postService.findPosts().isEmpty()).isEqualTo(true);

    }
}
