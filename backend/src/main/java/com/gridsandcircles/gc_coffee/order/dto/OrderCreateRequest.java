package com.gridsandcircles.gc_coffee.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderCreateRequest(
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        String email,

        @NotBlank(message = "배송 주소는 필수입니다")
        String address,

        @NotBlank(message = "우편번호는 필수입니다")
        String zipCode,

        @NotEmpty(message = "상품을 최소 1개 이상 선택해야 합니다")
        List<OrderItemReq> orderItems
) {
    public record OrderItemReq(
            Long productId,
            int quantity
    ) {}
}