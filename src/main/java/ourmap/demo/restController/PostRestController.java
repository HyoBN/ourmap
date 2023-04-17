package ourmap.demo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.config.jwt.SecurityUtil;
import ourmap.demo.dto.TipForm;
import ourmap.demo.dto.PostResponseDTO;
import ourmap.demo.dto.PostRequestDTO;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.Tip;
import ourmap.demo.service.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class PostRestController {
    private final PostService postService;
    private final MemberService memberService;
    private final TipService tipService;

    @GetMapping("/posts")
    public ResponseEntity posts() {
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        List<PostResponseDTO> friendsPostDTO = postService.getFriendsPostDTO(member);
        return new ResponseEntity(friendsPostDTO, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity newPost(@RequestBody PostRequestDTO postRequestDTO) {
        String postCheck = postService.postingCheck(postRequestDTO);
        if (postService.isExistPost(postRequestDTO)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else if (!postCheck.equals("ok")) {
            return new ResponseEntity(postCheck, HttpStatus.BAD_REQUEST);
        } else {
            for (String comment : postRequestDTO.getTips()) {
                String tipCheck = tipService.tipPostingCheck(comment);
                if (!tipCheck.equals("ok")) {
                    return new ResponseEntity(tipCheck, HttpStatus.BAD_REQUEST);
                }
            }
            String memberEmail = SecurityUtil.getMemberEmail();
            Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
            Post post = new Post(postRequestDTO.getStoreName(), postRequestDTO.getStoreType(), member);
            postService.upload(post);
            for (String comment : postRequestDTO.getTips()) {
                Tip tip = new Tip(post, comment, member);
                tipService.upload(tip);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("tip")
    public ResponseEntity newTip(@RequestBody Map<String, String> tipInfo) {
        TipForm tipForm = new TipForm(Long.parseLong(tipInfo.get("postId")), tipInfo.get("comment"));
        String tipCheck = tipService.tipPostingCheck(tipForm.getComment());
        if (!tipCheck.equals("ok")) {
            return new ResponseEntity<>(tipCheck, HttpStatus.BAD_REQUEST);
        } else {
            String memberEmail = SecurityUtil.getMemberEmail();
            Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
            Tip newTip = new Tip(postService.findPostById(tipForm.getPostId()), tipForm.getComment(), memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider()));
            tipService.upload(newTip);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("category")
    public ResponseEntity sortByCategory(@RequestBody Map<String, String> request) {
        String category = request.get("category");
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        List<PostResponseDTO> friendsPostDTO = postService.getFriendsPostDTO(member);
        List<PostResponseDTO> categoryPostDTO = postService.findByStoreTypes(friendsPostDTO, category);
        return new ResponseEntity(categoryPostDTO, HttpStatus.OK);
    }

    @GetMapping("name")
    public ResponseEntity findByStoreNameContains(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        List<PostResponseDTO> friendsPostDTO = postService.getFriendsPostDTO(member);
        List<PostResponseDTO> nameContainsPostDTO = postService.findByName(friendsPostDTO, name);
        return new ResponseEntity(nameContainsPostDTO, HttpStatus.OK);
    }

    @DeleteMapping("tip/{id}")
    public ResponseEntity deleteTip(@PathVariable Long id) {
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        Tip tip = tipService.findById(id);
        if (tip.getWriter().getId() == member.getId()) {
            tipService.deleteTip(id);
            if(tipService.CountTipOfPost(tip.getPost())==0){
                postService.deletePost(tip.getPost());
            }
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}