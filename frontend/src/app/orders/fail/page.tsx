'use client';

import Link from 'next/link';
import { useSearchParams } from 'next/navigation';

// 1. 에러 메시지 매핑 테이블
const ERROR_MESSAGES: { [key: string]: string } = {
  'OUT_OF_STOCK': '선택하신 상품의 재고가 부족합니다.',
  'INVALID_ORDER': '주문 정보가 올바르지 않습니다.',
  'MEMBER_NOT_FOUND': '회원 정보를 찾을 수 없습니다.',
  'SERVER_ERROR': '서버 통신 중 오류가 발생했습니다.',
  'DEFAULT': '주문 처리 중 알 수 없는 오류가 발생했습니다.'
};

export default function OrderFailPage() {
  const searchParams = useSearchParams();
  const errorCode = searchParams.get('message') || 'DEFAULT';

  // 2. 매핑 테이블에서 한글 메시지 추출 (없으면 기본 메시지)
  const displayMessage = ERROR_MESSAGES[errorCode] || ERROR_MESSAGES['DEFAULT'];

  return (
    <div className="flex items-center justify-center min-h-[calc(100vh-65px)] bg-black/10 p-6 font-sans">
      <div className="w-full max-w-sm bg-white rounded-2xl shadow-xl overflow-hidden animate-in fade-in zoom-in duration-300">
        <div className="p-8 flex flex-col items-center">
          
          <div className="w-16 h-16 bg-red-50 rounded-full flex items-center justify-center mb-6">
            <div className="w-10 h-10 bg-[#f44336] rounded-full flex items-center justify-center text-white text-xl">
              ✕
            </div>
          </div>

          <h2 className="text-xl font-bold text-gray-900 mb-2">주문에 실패하였습니다</h2>
          
          {/* 3. 깔끔한 한글 메시지만 노출 */}
          <p className="text-sm text-gray-600 mb-8 text-center leading-relaxed px-4 font-medium">
            {displayMessage}
          </p>

          <hr className="w-full border-gray-100 mb-8" />

          <Link
            href="/"
            className="w-full py-3 bg-[#ba9470] text-white font-bold rounded-xl hover:bg-[#a67c52] transition-all text-center"
          >
            장바구니로 돌아가기
          </Link>
        </div>
      </div>
    </div>
  );
}