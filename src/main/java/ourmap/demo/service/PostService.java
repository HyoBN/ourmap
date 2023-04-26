package ourmap.demo.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.dto.PostRequestDTO;
import ourmap.demo.dto.PostResponseDTO;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;
import ourmap.demo.repository.PostRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final TipService tipService;
    private final FriendService friendService;
    private final PostRepository postRepository;

    public void upload(Post post) {
        postRepository.save(post);
    }
    public Post findPostById(Long id) {
        return postRepository.findById(id).get();
    }

    public Post findByStoreNameAndStoreType(PostRequestDTO postRequestDTO) {
        Post post = postRepository.findByStoreNameAndStoreType(postRequestDTO.getStoreName(), postRequestDTO.getStoreType()).get();
        return post;
    }

    public List<PostResponseDTO> findByName(List<PostResponseDTO> posts, String name) {
        List<PostResponseDTO> containsNamePostDTO = new ArrayList<>();
        for (PostResponseDTO postResponseDTO : posts) {
            if (postResponseDTO.getStoreName().contains(name)) {
                containsNamePostDTO.add(postResponseDTO);
            }
        }
        Collections.sort(containsNamePostDTO);
        return containsNamePostDTO;
    }

    public List<PostResponseDTO> findByStoreTypes(List<PostResponseDTO> posts, String category) {
        List<PostResponseDTO> categoryPostDTO = new ArrayList<>();
        for (PostResponseDTO postResponseDTO : posts) {
            if (postResponseDTO.getStoreType().equals(StoreTypes.valueOf(category))) {
                categoryPostDTO.add(postResponseDTO);
            }
        }
        Collections.sort(categoryPostDTO);
        return categoryPostDTO;
    }

    public List<PostResponseDTO> getFriendsPostDTO(Member member) {
        List<Member> tipWriter = new ArrayList<>();
        tipWriter.add(member);
        tipWriter.addAll(friendService.findFriends(member));
        List<Tip> tips = new ArrayList<>();
        for (Member writer : tipWriter) {
            tips.addAll(tipService.findByWriter(writer));
        }

        Set<Post> postByTips = new HashSet<>();
        for (Tip tip : tips) {
            postByTips.add(tip.getPost());
        }

        List<PostResponseDTO> postDTOs = new ArrayList<>();
        for (Post post : postByTips) {
            PostResponseDTO postResponseDTO = new PostResponseDTO(post);
            postDTOs.add(postResponseDTO);
        }
        Collections.sort(postDTOs);

        for (Tip tip : tips) {
            for (PostResponseDTO postResponseDTO : postDTOs) {
                if(tip.getPost().getId()==postResponseDTO.getId()){
                    postResponseDTO.tips.add(tip);
                }
            }
        }
        for (PostResponseDTO postResponseDTO : postDTOs) {
            Collections.sort(postResponseDTO.tips);
        }
        return postDTOs;
    }

    public void deletePost(Post post) {
        postRepository.delete(findPostById(post.getId()));
    }

    public boolean isExistPost(PostRequestDTO postRequestDTO) {
        try {
            Post post = postRepository.findByStoreNameAndStoreType(postRequestDTO.getStoreName(), postRequestDTO.getStoreType()).get();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
    public boolean lengthOver(PostRequestDTO postRequestDTO) {
        int len = postRequestDTO.getStoreName().length();
        if (0 < len && len < 20) {
            return false;
        }
        else return true;
    }

    public boolean existSpecialWords(String word) {
        if (!word.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|(|)|,|.| |-]*")) {
            return true;
        } else return false;
    }
    public String postingCheck(PostRequestDTO postRequestDTO) {
        if (existSpecialWords(postRequestDTO.getStoreName())) {
            return "existSpecialWord";
        } else if (lengthOver(postRequestDTO)) {
            return "storeNameLengthOver";
        } else return "ok";
    }
}