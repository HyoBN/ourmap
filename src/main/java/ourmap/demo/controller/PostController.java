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

//    public PostController(PostService postService, TipService tipService, HttpSession httpSession) {
//        this.postService=postService;
//        this.tipService=tipService;
//        this.httpSession = httpSession;
//    }


    @ModelAttribute("storeTypes")
    private StoreTypes[] storeTypes(){
        return StoreTypes.values();
    }

    @ModelAttribute("posts")
    private List<Post> posts(){
        return postService.findPosts();
    }

    @GetMapping("/newForm")
    public String newPostPage(Model model) {
        model.addAttribute("post", new Post());
        return "post/newForm";
    }

    @PostMapping("/newForm")
    public String newPost(PostForm form, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        model.addAttribute("userName", member.getName());

        Post post = new Post(form.getStoreName(), form.getStoreType(), memberService.findMemberId(member.getEmail(), member.getProvider()));
        Tip tip = new Tip(post, form.getTip(), memberService.findMemberId(member.getEmail(), member.getProvider()));
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
        Tip newTip = new Tip(postService.findPostById(tip.getPostId()), tip.getComment(),memberService.findMemberId(member.getEmail(), member.getProvider()));
        tipService.upload(newTip);
        return "redirect:/mainPage";
    }

    @GetMapping("/home")
    public String home(Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");

        if(member!=null) {
            model.addAttribute("userName", member.getName());
            return "basic/home";
        } else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        if(member!=null) {
            model.addAttribute("userName", member.getName());
            return "basic/mainPage";
        }
        else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }


    }

    @RequestMapping("/test")
    public String test(){
        return "basic/test";
    }

    @RequestMapping("/editForm")
    public String edit(){
        return "post/editForm";
    }

    @GetMapping("/searchByName")
    public String search(String name, Model model) {
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
        Post post = new Post(postId, form.getStoreName(), form.getStoreType(),memberService.findMemberId(member.getEmail(), member.getProvider()));
        postService.upload(post);
        return "redirect:/mainPage";
    }

    @PostMapping("/deleteTip/{tipId}")
    public String deleteTip(@PathVariable("tipId") Long tipId, Long postId, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");

        Long sessionId = memberService.findMemberId(member.getEmail(), member.getProvider());
        Long writerId = (tipService.findTipById(tipId)).getWriterId();

        model.addAttribute(postService.findPostById(postId));

        if (sessionId.equals(writerId)) {
            System.out.println("두 값은 동일합니다.");
            tipService.deleteTip(tipId);
        }
        else {
            model.addAttribute("deniedMessage", "자신이 작성한 tip만 삭제 가능합니다!");
            System.out.println("디나이됨.");
        }

        System.out.println("tipId = " + tipId);
        return "post/editForm";
    }
}