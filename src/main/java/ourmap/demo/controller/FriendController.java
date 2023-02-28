package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ourmap.demo.config.auth.MemberForm;
import ourmap.demo.entity.Member;
import ourmap.demo.service.FriendService;
import ourmap.demo.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class FriendController {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private final FriendService friendService;

    @ModelAttribute("userNickname")
    private String userNickname(){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        return member.getNickname();
    }

    @PostMapping("/requestFriend")
    public String requestFriend(String friendNickname, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Member sender = memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider());
        try{
            Member receiver = memberService.findByNickname(friendNickname).get();
            if(sender.equals(receiver)){
                model.addAttribute("message", "나는 영원한 친구");
            }
            else if(friendService.request(sender, receiver)) {
                model.addAttribute("message", "친구 요청 완료");
            }else{
                model.addAttribute("message", "이미 친구이거나 요청을 한 상태입니다.");
            }
        }catch (NoSuchElementException e){
            model.addAttribute("message", "존재하지 않는 닉네임입니다.");}
        return "basic/home";
    }

    @PostMapping("/friendAccept")
    public String acceptFriendRequest(Long messageId) {
        friendService.acceptRequest(messageId);
        return "redirect:/mailBox";
    }

    @PostMapping("/friendReject")
    public String rejectFriendRequest(Long messageId) {
        friendService.rejectRequest(messageId);
        return "redirect:/mailBox";
    }
}
