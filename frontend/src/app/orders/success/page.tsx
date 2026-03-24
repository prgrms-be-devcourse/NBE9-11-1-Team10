'use client';

import Link from 'next/link';
import { useSearchParams } from 'next/navigation';

export default function OrderSuccessPage() {
  const searchParams = useSearchParams();

  // URL 파라미터에서 데이터 추출
  const totalPrice = searchParams.get('totalPrice') || '0';
  
  // 주문 상품 목록 파싱
  const itemsParam = searchParams.get('items'); 
  const orderItems = itemsParam 
    ? itemsParam.split('|') 
    : ['에티오피아 예가체프 x3:45,000원', '콜롬비아 수프리모 x1:13,000원'];

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

          {/* 4. 상품별 목록 (핵심 수정 영역) */}
          <div className="w-full space-y-4 mb-6">
            {orderItems.map((item, index) => {
              const [label, price] = item.includes(':') ? item.split(':') : [item, ''];
              return (
                <div key={index} className="flex justify-between items-start gap-4 text-sm">
                  {/* 상품명 영역: flex-grow로 남은 공간을 채우고 leading-relaxed로 가독성 확보 */}
                  <span className="text-gray-700 leading-relaxed">{label}</span>
                  
                  {/* 금액 영역: 
                      - whitespace-nowrap: 절대 줄바꿈 방지 (45,000원 한 줄 유지)
                      - flex-shrink-0: 상품명이 길어져도 금액 영역이 찌그러지지 않음
                      - font-bold: 영수증 느낌을 위해 굵게 처리
                  */}
                  <span className="text-gray-900 font-bold whitespace-nowrap flex-shrink-0">
                    {price}
                  </span>
                </div>
              );
            })}
          </div>

          <hr className="w-full border-gray-100 mb-6" />

          {/* 6. 총 합계 */}
          <div className="w-full flex justify-between items-center mb-10">
            <span className="text-base font-bold text-gray-900">총 합계</span>
            {/* 총 합계도 줄바꿈 방지 적용 */}
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