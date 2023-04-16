package ourmap.demo.config.jwt;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityUtil {

    public static String getMemberEmail(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.equals(null) || authentication.getName().equals(null)) {
            throw new RuntimeException("Security Context에 저장된 인증 정보가 없습니다.");
        }
        return  String.valueOf(authentication.getName());
    }
}
