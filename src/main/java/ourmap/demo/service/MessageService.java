package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.dto.MessageForm;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.MessageTypes;
import ourmap.demo.entity.NewMessage;
import ourmap.demo.entity.OldMessage;
import ourmap.demo.repository.NewMessageRepository;
import ourmap.demo.repository.OldMessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final NewMessageRepository newMessageRepository;
    private final OldMessageRepository oldMessageRepository;


    public List<MessageForm> findReceivedMessage(Member member) {
        List<MessageForm> messageForms = new ArrayList<>();
        List<NewMessage> newMessages = newMessageRepository.findByReceiverId(member.getId());
        for (NewMessage message : newMessages) {
            MessageForm messageForm = new MessageForm(message);
            messageForms.add(messageForm);
        }
        return messageForms;
    }

    public void newToOld(NewMessage message) {
        OldMessage oldMessage = new OldMessage(message.getSender(), message.getReceiver(), message.getMessageType());
        oldMessageRepository.save(oldMessage);
    }

    public boolean isExistNewMessage(Member sender, Member receiver, MessageTypes messageTypes) {
        try {
            NewMessage message = newMessageRepository.findBySenderIdAndReceiverIdAndMessageType(sender.getId(), receiver.getId(), messageTypes).get();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
