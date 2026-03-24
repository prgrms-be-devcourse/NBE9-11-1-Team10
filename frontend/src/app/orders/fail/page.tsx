'use client';

import Link from 'next/link';
import { useSearchParams } from 'next/navigation';

export default function OrderFailPage() {
  const searchParams = useSearchParams();
  
  // URL 파라미터에서 에러 메시지를 읽어옵니다. (예: ?message=재고가 부족합니다)
  const errorMessage = searchParams.get('message') || '주문 처리 중 오류가 발생했습니다.';

  return (
    <div className="flex items-center justify-center min-h-[calc(100vh-65px)] bg-black/10 p-6 font-sans">
      <div className="w-full max-w-sm bg-white rounded-2xl shadow-xl overflow-hidden animate-in fade-in zoom-in duration-300">
        <div className="p-8 flex flex-col items-center">
          
          {/* 1. 실패 아이콘 (빨간색 ✕) */}
          <div className="w-16 h-16 bg-red-50 rounded-full flex items-center justify-center mb-6">
            <div className="w-10 h-10 bg-[#f44336] rounded-full flex items-center justify-center text-white text-xl">
              ✕
            </div>
          </div>

          {/* 2. 타이틀 & 실패 사유 */}
          <h2 className="text-xl font-bold text-gray-900 mb-2">주문에 실패하였습니다</h2>
          <p className="text-sm text-gray-600 mb-8 text-center leading-relaxed">
            {errorMessage} <br /> 
            내용을 확인하고 다시 시도해 주세요.
          </p>

          <hr className="w-full border-gray-100 mb-8" />

          {/* 3. 복구 버튼 (장바구니가 있는 메인으로 이동) */}
          <Link
            href="/"
            className="w-full py-3 bg-[#ba9470] text-white font-bold rounded-xl hover:bg-[#a67c52] transition-all text-center shadow-lg shadow-[#ba9470]/20"
          >
            장바구니로 돌아가기
          </Link>
        </div>
      </div>
    </div>
  );
}