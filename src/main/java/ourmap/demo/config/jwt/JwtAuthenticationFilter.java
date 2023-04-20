package ourmap.demo.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider   jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //임시로 로그인 요청에 대해서는 토큰 검증안하도록 막아놓음.
        //
        if (request.getServletPath().equals("/test/kakaoLogin")) {
            chain.doFilter(request, response);
        }else {
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("유효한 토큰입니다.");
            } else {
                System.out.println("유효하지 않은 토큰입니다.");
                //failure Handler 만들어서 연결하기.
            }
            chain.doFilter(request, response);
        }
    }
}
