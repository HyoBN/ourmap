package ourmap.demo.config.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        MemberForm memberForm = new MemberForm();
        memberForm.setName((String) attributes.get("name"));
        memberForm.setEmail((String) attributes.get("email"));
        return memberForm;
    }),

    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println(response);
        MemberForm memberForm = new MemberForm();
        memberForm.setName((String) response.get("name"));
        memberForm.setEmail(((String) response.get("email")));
        return memberForm;
    }),

    KAKAO("kakao", (attributes) -> {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        MemberForm memberForm = new MemberForm();
        memberForm.setName((String) kakaoProfile.get("nickname"));
        memberForm.setEmail((String) kakaoAccount.get("email"));
        return memberForm;
    });

    private final String registrationId;
    private final Function<Map<String, Object>, MemberForm> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, MemberForm> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static MemberForm extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}