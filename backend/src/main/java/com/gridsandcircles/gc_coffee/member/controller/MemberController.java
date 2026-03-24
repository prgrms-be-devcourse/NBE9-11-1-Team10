package com.gridsandcircles.gc_coffee.member.controller;

import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.member.dto.MemberRequest;
import com.gridsandcircles.gc_coffee.member.dto.MemberResponse;
import com.gridsandcircles.gc_coffee.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberservice;

    @PostMapping
    public ApiResponse<MemberResponse> createOrFindMember(
            @RequestBody @Valid MemberRequest request){
        MemberResponse response = memberservice.findOrCreateMember(request);
        return ApiResponse.ok(response);
    }
}
