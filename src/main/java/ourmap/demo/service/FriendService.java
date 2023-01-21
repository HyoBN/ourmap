package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.MessageTypes;
import ourmap.demo.entity.NewMessage;
import ourmap.demo.repository.FriendRepository;
import ourmap.demo.repository.MemberRepository;
import ourmap.demo.repository.NewMessageRepository;
import ourmap.demo.repository.OldMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

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
        List<Long> friends = friendRepository.findMember1IdByMember2Id(memberId);
        friends.addAll(friendRepository.findMember2IdByMember1Id(memberId));
        return friends;
    }
}
