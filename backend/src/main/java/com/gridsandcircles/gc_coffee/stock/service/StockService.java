package com.gridsandcircles.gc_coffee.stock.service;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.global.exception.BusinessException;
import com.gridsandcircles.gc_coffee.global.exception.ErrorCode;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import com.gridsandcircles.gc_coffee.stock.dto.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductRepository productRepository;

    public void validateStockAvailability(Long productId, int requestQuantity) {
        StockResponse stockInfo = getProductStock(productId);

        //장바구니에서 들어오는 요청 수량이 재고 수량보다 많을 경우 "재고가 부족합니다"라는 메시지를 전송.
        if (requestQuantity > stockInfo.availableStock()) {
            throw new BusinessException(ErrorCode.OUT_OF_STOCK);
        }
    }

    public StockResponse getProductStock(Long productId) {

        //id로 해당 상품이 있는지 조회, 없으면 상품을 못찾겠다는 오류 메시지 반환
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return new StockResponse(product.getId(),product.getStock());
    }

    //재고 검증 및 감소 로직
    public void decreaseStock(Long productId, int quantity) {
        //해당 상품이 있는지 확인합니다.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
       //재고를 줄이고 테이블의 총 재고 개수를 최신화한다.
        product.removeStock(quantity);

    }

    //재고 검증 및 증가 로직
    public void increaseStock(Long productId, int quantity) {
        //해당 상품이 있는지 확인합니다.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        //들어온 quantity만큼 총 재고를 늘린다.
        product.addStock(quantity);
    }
}
