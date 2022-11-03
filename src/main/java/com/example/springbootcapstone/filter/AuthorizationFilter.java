package com.example.springbootcapstone.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/login")){
            filterChain.doFilter(request,response);
        }
        String AuthorizationHeader=request.getHeader(AUTHORIZATION);
        if(AuthorizationHeader!=null && AuthorizationHeader.startsWith("Bearer ")){
            try{

                String token= AuthorizationHeader.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256("secretKey".getBytes());
                JWTVerifier verifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT=verifier.verify(token);
                String username=decodedJWT.getSubject();
                String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                Collection<GrantedAuthority> authorities=new ArrayList<>();
                stream(roles).forEach(role->{
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                //Collection<GrantedAuthority> authorities1= List.of(decodedJWT.getClaim("roles").asArray(GrantedAuthority.class));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(username,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request,response);

            }catch (Exception e){
                log.error("Error logging in {}",e.getMessage());
                Map<String,String> errors=new HashMap<>();
                response.setStatus(FORBIDDEN.value());
                errors.put("error",e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),errors);

            }
        }else {
            filterChain.doFilter(request,response);
        }


    }

}
