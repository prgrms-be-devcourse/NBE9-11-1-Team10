"use client";

import { useEffect, useMemo, useState } from "react";
import Image from "next/image";
import { useCart } from "../context/CartContext";

export default function ProductList() {
  const { products, updateQuantity } = useCart();

  const [keyword, setKeyword] = useState("");
  const [sortType, setSortType] = useState("latest");
  const ITEMS_PER_PAGE = 4;
  const [currentPage, setCurrentPage] = useState(1);

  const filteredProducts = useMemo(() => {
    return products.filter((product) =>
      product.name.includes(keyword.trim())
    );
  }, [products, keyword]);

  const sortedProducts = useMemo(() => {
    const copied = [...filteredProducts];

    switch (sortType) {
      case "nameAsc":
        return copied.sort((a, b) => a.name.localeCompare(b.name));
      case "priceAsc":
        return copied.sort((a, b) => a.price - b.price);
      case "priceDesc":
        return copied.sort((a, b) => b.price - a.price);
      case "latest":
      default:
        return copied;
    }
  }, [filteredProducts, sortType]);

  const totalPages = Math.ceil(sortedProducts.length / ITEMS_PER_PAGE);

  const paginatedProducts = useMemo(() => {
    const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
    const endIndex = startIndex + ITEMS_PER_PAGE;
    return sortedProducts.slice(startIndex, endIndex);
  }, [sortedProducts, currentPage]);

  useEffect(() => {
    setCurrentPage(1);
  }, [keyword, sortType]);

  return (
    <section>
      {/* 검색창 영역 */}
      <div className="mb-6 flex gap-2">
        <input
          type="text"
          placeholder="상품명을 검색하세요"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          className="border border-[#D1CABF] rounded-lg px-3 py-2 w-full"
        />

        <button
          type="button"
          onClick={() => setKeyword("")}
          className="border border-[#D1CABF] rounded-lg px-3 py-2 whitespace-nowrap"
        >
          초기화
        </button>
      </div>

      {/* 정렬 버튼 */}
      <div className="mb-6 flex gap-2 flex-wrap">
        <button
          type="button"
          onClick={() => setSortType("latest")}
          className={`border rounded-lg px-3 py-2 ${sortType === "latest"
            ? "bg-[#E9E1D1] border-[#C9BDA7]"
            : "border-[#D1CABF]"
            }`}
        >
          최신순
        </button>

        <button
          type="button"
          onClick={() => setSortType("nameAsc")}
          className={`border rounded-lg px-3 py-2 ${sortType === "nameAsc"
            ? "bg-[#E9E1D1] border-[#C9BDA7]"
            : "border-[#D1CABF]"
            }`}
        >
          이름순
        </button>

        <button
          type="button"
          onClick={() => setSortType("priceAsc")}
          className={`border rounded-lg px-3 py-2 ${sortType === "priceAsc"
            ? "bg-[#E9E1D1] border-[#C9BDA7]"
            : "border-[#D1CABF]"
            }`}
        >
          낮은 가격순
        </button>

        <button
          type="button"
          onClick={() => setSortType("priceDesc")}
          className={`border rounded-lg px-3 py-2 ${sortType === "priceDesc"
            ? "bg-[#E9E1D1] border-[#C9BDA7]"
            : "border-[#D1CABF]"
            }`}
        >
          높은 가격순
        </button>
      </div>

      {/* 상태 메시지 처리 */}
      {products.length === 0 && <p>등록된 상품이 없습니다.</p>}
      {products.length > 0 && filteredProducts.length === 0 && <p>검색 결과가 없습니다.</p>}

      {/* 상품 그리드 영역 */}
      <div className="grid grid-cols-2 gap-x-6 gap-y-8">
        {paginatedProducts.map((product) => (
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
      {/* 페이지 버튼 */}
      {totalPages > 1 && (
        <div className="flex justify-center items-center gap-2 mt-8">
          <button
            type="button"
            onClick={() => setCurrentPage((prev) => prev - 1)}
            disabled={currentPage === 1}
            className="border border-[#D1CABF] rounded-lg px-3 py-2 disabled:opacity-40"
          >
            이전
          </button>

          {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
            <button
              key={page}
              type="button"
              onClick={() => setCurrentPage(page)}
              className={`border rounded-lg px-3 py-2 ${currentPage === page
                ? "bg-[#E9E1D1] border-[#C9BDA7]"
                : "border-[#D1CABF]"
                }`}
            >
              {page}
            </button>
          ))}

          <button
            type="button"
            onClick={() => setCurrentPage((prev) => prev + 1)}
            disabled={currentPage === totalPages}
            className="border border-[#D1CABF] rounded-lg px-3 py-2 disabled:opacity-40"
          >
            다음
          </button>
        </div>
      )}
    </section>
  );
}