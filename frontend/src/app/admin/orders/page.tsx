'use client';

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

export default function AdminOrdersPage() {
  const dummyOrders: Order[] = [
    {
      orderId: 101,
      email: 'alice@example.com',
      batchDate: '2026-03-25',
      address: '서울시 강남구 테헤란로 123',
      zipCode: '06134',
      totalPrice: 18500,
      totalQuantity: 3,
      orderedAt: '2026-03-24T15:12:30',
      orderItems: [
        { productId: 1, productName: '콜롬비아 수프리모 원두', quantity: 2, unitPrice: 4500 },
        { productId: 3, productName: '에티오피아 예가체프 원두', quantity: 1, unitPrice: 9500 },
      ],
    },
    {
      orderId: 102,
      email: 'bob@example.com',
      batchDate: '2026-03-25',
      address: '서울시 송파구 올림픽로 77',
      zipCode: '05551',
      totalPrice: 12000,
      totalQuantity: 2,
      orderedAt: '2026-03-24T16:05:10',
      orderItems: [{ productId: 2, productName: '에티오피아 예가체프 원두', quantity: 2, unitPrice: 6000 }],
    },
    {
      orderId: 103,
      email: 'charlie@example.com',
      batchDate: '2026-03-26',
      address: '서울시 마포구 월드컵북로 21',
      zipCode: '03991',
      totalPrice: 22500,
      totalQuantity: 4,
      orderedAt: '2026-03-24T17:20:00',
      orderItems: [
        { productId: 4, productName: '콜롬비아 수프리모 원두', quantity: 2, unitPrice: 6000 },
        { productId: 5, productName: '에티오피아 예가체프 원두', quantity: 1, unitPrice: 4500 },
        { productId: 6, productName: '인도네시아 만델링 원두', quantity: 1, unitPrice: 6000 },
      ],
    },
    {
      orderId: 104,
      email: 'david@example.com',
      batchDate: '2026-03-26',
      address: '서울시 성동구 왕십리로 83',
      zipCode: '04769',
      totalPrice: 9000,
      totalQuantity: 2,
      orderedAt: '2026-03-24T18:00:40',
      orderItems: [{ productId: 7, productName: '콜롬비아 수프리모 원두', quantity: 2, unitPrice: 4500 }],
    },
  ];

  return (
    <div className="w-[calc(110%+32px)] -mx-4 px-2 md:px-3 lg:px-10 space-y-6">
      <div className="flex justify-between items-end">
        <div>
          <h2 className="text-3xl font-black text-gray-900">주문 관리</h2>
          <p className="text-gray-500 mt-1">접수된 주문 내역을 확인하고 관리할 수 있습니다.</p>
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
              {dummyOrders.map((order) => {
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
              })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}