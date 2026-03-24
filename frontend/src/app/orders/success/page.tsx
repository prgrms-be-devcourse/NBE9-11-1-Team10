'use client';

import Link from 'next/link';
import { useSearchParams } from 'next/navigation';

export default function OrderSuccessPage() {
  const searchParams = useSearchParams();

  // URL 파라미터에서 데이터 추출
  const totalPrice = searchParams.get('totalPrice') || '0';
  
  // 주문 상품 목록 파싱 (예: "에티오피아 예가체프 x2:30,000원,과테말라 안티구아 x1:14,000원")
  const itemsParam = searchParams.get('items'); 
  const orderItems = itemsParam 
    ? itemsParam.split(',') 
    : ['에티오피아 예가체프 x2:30,000원', '과테말라 안티구아 x1:14,000원'];

  return (
    // 배경을 어둡게 처리하여 모달 느낌을 살림
    <div className="flex items-center justify-center min-h-[calc(100vh-65px)] bg-black/10 p-6 font-sans">
      <div className="w-full max-w-sm bg-white rounded-2xl shadow-xl overflow-hidden animate-in fade-in zoom-in duration-300">
        <div className="p-8 flex flex-col items-center">
          
          {/* 1. 상단 체크 아이콘 (사진의 녹색 원형 체크) */}
          <div className="w-16 h-16 bg-[#ebf7ed] rounded-full flex items-center justify-center mb-6">
            <div className="w-10 h-10 bg-[#4caf50] rounded-full flex items-center justify-center text-white text-xl">
              ✓
            </div>
          </div>

          {/* 2. 메인 타이틀 & 서브 타이틀 */}
          <h2 className="text-xl font-bold text-gray-900 mb-2">주문이 완료되었습니다</h2>
          <p className="text-sm text-gray-600 mb-8">주문해 주셔서 감사합니다</p>

          {/* 3. 첫 번째 구분선 */}
          <hr className="w-full border-gray-100 mb-6" />

          {/* 4. 상품별 목록 (왼쪽: 상품명x수량, 오른쪽: 가격) */}
          <div className="w-full space-y-4 mb-6">
            {orderItems.map((item, index) => {
              const [label, price] = item.includes(':') ? item.split(':') : [item, ''];
              return (
                <div key={index} className="flex justify-between items-center text-sm">
                  <span className="text-gray-700">{label}</span>
                  <span className="text-gray-900 font-medium">{price}</span>
                </div>
              );
            })}
          </div>

          {/* 5. 두 번째 구분선 */}
          <hr className="w-full border-gray-100 mb-6" />

          {/* 6. 총 합계 (사진처럼 굵게 표시) */}
          <div className="w-full flex justify-between items-center mb-10">
            <span className="text-base font-bold text-gray-900">총 합계</span>
            <span className="text-lg font-bold text-gray-900">
              {Number(totalPrice.replace(/[^0-9]/g, '')).toLocaleString()}원
            </span>
          </div>

          {/* 7. 닫기 버튼 (라운드 처리된 깔끔한 버튼) */}
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