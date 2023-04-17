package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.*;
import ourmap.demo.repository.FriendRepository;
import ourmap.demo.repository.NewMessageRepository;
import ourmap.demo.repository.OldMessageRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final MessageService messageService;
    private final FriendRepository friendRepository;
    private final NewMessageRepository newMessageRepository;
    private final OldMessageRepository oldMessageRepository;

    @Transactional
    public boolean request(Member sender, Member receiver) {
        List<Member> friendsId = findFriends(sender);
        for (Member friend : friendsId) {
            if (friend.getId() == receiver.getId()) {
                return false;
            }
        }
        MessageTypes messageTypes = MessageTypes.FRIENDREQUEST;
        if(messageService.isExistNewMessage(sender,receiver,messageTypes)){
            return false;
        }
        NewMessage newMessage = new NewMessage(sender, receiver, messageTypes);
        newMessageRepository.save(newMessage);
        return true;
    }

    public List<Member> findFriends(Member member) {
        List<Member> friends = new ArrayList<>();
        for (Friend friend : friendRepository.findByMember2Id(member.getId())) {
            friends.add(friend.getMember1());
        }
        for (Friend friend : friendRepository.findByMember1Id(member.getId())) {
            friends.add(friend.getMember2());
        }
        Collections.sort(friends);
        return friends;
    }

    public void acceptRequest(Long messageId){
        NewMessage requestMsg = newMessageRepository.findById(messageId).get();
        MessageTypes messageTypes = MessageTypes.FRIENDACCEPT;
        Member sender = requestMsg.getSender();
        Member receiver = requestMsg.getReceiver();
        messageService.newToOld(requestMsg);
        OldMessage oldMessage = new OldMessage(receiver, sender, messageTypes);
        oldMessageRepository.save(oldMessage);
        newMessageRepository.deleteById(messageId);
        Friend friend = new Friend(receiver, sender);
        friendRepository.save(friend);
    }

    public void rejectRequest(Long messageId) {
        NewMessage requestMsg = newMessageRepository.findById(messageId).get();
        MessageTypes messageTypes = MessageTypes.FRIENDREJECT;
        messageService.newToOld(requestMsg);
        OldMessage oldMessage = new OldMessage(requestMsg.getReceiver(), requestMsg.getSender(), messageTypes);

        oldMessageRepository.save(oldMessage);
        newMessageRepository.deleteById(messageId);
    }
}