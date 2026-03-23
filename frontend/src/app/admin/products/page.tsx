'use client';

export default function AdminProductsPage() {
  // 백엔드 API 연동 전 테스트를 위한 더미 데이터
  const dummyProducts = [
    { id: 1, name: '콜롬비아 수프리모 원두', price: 15000, stock: 50 },
    { id: 2, name: '에티오피아 예가체프 원두', price: 18000, stock: 12 },
    { id: 3, name: '인도네시아 만델링 원두', price: 16500, stock: 0 },
  ];

  return (
    <div className="space-y-8">
      <div className="flex justify-between items-end">
        <div>
          <h2 className="text-3xl font-black text-gray-900">상품 관리</h2>
          <p className="text-gray-500 mt-1">판매 중인 원두 상품을 등록하고 수정할 수 있습니다.</p>
        </div>
        <button className="bg-[#ba9470] text-white px-6 py-3 rounded-lg hover:bg-[#a67c52] shadow-md transition-all font-bold">
          + 새 상품 등록
        </button>
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              {['ID', '상품명', '가격', '재고 상태', '관리'].map((header) => (
                <th key={header} className="px-8 py-5 text-left text-xs font-bold text-gray-400 uppercase tracking-widest">
                  {header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-100">
            {dummyProducts.map((product) => (
              <tr key={product.id} className="hover:bg-indigo-50/30 transition-colors">
                <td className="px-8 py-5 whitespace-nowrap text-sm text-gray-500 font-medium">#{product.id}</td>
                <td className="px-8 py-5 whitespace-nowrap text-sm font-bold text-gray-900">{product.name}</td>
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
                <td className="px-8 py-5 whitespace-nowrap text-sm font-bold space-x-4 text-center">
                  <button className="text-[#ba9470] hover:text-[#a67c52] transition-colors">수정</button>
                  <button className="text-gray-400 hover:text-red-500 transition-colors">삭제</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}