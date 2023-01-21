package ourmap.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ourmap.demo.controller.PostResponseDTO;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;
import ourmap.demo.repository.PostRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private final TipService tipService;

    private final MemberService memberService;
    private final FriendService friendService;
    private final PostRepository postRepository;

    @Autowired
    public PostService(TipService tipService, MemberService memberService, FriendService friendService, PostRepository postRepository) {
        this.tipService = tipService;
        this.memberService = memberService;
        this.friendService = friendService;
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
        return postRepository.findById(id).get();
    }

    public List<PostResponseDTO> findByNameContains(Long memberId, String name) {
        List<PostResponseDTO> friendsPostDTO = getFriendsPostDTO(memberId);
        List<PostResponseDTO> containsNamePostDTO = new ArrayList<>();
        for (PostResponseDTO postResponseDTO : friendsPostDTO) {
            if (postResponseDTO.getStoreName().contains(name)) {
                containsNamePostDTO.add(postResponseDTO);
            }
        }
        return containsNamePostDTO;
    }

    public List<PostResponseDTO> findByStoreType(Long memberId, String type) {
        List<PostResponseDTO> friendsPostDTO = getFriendsPostDTO(memberId);
        List<PostResponseDTO> categoryPostDTO = new ArrayList<>();
        for (PostResponseDTO postResponseDTO : friendsPostDTO) {
            if (postResponseDTO.getStoreType().equals(StoreTypes.valueOf(type))) {
                categoryPostDTO.add(postResponseDTO);
            }
        }
        return categoryPostDTO;
    }

    public List<PostResponseDTO> getFriendsPostDTO(Long memberId) {
        List<Long> tipWriterId = new ArrayList<>();
        tipWriterId.add(memberId);
        tipWriterId.addAll(friendService.findFriendsId(memberId));
        List<Tip> tips = new ArrayList<>();
        for (Long writerId : tipWriterId) {
            tips.addAll(tipService.findTipByWriter(writerId));
        }
        List<PostResponseDTO> postDTOs = new ArrayList<>();
        Set<Long> postIdByTips = new HashSet<>();
        for (Tip tip : tips) {
            postIdByTips.add(tip.getPost().getId());
        }
        for (Long pid : postIdByTips) {
            Post post = findPostById(pid);
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