package com.gridsandcircles.gc_coffee.member.controller;

import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.member.dto.MemberRequest;
import com.gridsandcircles.gc_coffee.member.dto.MemberResponse;
import com.gridsandcircles.gc_coffee.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<MemberResponse>> createOrFindMember(
            @RequestBody MemberRequest request){
        MemberResponse response = memberservice.findOrCreateMember(request);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

}
