package com.gridsandcircles.gc_coffee.member.service;

import com.gridsandcircles.gc_coffee.entity.Member;
import com.gridsandcircles.gc_coffee.global.exception.BusinessException;
import com.gridsandcircles.gc_coffee.global.exception.ErrorCode;
import com.gridsandcircles.gc_coffee.global.jwt.JwtProvider;
import com.gridsandcircles.gc_coffee.member.dto.LoginRequest;
import com.gridsandcircles.gc_coffee.member.dto.MemberRequest;
import com.gridsandcircles.gc_coffee.member.dto.MemberResponse;
import com.gridsandcircles.gc_coffee.member.dto.TokenResponse;
import com.gridsandcircles.gc_coffee.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse findOrCreateMember(MemberRequest request){

        // 이메일로 기존 회원 조회, 없으면 새로운 Member 객체 생성 후 DB 저장
        Member member = memberRepository.findByEmail(request.email())
                                        .orElseGet(()->memberRepository.save(new Member(request.email())));

        return MemberResponse.from(member);
    }


    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(loginRequest.email())
                                        .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 일치 확인 (암호화된 비밀번호와 비교)
        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        // 인증 객체 생성 및 토큰 발급
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority(member.getRole().name()))
        );

        String token = jwtProvider.createToken(authentication);
        return TokenResponse.of(token);
    }

}
