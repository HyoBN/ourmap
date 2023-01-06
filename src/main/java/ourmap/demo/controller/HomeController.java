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
import ourmap.demo.service.MemberService;
import ourmap.demo.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @ModelAttribute("storeTypes")
    private StoreTypes[] storeTypes(){
        return StoreTypes.values();
    }

//    @ModelAttribute("userNickname")
//    private String userNickname(){
//        MemberForm member = (MemberForm) httpSession.getAttribute("member");
//        String nickname = memberService.findNicknameByMemberForm(member);
//        return nickname;
//    }

    @ModelAttribute("posts")
    private List<Post> posts(){
        return postService.findPosts();
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        if(member!=null) {
            String nickname = memberService.findNicknameByMemberForm(member);
            model.addAttribute("userNickname", nickname);
            return "basic/mainPage";
        }
        else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    @GetMapping("/nicknamePage")
    public String changeNickname(Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        if(member!=null) {
            String nickname = memberService.findNicknameByMemberForm(member);
            model.addAttribute("userNickname", nickname);
            return "basic/changeNicknamePage";
        }
        else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    private boolean isNotBlank(String str) {
        int len=str.length();
        if (str == null || len == 0) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if ((Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
    @PostMapping("/newNickname")
    public String newNickname(String nickname, Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        String userNickname = memberService.findNicknameByMemberForm(member);
        if(!isNotBlank(nickname)){
            model.addAttribute("userNickname", userNickname);
            model.addAttribute("checkMessage", "공백을 포함할 수 없습니다.");
            return "basic/changeNicknamePage";
        }
        else if(memberService.IsSameNickname(nickname)){
            model.addAttribute("userNickname", userNickname);
            model.addAttribute("checkMessage", "이미 존재하는 닉네임입니다.");
            return "basic/changeNicknamePage";
        }
        else{
            Member updateMember = memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider());
            updateMember.updateNickname(nickname);
            memberService.updateMemberInfo(updateMember);
            model.addAttribute("userNickname", nickname);
            return "basic/home";
        }
    }

    @GetMapping("/home")
    public String home(Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        String userNickname = memberService.findNicknameByMemberForm(member);
        if(member!=null) {
            model.addAttribute("userNickname", userNickname);
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
