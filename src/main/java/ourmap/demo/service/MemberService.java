package ourmap.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.entity.Member;
import ourmap.demo.repository.MemberRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    public Member findMemberByEmailAndProvider(String email, String provider) {
        return memberRepository.findByEmailAndProvider(email, provider).get();
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public void updateMemberInfo(Member member){
        memberRepository.save(member);
    }

    public boolean IsSameNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            return true;
        }
        return false;
    }
}
