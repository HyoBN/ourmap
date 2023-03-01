package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.config.auth.MemberForm;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;
import ourmap.demo.service.FriendService;
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
    private List<PostResponseDTO> posts(){
        MemberForm memberForm = (MemberForm) httpSession.getAttribute("member");
        Member member = memberService.findMemberByEmailAndProvider(memberForm.getEmail(), memberForm.getProvider());
        return postService.getFriendsPostDTO(member);
    }

    @ModelAttribute("userNickname")
    private String userNickname(){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        return member.getNickname();
    }

    @GetMapping("/newForm")
    public String newPostPage() { return "post/newForm";}

    @PostMapping("/newForm")
    public String newPost(Model model, PostForm form) {
        if(postService.isExistPost(form)){
            model.addAttribute("msg", "이미 존재하는 post 입니다.");
            return "post/newForm";
        } else if (postService.isTooLongName(form)) {
            model.addAttribute("msg", "20글자 이내의 가게명을 입력해주세요.");
            return "post/newForm";
        }
        MemberForm memberForm = (MemberForm) httpSession.getAttribute("member");
        Member member = memberService.findMemberByEmailAndProvider(memberForm.getEmail(), memberForm.getProvider());
        Post post = new Post(form.getStoreName(), form.getStoreType(),member);
        Tip tip = new Tip(post, form.getTip(), member);
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
        Tip newTip = new Tip(postService.findPostById(tip.getPostId()), tip.getComment(),memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider()));
        tipService.upload(newTip);
        return "redirect:/mainPage";
    }

    @RequestMapping("/editForm")
    public String edit(){
        return "post/editForm";
    }

    @GetMapping("/sortByCategory")
    public String categorySort(String category, Model model) {

        MemberForm memberForm = (MemberForm) httpSession.getAttribute("member");
        Member member = memberService.findMemberByEmailAndProvider(memberForm.getEmail(), memberForm.getProvider());
        List<PostResponseDTO> categoryPosts = postService.findByStoreType(member, category);
        model.addAttribute("posts", categoryPosts);
        return "basic/mainPage";
    }

    @GetMapping("/searchByName")
    public String search(String name, Model model) {
        MemberForm memberForm = (MemberForm) httpSession.getAttribute("member");
        Member member = memberService.findMemberByEmailAndProvider(memberForm.getEmail(), memberForm.getProvider());
        List<PostResponseDTO> searchedPosts = postService.findByNameContains(member, name);
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
        Post post = new Post(postId, form.getStoreName(), form.getStoreType(),memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider()));
        postService.upload(post);
        return "redirect:/mainPage";
    }

    @PostMapping("/deleteTip/{tipId}")
    public String deleteTip(@PathVariable("tipId") Long tipId, Long postId, Model model) {
        model.addAttribute(postService.findPostById(postId));
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Long memberId = memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider()).getId();
        Long writerId = (tipService.findById(tipId)).getWriter().getId();
        if (memberId.equals(writerId)) {
            tipService.deleteTip(tipId);
        } else {
            model.addAttribute("deniedMessage", "자신이 작성한 tip만 삭제 가능합니다!");
        }
        return "post/editForm";
    }
}