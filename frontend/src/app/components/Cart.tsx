"use client"
import {useCart} from "@/app/context/CartContext"

export default function Cart() {

    const {cart, products, updateQuantity, totalAmount} = useCart();

    const EmptyCart = () => (
      <div className="py-12 text-center">
        <p className="text-sm text-stone-400">장바구니가 비어 있습니다.</p>
      </div>
    );

    return (
      <div>
        <h3 className="text-lg font-bold mb-4">장바구니</h3>
        <p className="text-gray-400">장바구니 컴포넌트</p>
        {/* 1. 장바구니 리스트 영역 */}
        <div className="bg-white rounded-lg border border-stone-200 divide-y divide-stone-100">
          {totalAmount === 0 ? (
            <EmptyCart />
          ) : (
            <div className="p-4 space-y-4">
              {products.map((p) => {
                const quantity = cart[p.id] || 0;
                if (quantity <= 0) return null;

                return (
                  <div key={p.id} className="flex flex-col gap-2 pb-4 last:pb-0">
                    <div className="flex justify-between items-start">
                      <div className="flex flex-col">
                        <span className="text-sm font-medium text-stone-700">{p.name}</span>
                        <span className="text-xs text-stone-400">{p.price.toLocaleString()}원</span>
                      </div>
                      <span className="text-sm font-bold text-stone-900">
                        {(p.price * quantity).toLocaleString()}원
                      </span>
                    </div>

                    {/* 수량 조절 컨트롤러 */}
                    <div className="flex justify-end">
                      <div className="flex items-center bg-stone-50 rounded-full border border-stone-200 p-1">
                        <button 
                          onClick={() => updateQuantity(p.id, -1)}
                          className="w-7 h-7 flex items-center justify-center rounded-full bg-white border border-stone-200 text-stone-600 hover:bg-stone-100 transition-colors"
                        >
                          －
                        </button>
                        <span className="text-sm font-bold text-stone-700 w-8 text-center">
                          {quantity}
                        </span>
                        <button 
                          onClick={() => updateQuantity(p.id, 1)}
                          className="w-7 h-7 flex items-center justify-center rounded-full bg-white border border-stone-200 text-stone-600 hover:bg-stone-100 transition-colors"
                        >
                          ＋
                        </button>
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
          )}
        </div>

        {/* 합계 요약  */}
        {totalAmount > 0 && (
          <div className="mt-4 px-2 flex justify-between items-center">
            <span className="text-sm text-stone-500">선택 상품 합계</span>
            <span className="text-lg font-extrabold text-stone-900">
              {totalAmount.toLocaleString()}원
            </span>
          </div>
        )}

      </div>
    );
  }