package cj.task.sleact.configuration.filter;

import cj.task.sleact.core.member.controller.dto.request.LoginReq;
import cj.task.sleact.core.member.controller.dto.response.MemberInfoRes;
import cj.task.sleact.core.member.service.UserAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserAuthenticationService userAuthenticationService;
    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserAuthenticationService userAuthenticationService,
                                Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.userAuthenticationService = userAuthenticationService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginReq login = new ObjectMapper().readValue(request.getInputStream(), LoginReq.class);

            // 사용자가 입력한 email 과 passwd 를 AuthenticationToken 으로 변환한 다음 인증 작업 처리
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        MemberInfoRes memberInfo = userAuthenticationService.loadMemberInfoByUsername(userName);

        String token = Jwts.builder()
                .setSubject(memberInfo.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(Objects.requireNonNull(env.getProperty("token.expire")))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        String result = new ObjectMapper().writeValueAsString(memberInfo);
        response.addHeader("token", token);
        response.getWriter().write(result);
    }

}
