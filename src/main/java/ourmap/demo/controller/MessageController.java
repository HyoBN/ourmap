package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ourmap.demo.config.auth.MemberForm;
import ourmap.demo.entity.Member;
import ourmap.demo.service.MemberService;
import ourmap.demo.service.MessageService;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MessageService messageService;

    @GetMapping("/mailBox")
    public String gotoMailBox(Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        Member sender = memberService.findMemberByEmailAndProvider(member.getEmail(), member.getProvider());

        List<MessageForm> receivedMessages = messageService.findReceivedMessage(sender);
        model.addAttribute("receivedMessages", receivedMessages);
        return "basic/mailbox";
    }


}

