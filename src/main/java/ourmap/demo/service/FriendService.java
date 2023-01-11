package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.MessageTypes;
import ourmap.demo.entity.NewMessage;
import ourmap.demo.repository.FriendRepository;
import ourmap.demo.repository.NewMessageRepository;
import ourmap.demo.repository.OldMessageRepository;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final NewMessageRepository newMessageRepository;
    private final OldMessageRepository oldMessageRepository;

    @Transactional
    public void request(Long senderId, Long receiverId) {
        MessageTypes messageTypes = MessageTypes.FRIENDREQUEST;
        NewMessage newMessage = new NewMessage(senderId, receiverId, messageTypes);
        newMessageRepository.save(newMessage);
    }
}
