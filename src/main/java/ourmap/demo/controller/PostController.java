package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.config.auth.MemberForm;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;
import ourmap.demo.service.MemberService;
import ourmap.demo.service.PostService;
import ourmap.demo.service.TipService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final TipService tipService;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @ModelAttribute("storeTypes")
    private StoreTypes[] storeTypes(){
        return StoreTypes.values();
    }

    @ModelAttribute("posts")
    private List<Post> posts(){
        return postService.findPosts();
    }
    @ModelAttribute("userNickname")
    private String userNickname(){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        return member.getNickname();
    }

    @GetMapping("/newForm")
    public String newPostPage(Model model) {
        model.addAttribute("post", new Post());
        return "post/newForm";
    }

    @PostMapping("/newForm")
    public String newPost(PostForm form, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        //model.addAttribute("userName", member.getName());

        Post post = new Post(form.getStoreName(), form.getStoreType(), memberService.findMemberIdByEmailAndProvider(member.getEmail(), member.getProvider()));
        Tip tip = new Tip(post, form.getTip(), memberService.findMemberIdByEmailAndProvider(member.getEmail(), member.getProvider()));
        tipService.upload(tip);
        postService.upload(post);
        return "redirect:/mainPage";
    }

    @GetMapping("/newComment")
    public String newComment(){
        return "redirect:/basic/mainPage";
    }

    @PostMapping("/newComment")
    public String newComment(TipForm tip) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Tip newTip = new Tip(postService.findPostById(tip.getPostId()), tip.getComment(),memberService.findMemberIdByEmailAndProvider(member.getEmail(), member.getProvider()));
        tipService.upload(newTip);
        return "redirect:/mainPage";
    }

    @RequestMapping("/editForm")
    public String edit(){
        return "post/editForm";
    }

    @GetMapping("/sortByCategory")
    public String categorySort(String category, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        List<Post> categoryPosts = postService.findByStoreType(category);
        model.addAttribute("posts", categoryPosts);
        return "basic/mainPage";
    }

    @GetMapping("/searchByName")
    public String search(String name, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        List<Post> searchedPosts = postService.findByNameContains(name);
        // 검색 결과가 없을 시 message 전달, 출력 로직 추가하기.
        model.addAttribute("posts", searchedPosts);
        return "basic/mainPage";
    }

    @GetMapping("/edit/{postId}")
    public String editForm(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "post/editForm";
    }

    @PostMapping("/edit/{postId}")
    public String editPost(@PathVariable("postId") Long postId, PostForm form) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Post post = new Post(postId, form.getStoreName(), form.getStoreType(),memberService.findMemberIdByEmailAndProvider(member.getEmail(), member.getProvider()));
        postService.upload(post);
        return "redirect:/mainPage";
    }

    @PostMapping("/deleteTip/{tipId}")
    public String deleteTip(@PathVariable("tipId") Long tipId, Long postId, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Long sessionId = memberService.findMemberIdByEmailAndProvider(member.getEmail(), member.getProvider());
        Long writerId = (tipService.findTipById(tipId)).getWriterId();

        model.addAttribute(postService.findPostById(postId));

        if (sessionId.equals(writerId)) {
            tipService.deleteTip(tipId);
        } else {
            model.addAttribute("deniedMessage", "자신이 작성한 tip만 삭제 가능합니다!");
        }
        return "post/editForm";
    }
}