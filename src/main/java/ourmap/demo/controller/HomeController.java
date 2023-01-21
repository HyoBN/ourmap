package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final TipService tipService;

    private final MemberService memberService;
    private final FriendService friendService;
    private final HttpSession httpSession;

    @ModelAttribute("storeTypes")
    private StoreTypes[] storeTypes(){
        return StoreTypes.values();
    }

    @ModelAttribute("userNickname")
    private String userNickname(){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        return member.getNickname();
    }

    @ModelAttribute("posts")
    private List<PostResponseDTO> posts(){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Long memberId = memberService.findMemberIdByEmailAndProvider(member.getEmail(), member.getProvider());
        List<Long> tipWriterId = new ArrayList<>();
        tipWriterId.add(memberId);
        tipWriterId.addAll(friendService.findFriendsId(memberId));
        List<Tip> tips = new ArrayList<>();
        for (Long writerId : tipWriterId) {
            tips.addAll(tipService.findTipByWriter(writerId));
        }
        Set<PostResponseDTO> postSet = new HashSet<>();
        Set<Long> postIdBytips = new HashSet<>();
        for (Tip tip : tips) {
            postIdBytips.add(tip.getPost().getId());
        }
        for (Long pid : postIdBytips) {
            Post post = postService.findPostById(pid);
            PostResponseDTO postResponseDTO = new PostResponseDTO(post);
            postSet.add(postResponseDTO);
        }

        for (Tip tip : tips) {
            for (PostResponseDTO postResponseDTO : postSet) {
                if(tip.getPost().getId()==postResponseDTO.getId()){
                    postResponseDTO.tips.add(tip);
                }
            }
        }
        List<PostResponseDTO> posts = new ArrayList<>(postSet);
        return posts;
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        if(member!=null) {
            return "basic/mainPage";
        } else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    @GetMapping("/nicknamePage")
    public String changeNickname(Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        if(member!=null) {
            return "basic/changeNicknamePage";
        } else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    private boolean isNotBlank(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if ((Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
    @PostMapping("/newNickname")
    public String newNickname(String nickname, Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        String userNickname = member.getNickname();
        if(!isNotBlank(nickname)){
            model.addAttribute("checkMessage", "공백을 포함할 수 없습니다.");
            return "basic/changeNicknamePage";
        } else if(memberService.IsSameNickname(nickname)){
            model.addAttribute("checkMessage", "이미 존재하는 닉네임입니다.");
            return "basic/changeNicknamePage";
        } else{
            Member updateMember = memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider());
            updateMember.updateNickname(nickname);
            memberService.updateMemberInfo(updateMember);
            MemberForm memberForm = new MemberForm(updateMember);
            httpSession.setAttribute("member",memberForm);
            return "redirect:/home";
        }
    }

    @GetMapping("/home")
    public String home(Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        if(member!=null) {
            return "basic/home";
        } else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    @RequestMapping("/test")
    public String test(){
        return "basic/test";
    }
}
