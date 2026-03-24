'use client';

import { useEffect, useState } from 'react';
import { Product, ApiResponse } from '@/app/type/product';

export default function AdminProductsPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // 컴포넌트 마운트 시 데이터 가져오기
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setIsLoading(true);
        const response = await fetch('http://localhost:8080/api/v1/products');

        if (!response.ok) {
          throw new Error('네트워크 응답에 문제가 발생했습니다.');
        }

        const result = await response.json();

        // 백엔드가 배열을 보냈을 경우
        if (Array.isArray(result)) {
          setProducts(result);
        }
        // ApiResponse 형태로 보냈을 경우
        else if (result.success !== undefined) {
          if (result.success) {
            // data가 배열인지, 아니면 페이징 content인지 체크
            if (Array.isArray(result.data)) {
              setProducts(result.data); // data가 배열이면 바로 삽입
            } else if (result.data && Array.isArray(result.data.content)) {
              setProducts(result.data.content); // data.content가 배열이면 삽입 (페이징 시)
            } else {
              setProducts([]); // 둘 다 아니면 에러 방지를 위해 빈 배열 삽입
            }
          } else {
            setError(result.error?.message || '데이터를 불러오는데 실패했습니다.');
          }
        } else {
          setProducts([]);
        }
      } catch (err) {
        console.error('API 연동 에러:', err);
        setError('백엔드 서버 연결을 확인해주세요.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchProducts();
  }, []);

  // 로딩 상태 UI
  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-[#ba9470] font-bold animate-pulse">원두 정보를 가져오는 중...</div>
      </div>
    );
  }

  // 에러 발생 시 UI
  if (error) {
    return (
      <div className="bg-red-50 p-6 rounded-xl border border-red-100 text-red-600 font-medium">
        ⚠️ {error}
      </div>
    );
  }

  return (
    <div className="space-y-8">
      {/* 상단 타이틀 및 등록 버튼 */}
      <div className="flex justify-between items-end">
        <div>
          <h2 className="text-3xl font-black text-[#222222]">상품 관리</h2>
          <p className="text-gray-500 mt-1 font-medium">판매 중인 원두 상품을 실시간으로 관리합니다.</p>
        </div>
        <button className="bg-[#ba9470] text-white px-6 py-3 rounded-lg hover:bg-[#a67c52] shadow-md transition-all font-bold">
          + 새 상품 등록
        </button>
      </div>

      {/* 실데이터 렌더링 테이블 */}
      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              {['ID', '상품명', '가격', '재고 상태', '관리'].map((header) => (
                <th
                  key={header}
                  className={`px-8 py-5 text-xs font-bold text-gray-400 uppercase tracking-widest ${header === '관리' ? 'text-center' : 'text-left'
                    }`}
                >
                  {header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-100">
            {products.length === 0 ? (
              <tr>
                <td colSpan={5} className="px-8 py-10 text-center text-gray-400 font-medium">
                  등록된 상품이 없습니다.
                </td>
              </tr>
            ) : (
              products.map((product) => (
                <tr key={product.id} className="hover:bg-[#ba9470]/5 transition-colors">
                  <td className="px-8 py-5 whitespace-nowrap text-sm text-gray-500 font-medium">#{product.id}</td>
                  <td className="px-8 py-5 whitespace-nowrap text-sm font-bold text-[#222222]">{product.name}</td>
                  <td className="px-8 py-5 whitespace-nowrap text-sm text-gray-600">{product.price.toLocaleString()}원</td>
                  <td className="px-8 py-5 whitespace-nowrap">
                    {product.stock > 0 ? (
                      <span className="px-3 py-1 text-xs font-bold bg-[#ba9470]/10 text-[#ba9470] rounded-md">
                        재고 있음 ({product.stock})
                      </span>
                    ) : (
                      <span className="px-3 py-1 text-xs font-bold bg-gray-100 text-gray-400 rounded-md">
                        품절
                      </span>
                    )}
                  </td>
                  <td className="px-8 py-5 whitespace-nowrap text-sm font-bold space-x-6 text-center">
                    <button className="text-[#ba9470] hover:text-[#a67c52] transition-colors">수정</button>
                    <button className="text-gray-300 hover:text-red-500 transition-colors">삭제</button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}