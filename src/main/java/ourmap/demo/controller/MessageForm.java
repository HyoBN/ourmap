package ourmap.demo.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ourmap.demo.entity.Message;
import ourmap.demo.repository.MemberRepository;
import ourmap.demo.service.MemberService;

@Getter
@Setter
public class MessageForm {
    private Long id;
    private String sender;
    private String receiver;
    private String type;

    public MessageForm(Message message) {
        this.id= message.getId();
        this.sender = message.getSender().getNickname();
        this.receiver = message.getReceiver().getNickname();
        this.type = message.getMessageType().getDescription();
    }
}
