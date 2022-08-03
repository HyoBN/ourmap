package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


}
