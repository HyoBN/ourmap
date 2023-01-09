package ourmap.demo.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ourmap.demo.entity.Member;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberForm {
    private String name;
    private String email;
    private String provider;
    private String nickname;


    public MemberForm(Member member) {
        this.setName(member.getName());
        this.setProvider(member.getProvider());
        this.setEmail(member.getEmail());
        this.setNickname(member.getNickname());
    }

    //Builder Pattern
    public Member toMember() {
        return Member.builder()
                .name(name)
                .email(email)
                .nickname(name)
                .provider(provider)
                .build();
    }

}
