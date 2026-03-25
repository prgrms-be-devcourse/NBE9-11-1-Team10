package com.gridsandcircles.gc_coffee.member.dto;

import com.gridsandcircles.gc_coffee.entity.Member;
import com.gridsandcircles.gc_coffee.entity.Role;

public record MemberResponse(
        Long id,
        String email,
        Role role
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getRole()
        );
    }
}