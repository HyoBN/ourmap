//package ourmap.demo.config.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity //spring security 설정을 활성화시켜주는 어노테이션
//@RequiredArgsConstructor //final 필드 생성자 만들어줌
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final customOAuthService oAuthService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//                .logout().logoutSuccessUrl("/") //logout 요청시 홈으로 이동 - 기본 logout url = "/logout"
//                .and()
//                //HttpServletRequest를 사용하는 요청들에 대해 접근 제한을 설정하겠다는 의미.
////                .authorizeHttpRequests()
////                //"/index"에 대한 요청은 인증없이 접근을 허용하겠다는 의미.
////                .antMatchers("/index").permitAll()
////                //나머지 요청들에 대해서는 모두 인증이 필요하다는 의미.
////                .anyRequest().authenticated()
////                .and()
//                .oauth2Login() //OAuth2 로그인 설정 시작점
//                .defaultSuccessUrl("/home", true) //OAuth2 성공시 redirect
//                .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
//                .userService(oAuthService); //OAuth2 로그인 성공 시, 작업을 진행할 MemberService
//
//    }
//}