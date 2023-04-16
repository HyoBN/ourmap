package ourmap.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.entity.Member;
import ourmap.demo.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberByEmailAndProvider(String email, String provider) {
        return memberRepository.findByEmailAndProvider(email, provider).get();
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public void saveOrUpdateMember(Member member){
        memberRepository.save(member);
    }

    public boolean existSameNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean isPossibleNickname(String nickname) {
        if (!nickname.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|(|)|.|,|-]*")) {
            return false;
        } else return true;
    }
}
