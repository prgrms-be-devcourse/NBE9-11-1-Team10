'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
// 1. 경로 수정: components 폴더에서 context 폴더로 접근
import { useCart } from '../context/CartContext';
// 커스텀 fetch 사용
import { customFetch } from "../api/customFetch";

export default function OrderForm() {
  const router = useRouter();
  // 2. CartContext에서 필요한 데이터와 함수를 가져옵니다.
  const { cart, products, totalAmount, resetCart } = useCart();

  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [zipCode, setZipCode] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // 프론트엔드 검증
    if (!email.trim()) return alert("이메일을 입력해주세요.");
    if (!address.trim()) return alert("주소를 입력해주세요.");
    if (!zipCode.trim()) return alert("우편번호를 입력해주세요.");

    // 장바구니가 비어있는지 확인
    if (Object.keys(cart).length === 0) return alert("장바구니에 상품을 담아주세요.");

    try {
      // 3. 백엔드 DTO 규격에 맞춰 데이터 가공
      const orderData = {
        email,
        address,
        zipCode,
        // cart 객체 { id: quantity }를 [{ productId, quantity }] 배열로 변환
        orderItems: Object.entries(cart).map(([productId, quantity]) => ({
          productId: Number(productId),
          quantity: quantity
        }))
      };

      const response = await customFetch(`/api/v1/orders`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(orderData),
      });

      const result = await response.json();

      if (result.success) {
        // 4. 성공 시 장바구니 리셋 후 이동
        resetCart();

        // 실제 상품 정보를 성공 페이지에 전달하기 위해 데이터 구성
        const itemsSummary = products
          .filter(p => cart[p.id] > 0)
          .map(p => `${p.name} x${cart[p.id]}:${(p.price * cart[p.id]).toLocaleString()}원`)
          .join('|');

        router.push(`/orders/success?totalPrice=${totalAmount}&items=${itemsSummary}`);
      } else {
        router.push(`/orders/fail?message=${result.error.message}`);
      }
    } catch (error) {
      router.push(`/orders/fail?message=서버와 통신 중 오류가 발생했습니다.`);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <h3 className="text-lg font-bold mb-4">주문 정보</h3>

      {/* 이메일 입력 */}
      <div className="space-y-2">
        <label className="text-sm font-medium text-gray-700">이메일</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full p-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#ba9470]/20 focus:border-[#ba9470]"
          placeholder="coffee@example.com"
        />
      </div>

      {/* 주소 입력 */}
      <div className="space-y-2">
        <label className="text-sm font-medium text-gray-700">주소</label>
        <input
          type="text"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          className="w-full p-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#ba9470]/20 focus:border-[#ba9470]"
          placeholder="상세 주소를 입력하세요"
        />
      </div>

      {/* 우편번호 입력 */}
      <div className="space-y-2">
        <label className="text-sm font-medium text-gray-700">우편번호</label>
        <input
          type="text"
          value={zipCode}
          onChange={(e) => setZipCode(e.target.value)}
          className="w-full p-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#ba9470]/20 focus:border-[#ba9470]"
          placeholder="12345"
        />
      </div>

      <button
        type="submit"
        className="w-full py-4 bg-[#ba9470] text-white font-bold rounded-xl hover:bg-[#a67c52] transition-all shadow-lg shadow-[#ba9470]/10"
      >
        주문 확정
      </button>

      <p className="text-center text-xs text-gray-400 mt-4">
        매일 14:00 기준으로 주문을 모아 배송합니다 [cite: 2026-03-24]
      </p>
    </form>
  );
}