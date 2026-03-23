package com.gridsandcircles.gc_coffee.member.service;

import com.gridsandcircles.gc_coffee.entity.Member;
import com.gridsandcircles.gc_coffee.member.dto.MemberRequest;
import com.gridsandcircles.gc_coffee.member.dto.MemberResponse;
import com.gridsandcircles.gc_coffee.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse findOrCreateMember(MemberRequest request){

        // 이메일로 기존 회원 조회, 없으면 새로운 Member 객체 생성 후 DB 저장
        Member member = memberRepository.findByEmail(request.email())
                                        .orElseGet(()->memberRepository.save(new Member(request.email())));

        return MemberResponse.from(member);
    }
}
