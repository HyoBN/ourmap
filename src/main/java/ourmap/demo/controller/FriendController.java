package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ourmap.demo.config.auth.MemberForm;
import ourmap.demo.entity.Member;
import ourmap.demo.service.FriendService;
import ourmap.demo.service.MemberService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class FriendController {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private final FriendService friendService;

    @PostMapping("/requestFriend")
    public String requestFriend(String friendNickname, Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Long senderId = memberService.findMemberIdByEmailAndProvider(member.getEmail(), member.getProvider());
        Member receiver = memberService.findByNickname(friendNickname);
        friendService.request(senderId, receiver.getId());
        model.addAttribute("message", "친구 요청 완료");
        return "redirect:/home";
    }

    @PostMapping("/friendAccept")
    public String acceptFriendRequest(Model model, Long messageId) {
        friendService.acceptRequest(messageId);
        return "redirect:/mailBox";
    }

    @PostMapping("/friendReject")
    public String rejectFriendRequest(Model model, Long messageId) {
        friendService.rejectRequest(messageId);
        return "redirect:/mailBox";
    }

}
