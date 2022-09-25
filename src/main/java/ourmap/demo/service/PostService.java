package ourmap.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ourmap.demo.entity.Post;
import ourmap.demo.repository.PostRepository;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long upload(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    public Long deletePost(Long id) {
        postRepository.deleteById(id);
        return id;
    }

    public List<Post> findPosts(){
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        //optional 처리를 위해 .get()사용
        return postRepository.findById(id).get();
    }

    public Post findPostByName(String name){
        return postRepository.findByStoreName(name);
    }

    public List<Post> findByNameContains(String name) {
        return postRepository.findByStoreNameContains(name);
    }

    public List<Post> findByWriterId(Long writerId){
        return postRepository.findByWriterId(writerId);}
}