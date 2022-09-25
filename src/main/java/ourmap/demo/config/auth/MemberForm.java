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


    //Builder Pattern
    public Member toMember() {
        return Member.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .build();
    }

}
