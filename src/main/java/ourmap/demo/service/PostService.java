package ourmap.demo.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.controller.PostResponseDTO;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;
import ourmap.demo.repository.PostRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<PostResponseDTO> findByNameContains(Member member, String name) {
        List<PostResponseDTO> containsNamePostDTO = new ArrayList<>();
        for (PostResponseDTO postResponseDTO : getFriendsPostDTO(member)) {
            if (postResponseDTO.getStoreName().contains(name)) {
                containsNamePostDTO.add(postResponseDTO);
            }
        }
        return containsNamePostDTO;
    }

    public List<PostResponseDTO> findByStoreType(Member member, String type) {
        List<PostResponseDTO> categoryPostDTO = new ArrayList<>();
        for (PostResponseDTO postResponseDTO : getFriendsPostDTO(member)) {
            if (postResponseDTO.getStoreType().equals(StoreTypes.valueOf(type))) {
                categoryPostDTO.add(postResponseDTO);
            }
        }
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

        for (Tip tip : tips) {
            for (PostResponseDTO postResponseDTO : postDTOs) {
                if(tip.getPost().getId()==postResponseDTO.getId()){
                    postResponseDTO.tips.add(tip);
                }
            }
        }
        return postDTOs;
    }
}