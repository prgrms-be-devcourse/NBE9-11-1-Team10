'use client';

import { useEffect, useState } from 'react';
// 커스텀 fetch 사용
import { customFetch } from '@/app/api/customFetch';

type ApiResponse<T> = {
  success: boolean;
  data: T;
  error?: {
    code: string;
    message: string;
  } | null;
};

type OrderItem = {
  productId: number;
  productName: string;
  quantity: number;
  unitPrice: number;
};

type Order = {
  orderId: number;
  email: string;
  batchDate: string;
  address: string;
  zipCode: string;
  totalPrice: number;
  totalQuantity: number;
  orderedAt: string;
  orderItems: OrderItem[];
};

function formatOrderDateParts(orderedAt: string) {
  const d = new Date(orderedAt);
  const datePart = d.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
  });
  const timePart = d.toLocaleTimeString('ko-KR', {
    hour: 'numeric',
    minute: '2-digit',
    second: '2-digit',
    hour12: true,
  });
  return { datePart, timePart };
}

function normalizeOrder(raw: any): Order {
  return {
    orderId: raw.orderId ?? raw.id ?? 0,
    email: raw.email ?? raw.member?.email ?? '',
    batchDate: raw.batchDate ?? raw.orderBatch?.batchDate ?? '',
    address: raw.address ?? '',
    zipCode: raw.zipCode ?? '',
    totalPrice: raw.totalPrice ?? 0,
    totalQuantity: raw.totalQuantity ?? 0,
    orderedAt: raw.orderedAt ?? new Date().toISOString(),
    orderItems: (raw.orderItems ?? []).map((item: any) => ({
      productId: item.productId ?? item.product?.id ?? 0,
      productName: item.productName ?? item.product?.name ?? '',
      quantity: item.quantity ?? 0,
      unitPrice: item.unitPrice ?? item.product?.price ?? 0,
    })),
  };
}

export default function AdminOrdersPage() {
  const [orders, setOrders] = useState<Order[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        setIsLoading(true);
        setError(null);

        // 커스텀 fetch 사용
        const response = await customFetch('/api/v1/admin/orders', {
          method: 'GET',
          cache: 'no-store',
        });

        if (!response.ok) {
          throw new Error('네트워크 응답에 문제가 발생했습니다.');
        }

        const result = await response.json();

        if (Array.isArray(result)) {
          const normalized = result.map(normalizeOrder);
          setOrders(normalized);
        } else if (result.success !== undefined) {
          const apiResult = result as ApiResponse<any>;

          if (apiResult.success) {
            const rawList = Array.isArray(apiResult.data)
              ? apiResult.data
              : Array.isArray(apiResult.data?.content)
                ? apiResult.data.content
                : [];

            setOrders(rawList.map(normalizeOrder));
          } else {
            setError(apiResult.error?.message || '데이터를 불러오는데 실패했습니다.');
            setOrders([]);
          }
        } else {
          setOrders([]);
        }
      } catch (err) {
        console.error('API 연동 에러:', err);
        setError('백엔드 서버 연결을 확인해주세요.');
        setOrders([]);
      } finally {
        setIsLoading(false);
      }
    };

    fetchOrders();
  }, []);

  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-[#ba9470] font-bold animate-pulse">주문 정보를 가져오는 중...</div>
      </div>
    );
  }

  return (
    <div className="w-[calc(110%+32px)] -mx-4 px-2 md:px-3 lg:px-10 space-y-6">
      <div className="flex justify-between items-end">
        <div>
          <h2 className="text-3xl font-black text-gray-900">주문 관리</h2>
          <p className="text-gray-500 mt-1">접수된 주문 내역을 확인하고 관리할 수 있습니다.</p>
          {error && <p className="text-xs mt-1 text-red-500">API 오류: {error}</p>}
        </div>
      </div>

      <div className="w-full bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <div className="w-full overflow-x-auto">
          <table className="w-full table-fixed divide-y divide-gray-200">
            <colgroup>
              <col className="w-[8%]" />
              <col className="w-[16%]" />
              <col className="w-[10%]" />
              <col className="w-[14%]" />
              <col className="w-[18%]" />
              <col className="w-[8%]" />
              <col className="w-[8%]" />
              <col className="w-[18%]" />
            </colgroup>

            <thead className="bg-gray-50">
              <tr>
                {['주문번호', '주문자', '배치일', '주문일시', '배송지', '총수량', '총금액', '상품'].map((header) => (
                  <th
                    key={header}
                    className="px-5 py-4 text-left text-xs font-bold text-gray-400 uppercase tracking-widest"
                  >
                    {header}
                  </th>
                ))}
              </tr>
            </thead>

            <tbody className="bg-white divide-y divide-gray-100">
              {orders.length === 0 ? (
                <tr>
                  <td colSpan={8} className="px-5 py-8 text-center text-sm text-gray-500">
                    주문 내역이 없습니다.
                  </td>
                </tr>
              ) : (
                orders.map((order) => {
                  const { datePart, timePart } = formatOrderDateParts(order.orderedAt);

                  return (
                    <tr key={order.orderId} className="hover:bg-indigo-50/30 transition-colors">
                      <td className="px-5 py-4 text-sm text-gray-500 font-medium align-top break-words">#{order.orderId}</td>
                      <td className="px-5 py-4 text-sm font-bold text-gray-900 align-top break-words">{order.email}</td>
                      <td className="px-5 py-4 text-sm text-gray-600 align-top break-words">{order.batchDate}</td>
                      <td className="px-5 py-4 text-sm text-gray-600 align-top">
                        <div>{datePart}</div>
                        <div>{timePart}</div>
                      </td>
                      <td className="px-5 py-4 text-sm text-gray-600 align-top break-words">
                        {order.address} ({order.zipCode})
                      </td>
                      <td className="px-5 py-4 text-sm text-gray-600 align-top break-words">{order.totalQuantity}개</td>
                      <td className="px-5 py-4 text-sm font-semibold text-gray-800 align-top break-words">
                        {order.totalPrice.toLocaleString()}원
                      </td>
                      <td className="px-5 py-4 text-sm text-gray-600 align-top break-words">
                        <ul className="list-disc pl-5 space-y-1">
                          {order.orderItems.map((item) => (
                            <li key={`${order.orderId}-${item.productId}`}>
                              {item.productName} <span className="font-semibold">x{item.quantity}</span>
                            </li>
                          ))}
                        </ul>
                      </td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}