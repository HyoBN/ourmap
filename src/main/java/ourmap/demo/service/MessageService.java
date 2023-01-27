package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.controller.MessageForm;
import ourmap.demo.entity.MessageTypes;
import ourmap.demo.entity.NewMessage;
import ourmap.demo.entity.OldMessage;
import ourmap.demo.repository.NewMessageRepository;
import ourmap.demo.repository.OldMessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final NewMessageRepository newMessageRepository;
    private final OldMessageRepository oldMessageRepository;


    public List<MessageForm> findReceivedMessage(Long id) {
        List<MessageForm> messageForms = new ArrayList<>();
        List<NewMessage> newMessages = newMessageRepository.findByReceiverId(id);
        for (NewMessage message : newMessages) {
            MessageForm messageForm = new MessageForm(message);
            messageForms.add(messageForm);
        }
        return messageForms;
    }

    public void newToOld(Long messageId) {
        NewMessage message = newMessageRepository.findById(messageId).get();
        OldMessage oldMessage = new OldMessage(message.getSender(), message.getReceiver(), message.getMessageType());
        oldMessageRepository.save(oldMessage);
    }

    public boolean isExistNewMessage(Long senderId, Long receiver_id, MessageTypes messageTypes) {
        try {
            NewMessage message = newMessageRepository.findBySenderIdAndReceiverIdAndMessageType(senderId, receiver_id, messageTypes).get();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
