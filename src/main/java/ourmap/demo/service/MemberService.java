package ourmap.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ourmap.demo.entity.Member;
import ourmap.demo.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long findMemberId(String email, String provider) {
        Member member = memberRepository.findByEmailAndProvider(email, provider).get();
        return member.getId();

    }

}
