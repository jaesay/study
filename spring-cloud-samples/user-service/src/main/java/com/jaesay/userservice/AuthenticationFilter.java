package com.jaesay.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment env) {

        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            SignInRequest credentials = new ObjectMapper().readValue(request.getInputStream(), SignInRequest.class);

            // 사용자가 입력한 email, password를 spring security에서 사용할 수 있는 토큰으로 변환
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(), credentials.getPassword(), new ArrayList<>());

            // 인증 처리
            return getAuthenticationManager().authenticate(authToken);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 로그인을 성공했을 때 어떤 처리를 할지
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserEntity userEntity = userService.getUserDetailsByEmail(username);

        String jwtToken = Jwts.builder()
                .setSubject(String.valueOf(userEntity.getId()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.encode(env.getProperty("token.secret")))
                .compact();

        response.addHeader("token", jwtToken);
        response.addHeader("userId", String.valueOf(userEntity.getId()));
    }
}
