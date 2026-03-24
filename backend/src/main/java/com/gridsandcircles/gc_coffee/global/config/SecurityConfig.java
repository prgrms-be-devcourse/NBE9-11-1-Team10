package com.gridsandcircles.gc_coffee.global.config;

import com.gridsandcircles.gc_coffee.global.jwt.JwtFilter;
import com.gridsandcircles.gc_coffee.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public org.springframework.security.web.SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 설정 끄기 (Rest API로 불필요)
                .csrf(AbstractHttpConfigurer::disable)

                // 세션 사용 X (JWT 기반으로 STATELESS 설정)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/products/**").permitAll() // 상품 조회는 누구나 가능
                        .requestMatchers("/api/v1/members/login").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN") // 관리자 API는 ADMIN만 가능
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )

                // JWT 필터를 시큐리티 필터 체인에 등록
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}