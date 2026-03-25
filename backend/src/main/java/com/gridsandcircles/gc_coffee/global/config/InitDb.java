package com.gridsandcircles.gc_coffee.global.config;

import com.gridsandcircles.gc_coffee.entity.Member;
import com.gridsandcircles.gc_coffee.entity.Role;
import com.gridsandcircles.gc_coffee.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDb implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // DB에 admin 계정이 없는 경우에만 실행
        if (memberRepository.findByEmail("admin@test.com").isEmpty()) {

            Member admin = new Member(
                    "admin@test.com",
                    passwordEncoder.encode("1234"),
                    Role.ADMIN
            );

            memberRepository.save(admin);
            log.info("초기 관리자 계정이 생성되었습니다. (admin@test.com / 1234)");
        }
    }
}