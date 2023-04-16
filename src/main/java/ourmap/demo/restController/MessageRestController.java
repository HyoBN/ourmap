package ourmap.demo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ourmap.demo.config.jwt.SecurityUtil;
import ourmap.demo.dto.MessageForm;
import ourmap.demo.entity.Member;
import ourmap.demo.service.MemberService;
import ourmap.demo.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MessageRestController {

    private final MemberService memberService;
    private final MessageService messageService;
    @GetMapping("/messages")
    public ResponseEntity receivedMessages(){
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        List<MessageForm> messages = messageService.findReceivedMessage(member);
        return new ResponseEntity(messages, HttpStatus.OK);
    }

}
