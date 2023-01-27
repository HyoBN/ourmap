package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.*;
import ourmap.demo.repository.FriendRepository;
import ourmap.demo.repository.MemberRepository;
import ourmap.demo.repository.NewMessageRepository;
import ourmap.demo.repository.OldMessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MessageService messageService;
    private final FriendRepository friendRepository;
    private final NewMessageRepository newMessageRepository;
    private final OldMessageRepository oldMessageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void request(Long senderId, Long receiverId) {
        MessageTypes messageTypes = MessageTypes.FRIENDREQUEST;
        Member sender = memberRepository.findById(senderId).get();
        Member receiver = memberRepository.findById(receiverId).get();
        NewMessage newMessage = new NewMessage(sender, receiver, messageTypes);
        newMessageRepository.save(newMessage);
    }

    public List<Long> findFriendsId(Long memberId) {
        List<Long> friendsId = new ArrayList<>();
        for (Friend friend : friendRepository.findByMember2Id(memberId)) {
            friendsId.add(friend.getMember1().getId());
        }
        for (Friend friend : friendRepository.findByMember1Id(memberId)) {
            friendsId.add(friend.getMember2().getId());
        }
        return friendsId;
    }

    public void acceptRequest(Long messageId){
        NewMessage requestMsg = newMessageRepository.findById(messageId).get();
        MessageTypes messageTypes = MessageTypes.FRIENDACCEPT;
        Member sender = requestMsg.getSender();
        Member receiver = requestMsg.getReceiver();
        messageService.newToOld(requestMsg.getId());
        OldMessage oldMessage = new OldMessage(receiver, sender, messageTypes);
        oldMessageRepository.save(oldMessage);
        newMessageRepository.deleteById(messageId);
        Friend friend = new Friend(receiver, sender);
        friendRepository.save(friend);
    }

    public void rejectRequest(Long messageId) {
        NewMessage requestMsg = newMessageRepository.findById(messageId).get();
        MessageTypes messageTypes = MessageTypes.FRIENDREJECT;
        messageService.newToOld(requestMsg.getId());
        OldMessage oldMessage = new OldMessage(requestMsg.getReceiver(), requestMsg.getSender(), messageTypes);

        oldMessageRepository.save(oldMessage);
        newMessageRepository.deleteById(messageId);
    }
}
