package ourmap.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.config.auth.MemberForm;
import ourmap.demo.entity.Member;
import ourmap.demo.repository.MemberRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;


    public Long findMemberIdByEmailAndProvider(String email, String provider) {
        Member member = memberRepository.findByEmailAndProvider(email, provider).get();
        return member.getId();
    }
    //나중에 리팩토링하기.
    public Member findMemberByEmailAndProvider(String email, String provider) {
        return memberRepository.findByEmailAndProvider(email, provider).get();
    }

    public String findNicknameById(Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getNickname();
    }

    public Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).get();
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
