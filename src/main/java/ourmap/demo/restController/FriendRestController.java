package ourmap.demo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.config.jwt.SecurityUtil;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.MessageTypes;
import ourmap.demo.entity.NewMessage;
import ourmap.demo.service.FriendService;
import ourmap.demo.service.MemberService;
import ourmap.demo.service.MessageService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class FriendRestController {

    private final MemberService memberService;
    private final FriendService friendService;
    private final MessageService messageService;

    @GetMapping("/friends")
    public ResponseEntity friends(){
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        List<Member> friends = friendService.findFriends(member);
        return new ResponseEntity(friends, HttpStatus.OK);
    }

    @PostMapping("/friend")
    public ResponseEntity requestFriend(@RequestBody Map<String, String> request) {
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        try{
            Member receiver = memberService.findByNickname(request.get("receiver")).get();
            if (member.getId() == receiver.getId()) {
                // 자기 자신에게 요청을 한 경우.
                return new ResponseEntity<>("self", HttpStatus.BAD_REQUEST);
            } else if (friendService.request(member, receiver)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                // 이미 친구이거나 요청을 한 상태인 경우.
                return new ResponseEntity<>("already", HttpStatus.CONFLICT);
            }
        } catch (NoSuchElementException e){
            // 요청을 보내고자 하는 닉네임의 멤버가 존재하지 않는 경우.
            return new ResponseEntity<>("notExist", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/friendAccept")
    public ResponseEntity acceptFriendRequest(@RequestBody Map<String, Long> request) {
        friendService.acceptRequest(request.get("messageId"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/friendReject")
    public ResponseEntity rejectFriendRequest(Long messageId) {
        friendService.rejectRequest(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/friend/{memberId}")
    public ResponseEntity deleteFriend(@PathVariable Long memberId) {
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        Member friend = memberService.findById(memberId);
        friendService.deleteFriend(member, friend);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
