"use client"

import { useEffect, useState } from "react";
import { Product } from "../type/product";
import { useCart } from "../context/CartContext";
import Image from "next/image";

export default function ProductList() {

  const { cart, updateQuantity } = useCart();

  // // 더미데이터
  // const mockProducts: Product[] = [
  //   { id: 1, name: "에티오피아 예가체프", price: 15000, stock: 5 },
  //   { id: 2, name: "콜롬비아 수프리모", price: 13000, stock: 1 },
  //   { id: 3, name: "브라질 산토스", price: 12000, stock: 3 },
  //   { id: 4, name: "과테말라 안티구아", price: 14000, stock: 2 },
  // ];

  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fetchProducts = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/v1/products`);

      if (!res.ok) {
        throw new Error("상품 목록 조회 실패");
      }

      const data = await res.json();

      setProducts(data.data.content);
    } catch (e) {
      setError("에러 발생");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  // // 더미데이터 띄우기용
  // useEffect(() => {
  //   setProducts(mockProducts);
  // }, []);

  return (
    <section>
      {/* 상태 메시지 처리 */}
      {loading && <p>로딩 중...</p>}
      {error && <p>{error}</p>}
      {!loading && !error && products.length === 0 && (
        <p>등록된 상품이 없습니다.</p>
      )}

      {/* 상품 그리드 영역 */}
      <div className="grid grid-cols-2 gap-x-6 gap-y-8">
        {products.map((product) => {
          const currentQuantity = cart[product.id] || 0;

          return (
            <article 
              key={product.id} 
              className="bg-white border border-[#E9E1D1] rounded-xl overflow-hidden shadow-sm flex flex-col"
            >
              {/* 이미지 영역 */}
              <div className="bg-[#F6F4F0] h-[180px] flex items-center justify-center text-[#908D86]">
                <Image
                  src="/images/colombia.png"
                  alt="콜롬비아 수프리모"
                  width={130}
                  height={130}
                  className="object-contain"
                />
              </div>

              {/* 텍스트 및 버튼 영역 */}
              <div className="p-4 pt-4 flex-grow flex flex-col">
                <h2 className="text-lg font-semibold text-[#1D1D1D] mb-1">
                  {product.name}
                </h2>
                <p className="text-base text-[#3A3A3A] mb-4">
                  {product.price.toLocaleString()}원
                </p>

                {/* 수량 조절 버튼 */}
                <div className="mt-auto flex items-center gap-4">
                  <button
                    type="button"
                    onClick={() => updateQuantity(product.id, -1)}
                    className="border border-[#D1CABF] rounded-lg w-12 h-10 text-xl flex items-center justify-center text-[#555] active:bg-gray-100"
                  >
                    -
                  </button>

                  <span className="text-lg font-medium w-10 text-center text-[#1D1D1D]">
                    {currentQuantity}
                  </span>

                  <button
                    type="button"
                    onClick={() => updateQuantity(product.id, 1)}
                    className="border border-[#D1CABF] rounded-lg w-12 h-10 text-xl flex items-center justify-center text-[#555] active:bg-gray-100"
                  >
                    +
                  </button>
                </div>
              </div>
            </article>
          );
        })}
      </div>
    </section>
  );
}