package cj.task.sleact.config.auth;

import cj.task.sleact.common.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()    // URL별 권한 관리
                .antMatchers("/").permitAll()   // "/" 전체 권한
                .antMatchers("/swagger-ui/**", "/docs/api.json").permitAll()   // swagger 전체 권한
                .antMatchers("/oauth2/authorization/google").permitAll()   // swagger 전체 권한
                .antMatchers("/api/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name()) // "/api/**" USER, ADMIN 권한
                .anyRequest().authenticated()   // 상기 요청 이외 요청들은 인증된 사용자만 허용
                .and()
                .logout().logoutSuccessUrl("/") // 로그아웃시 "/" 로 이동
                .and()
                .oauth2Login()  // 로그인 기능 설정
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                .userService(customOAuth2UserService);  // 소셜 로그인 성공시 수행될 UserService 인터페이스의 구현체 등록
    }

}
