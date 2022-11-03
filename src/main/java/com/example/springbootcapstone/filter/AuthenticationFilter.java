package com.example.springbootcapstone.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springbootcapstone.Document.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManagerBean) {
        this.authenticationManager=authenticationManagerBean;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        log.info("username: {} and password: {}",username,password);
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user= (User) authResult.getPrincipal();
        String username = request.getParameter("username");
        Algorithm algorithm=Algorithm.HMAC256("secretKey".getBytes());
        String accessToken;
        String refreshToken;
        accessToken= JWT.create().withSubject(username)
                .withClaim("first name",user.getFirstName())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withIssuer(request.getRequestURI().toString())
                .withExpiresAt(new Date(System.currentTimeMillis()+(10* 60* 60 * 60)))
                .sign(algorithm);

        refreshToken=JWT.create().withSubject(username)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+(60 * 60 * 60)))
                .sign(algorithm);

        response.setHeader("access_token",accessToken);
        response.setHeader("refresh_token",refreshToken);

        Map<String,String> tokens=new HashMap<>();
        tokens.put("access-token",accessToken);
        tokens.put("refresh-token",refreshToken);



        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
    }


}
