"use client";

import Image from "next/image";
import { useCart } from "../context/CartContext";

export default function ProductList() {
  const { products, updateQuantity } = useCart();

  return (
    <section>
      {/* 상태 메시지 처리 */}
      {products.length === 0 && (
        <p>등록된 상품이 없습니다.</p>
      )}

      {/* 상품 그리드 영역 */}
      <div className="grid grid-cols-2 gap-x-6 gap-y-8">
        {products.map((product) => (
          <article
            key={product.id}
            className="bg-white border border-[#E9E1D1] rounded-xl overflow-hidden shadow-sm flex flex-col"
          >
            {/* 이미지 영역 */}
            <div className="bg-[#F6F4F0] h-[180px] flex items-center justify-center text-[#908D86]">
              <Image
                src="/images/colombia.png"
                alt={product.name}
                width={130}
                height={130}
                className="object-contain"
              />
            </div>

            {/* 텍스트 및 버튼 영역 */}
            <div className="p-4 flex-grow flex flex-col">
              <h2 className="text-lg font-semibold text-[#1D1D1D] mb-1">
                {product.name}
              </h2>

              <p className="text-base text-[#3A3A3A] mb-4">
                {product.price.toLocaleString()}원
              </p>

              {/* 장바구니 담기 버튼 */}
              <div className="mt-auto flex items-center gap-4">
                <button
                  type="button"
                  onClick={() => updateQuantity(product.id, 1)}
                  className="border border-[#D1CABF] rounded-lg w-full h-8 text-base flex items-center justify-center text-[#555] active:bg-gray-100"
                >
                  장바구니 담기
                </button>
              </div>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
}