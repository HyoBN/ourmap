package ourmap.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ourmap.demo.entity.Member;

import javax.persistence.Id;

@Getter
@NoArgsConstructor
public class MemberResponseDTO {
    @Id
    private Long id;
    private String nickname;
    private String email;
    private String provider;

    public MemberResponseDTO(Member member) {
        this.id=member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.provider = member.getProvider();
    }
}