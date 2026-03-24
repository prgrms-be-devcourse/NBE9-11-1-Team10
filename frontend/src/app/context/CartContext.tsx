"use client";
import React, { createContext, useContext, useState, useMemo, useEffect } from "react";
import { Product } from "../type/product";
import { toast } from "sonner";
// 커스텀 fetch 사용
import { customFetch } from "../api/customFetch";

interface CartContextType {
  cart: { [key: number]: number };
  products: Product[];
  updateQuantity: (id: number, delta: number) => Promise<void>;
  totalAmount: number;
  resetCart: () => void;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export function CartProvider({ children }: { children: React.ReactNode }) {
  //장바구니 상품 목록
  // cart 객체 예시 입니다.
  // { 
  //     1(상품 아이디) : 2(수량),  // 에티오피아 예가체프 2개
  //     2(상품 아이디) : 1(수량),  // 브라질 산토스 1개
  //     4(상품 아이디) : 5(수량),  // 과테말라 안티구아 5개
  // }
  const [cart, setCart] = useState<{ [key: number]: number }>({});

  //상품 목록
  //products 객체 예시 입니다.
  // [
  //     { id: 1, name: "에티오피아 예가체프", price: 15000, stock: 10 },  // 상품 아이디 1, 상품 이름 에티오피아 예가체프, 상품 가격 15000, 상품 재고 10
  //     { id: 2, name: "콜롬비아 수프리모", price: 13000, stock: 3 },
  //     { id: 3, name: "브라질 산토스", price: 12000, stock: 11 },
  //     { id: 4, name: "과테말라 안티구아", price: 14000, stock: 12 },
  // ]
  const [products, setProducts] = useState<Product[]>([]);

  //페이지 로드 시 딱 한 번 실행 , 백엔드 상품 목록 조회 api 호출
  useEffect(() => {
    customFetch(`/api/v1/products?size=100`)
      .then(res => {
        if (!res.ok) throw new Error("상품 목록 로드 실패");
        return res.json();
      })
      .then(response => {
        // response: ApiResponse 객체
        // response.data: PageResponse 객체
        // response.data.content: 진짜 상품 배열

        const actualProducts = response.data?.content || [];
        setProducts(actualProducts);
      })
      .catch(e => {
        console.error("데이터 로드 에러:", e);
        setProducts([]); // 에러 시 빈 배열로 초기화하여 reduce 에러 방지
      });
  }, []);

  // 총 합을 구하는 부분
  const totalAmount = useMemo(() => {
    return products.reduce((acc, p) => acc + (p.price * (cart[p.id] || 0)), 0);
  }, [cart, products]);

  //카트 내부 리셋
  const resetCart = () => {
    setCart({});
  };

  //상품 수량 변경
  const updateQuantity = async (productId: number, quantity_delta: number) => {

    //현재 cart에 들어있는 상품의 수량을 가져옴
    const currentQty = cart[productId] || 0;

    //현재 수량에 변동값을 더해서 새로운 수량을 계산
    const targetQty = currentQty + quantity_delta;

    //장바구니의 +,- 버튼중 - 버튼을 클릭할때, 수량이 줄어어드는 경우, 수량이 0이 되면 장바구니에서 상품을 제거
    if (quantity_delta < 0) {
      setCart((prev) => ({ ...prev, [productId]: Math.max(0, targetQty) }));
      return;
    }

    //장바구니의 +,- 버튼중 + 버튼을 클릭할때, 재고가 부족하면 장바구니에 상품을 추가하지 않음
    try {
      //백엔드 재고 조회 api 호출
      const response = await customFetch(`/api/v1/products/${productId}/stock?requestQuantity=${targetQty}`);

      //백엔드 재고 조회 api 호출 결과가 실패하면 재고가 부족하다는 메시지를 보여줌
      if (!response.ok) {
        const result = await response.json();
        toast.error(result.error.message || "재고가 부족합니다.");
        return;
      }

      //백엔드 재고 조회 api 호출 결과가 성공하면 장바구니에 상품을 추가
      setCart((prev) => ({ ...prev, [productId]: targetQty }));
    } catch (e) {
      toast.error("서버와 통신 중 오류가 발생했습니다.");
    }
  };

  return (
    <CartContext.Provider value={{ cart, products, updateQuantity, totalAmount, resetCart }}>
      {children}
    </CartContext.Provider>
  );

}

//장바구니 컨텍스트 사용을 위한 훅
export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) throw new Error("useCart must be used within CartProvider");
  return context;
};