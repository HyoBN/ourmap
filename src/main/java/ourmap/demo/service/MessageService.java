package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.controller.MessageForm;
import ourmap.demo.entity.NewMessage;
import ourmap.demo.repository.NewMessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final NewMessageRepository newMessageRepository;


    public List<MessageForm> findReceivedMessage(Long id) {
        List<MessageForm> messageForms = new ArrayList<>();
        System.out.println("메시지 서비스로 들어오긴함.");

        List<NewMessage> newMessages = newMessageRepository.findByReceiverId(id);
        System.out.println("뉴메시지.size() = " + newMessages.size());
        
        for (NewMessage message : newMessages) {
            System.out.println("메시지서비스의 포문 안으로는 들어옴");
            System.out.println("뉴메시지 리스트.size() = " + newMessages.size());

            System.out.println("newMessage의 리시버 id = " + message.getReceiver().getId());
            System.out.println("newMessage의 메시지 타입 = " + message.getMessageType().name());

            MessageForm messageForm = new MessageForm(message);
            messageForms.add(messageForm);
        }
        return messageForms;
    }

}
