'use client';

import Link from 'next/link';
import { useSearchParams } from 'next/navigation';

export default function OrderSuccessPage() {
  const searchParams = useSearchParams();

  // URL 파라미터에서 데이터 추출
  const totalPrice = searchParams.get('totalPrice') || '0';
  
  // 주문 상품 목록 파싱 (데이터가 없으면 빈 배열로 초기화)
  const itemsParam = searchParams.get('items'); 
  const orderItems = itemsParam ? itemsParam.split('|') : [];

  return (
    <div className="flex items-center justify-center min-h-[calc(100vh-65px)] bg-black/10 p-6 font-sans">
      <div className="w-full max-w-sm bg-white rounded-2xl shadow-xl overflow-hidden animate-in fade-in zoom-in duration-300">
        <div className="p-8 flex flex-col items-center">
          
          {/* 1. 상단 체크 아이콘 */}
          <div className="w-16 h-16 bg-[#ebf7ed] rounded-full flex items-center justify-center mb-6">
            <div className="w-10 h-10 bg-[#4caf50] rounded-full flex items-center justify-center text-white text-xl">
              ✓
            </div>
          </div>

          {/* 2. 메인 타이틀 & 서브 타이틀 */}
          <h2 className="text-xl font-bold text-gray-900 mb-2">주문이 완료되었습니다</h2>
          <p className="text-sm text-gray-600 mb-8">주문해 주셔서 감사합니다</p>

          <hr className="w-full border-gray-100 mb-6" />

          {/* 4. 상품별 목록 (내역 없음 처리 추가) */}
          <div className="w-full space-y-4 mb-6">
            {orderItems.length === 0 ? (
              // URL에 상품 정보가 없을 경우 보여줄 Fallback UI
              <div className="py-4 text-center">
                <p className="text-sm text-gray-400">주문 내역을 불러올 수 없습니다.</p>
              </div>
            ) : (
              // 정상적으로 상품 정보가 있을 경우 렌더링
              orderItems.map((item, index) => {
                const [label, price] = item.includes(':') ? item.split(':') : [item, ''];
                return (
                  <div key={index} className="flex justify-between items-start gap-4 text-sm">
                    <span className="text-gray-700 leading-relaxed">{label}</span>
                    <span className="text-gray-900 font-bold whitespace-nowrap flex-shrink-0">
                      {price}
                    </span>
                  </div>
                );
              })
            )}
          </div>

          <hr className="w-full border-gray-100 mb-6" />

          {/* 6. 총 합계 */}
          <div className="w-full flex justify-between items-center mb-10">
            <span className="text-base font-bold text-gray-900">총 합계</span>
            <span className="text-lg font-bold text-gray-900 whitespace-nowrap">
              {Number(totalPrice.replace(/[^0-9]/g, '')).toLocaleString()}원
            </span>
          </div>

          {/* 7. 닫기 버튼 */}
          <Link
            href="/"
            className="w-full py-3 border border-gray-200 text-gray-600 font-medium rounded-xl hover:bg-gray-50 transition-all text-center"
          >
            닫기
          </Link>
        </div>
      </div>
    </div>
  );
}