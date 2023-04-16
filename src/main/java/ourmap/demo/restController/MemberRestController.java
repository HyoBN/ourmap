package ourmap.demo.restController;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ourmap.demo.config.jwt.JwtTokenProvider;
import ourmap.demo.config.jwt.SecurityUtil;
import ourmap.demo.entity.Member;
import ourmap.demo.service.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MemberRestController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/kakaoLogin")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> user) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("jwt", jwtTokenProvider.createToken(user.get("email"), "kakao"));
        try {
            //로그인 성공 - 기존 회원
            Member member = memberService.findMemberByEmailAndProvider(user.get("email"), "kakao");
            responseMap.put("nickname", member.getNickname());
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // 회원가입
            String nickname = user.get("nickname");
            Member newMember = new Member(nickname, user.get("email"), "kakao", nickname);
            HttpStatus httpStatus = HttpStatus.OK;
            if (memberService.existSameNickname(nickname) || !memberService.isPossibleNickname(nickname)) {
                // 닉네임 중복되거나 공백, 특수문자(괄호, 온점, 콤마, 하이픈 제외) - 랜덤 닉네임 생성 후 닉네임 변경 페이지로 이동하도록.
                String randomNickname = RandomStringUtils.random(8, true, true);
                newMember.setNickname(randomNickname);
                httpStatus = HttpStatus.SEE_OTHER;
            }
            memberService.saveOrUpdateMember(newMember);
            responseMap.put("nickname", newMember.getNickname());
            return new ResponseEntity<>(responseMap, httpStatus);
        }
    }

    @PostMapping("/nickname")
    public ResponseEntity changeNickname(@RequestBody Map<String, String> nickname) {
        String memberEmail = SecurityUtil.getMemberEmail();
        Member member = memberService.findMemberByEmailAndProvider(memberEmail, "kakao");
        String newNickname = nickname.get("nickname");
        if (memberService.existSameNickname(newNickname)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else if (memberService.isPossibleNickname(newNickname)) {
            member.updateNickname(newNickname);
            memberService.saveOrUpdateMember(member);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}