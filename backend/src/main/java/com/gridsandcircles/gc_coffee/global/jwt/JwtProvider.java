package com.gridsandcircles.gc_coffee.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

    @Slf4j
    @Component
    public class JwtProvider implements InitializingBean {

        private static final String AUTHORITIES_KEY = "auth";

        private final String secret;
        private final long tokenValidityInMilliseconds;
        private Key key;

        // application.yaml에 세팅한 환경변수 값 가져오기
        public JwtProvider(
                @Value("${jwt.secret}") String secret,
                @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
            this.secret = secret;
            this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        }

        // 빈 생성 -> 의존성 주입 -> 비밀키 암호화 키로 변경(Base64 디코딩)
        @Override
        public void afterPropertiesSet() {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            this.key = Keys.hmacShaKeyFor(keyBytes);
        }

        // 토큰 생성(로그인 성공 시 실행)
        public String createToken(Authentication authentication) {
            String authorities = authentication.getAuthorities().stream()
                                               .map(GrantedAuthority::getAuthority)
                                               .collect(Collectors.joining(","));

            long now = (new Date()).getTime();
            Date validity = new Date(now + this.tokenValidityInMilliseconds);

            return Jwts.builder()
                       .subject(authentication.getName())      // 토큰 제목 (이메일)
                       .claim(AUTHORITIES_KEY, authorities)    // 권한 정보 (예: ROLE_ADMIN)
                       .signWith(key)
                       .expiration(validity)                   // 만료 시간
                       .compact();
        }

        // 토큰 복호화 및 인증 객체(Authentication) 반환
        public Authentication getAuthentication(String token) {
            Claims claims = Jwts.parser()
                                .verifyWith((SecretKey) key)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                          .map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());

            org.springframework.security.core.userdetails.User principal =
                    new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        }

        // 토큰 유효성 검증
        public boolean validateToken(String token) {
            try {
                Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                log.info("유효하지 않은 JWT 토큰입니다: {}", e.getMessage());
            }
            return false;
        }
    }

