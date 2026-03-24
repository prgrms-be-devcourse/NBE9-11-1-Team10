'use client';

import { useEffect, useState } from 'react';
import { Product, ApiResponse } from '@/app/type/product';
import { customFetch } from '@/app/api/customFetch';

export default function AdminProductsPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // 모달 및 새 상품 입력 상태
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [newProduct, setNewProduct] = useState({ name: '', price: '', stock: '' });

  // 상품 목록 불러오기 
  const fetchProducts = async () => {
    try {
      setIsLoading(true);
      // size=100으로 백엔드의 4개 제한을 우회하고, t파라미터로 캐시 방지
      const response = await customFetch(`/api/v1/products?size=100`, {
        cache: 'no-store',
      });

      if (!response.ok) throw new Error('서버 응답에 문제가 발생했습니다.');

      const result = await response.json();

      let productList = [];
      if (Array.isArray(result)) {
        productList = result;
      } else if (result.data && Array.isArray(result.data)) {
        productList = result.data;
      } else if (result.data && Array.isArray(result.data.content)) {
        productList = result.data.content;
      }
      setProducts(productList);

    } catch (err) {
      console.error('API 연동 에러:', err);
      setError('서버 연결을 확인해주세요.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  // 모달 닫기 및 폼 초기화
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingId(null); // 수정 모드 해제
    setNewProduct({ name: '', price: '', stock: '' });
  };

  const handleCreateModal = () => {
    setEditingId(null);
    setNewProduct({ name: '', price: '', stock: '' });
    setIsModalOpen(true);
  }

  const handleEditModal = (product: Product) => {
    const targetId = (product as any).productId || product.id;
    setEditingId(targetId);
    setNewProduct({
      name: product.name,
      price: String(product.price),
      stock: String(product.stock)
    });
    setIsModalOpen(true);
  }

  // 상품 등록/수정 제출
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const trimmedName = newProduct.name.trim();

    // 유효성 검사: 숫자로만 된 이름 방지
    if (/^\d+$/.test(trimmedName)) {
      alert('상품명은 숫자로만 입력할 수 없습니다. 정확한 이름을 입력해주세요.');
      return;
    }
    const url = editingId
      ? `/api/v1/admin/products/${editingId}`
      : `/api/v1/admin/products`;
    const method = editingId ? 'PUT' : 'POST';

    try {
      const response = await customFetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: trimmedName,
          price: Number(newProduct.price),
          stock: Number(newProduct.stock)
        }),
      });

      if (response.ok) {
        alert(editingId ? '상품이 성공적으로 수정되었습니다!' : '상품이 성공적으로 등록되었습니다!');
        handleCloseModal(); // 닫으면서 초기화
        fetchProducts();    // 목록 최신화
      } else {
        alert(editingId ? '상품 수정에 실패했습니다.' : '상품 등록에 실패했습니다.');
      }
    } catch (err) {
      console.error('통신 에러:', err);
      alert('서버와 통신 중 에러가 발생했습니다.');
    }
  };

  // 상품 삭제
  const handleDelete = async (id: number) => {
    if (!window.confirm('정말 상품을 삭제하시겠습니까?')) {
      return; // 취소 가능
    }

    try {
      const response = await customFetch(`/api/v1/admin/products/${id}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        alert('상품이 성공적으로 삭제되었습니다!');
        fetchProducts();    // 목록 최신화
      } else {
        alert('상품 삭제에 실패했습니다.');
      }
    } catch (err) {
      console.error('삭제 에러:', err);
      alert('서버와 통신 중 에러가 발생했습니다.');
    }
  };

  if (isLoading && products.length === 0) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-[#ba9470] font-bold animate-pulse">원두 정보를 가져오는 중...</div>
      </div>
    );
  }

  if (error) return <div className="bg-red-50 p-6 rounded-xl border border-red-100 text-red-600 font-medium">⚠️ {error}</div>;

  return (
    <div className="space-y-8 relative">
      <div className="flex justify-between items-end">
        <div>
          <h2 className="text-3xl font-black text-[#222222]">상품 관리</h2>
          <p className="text-gray-500 mt-1 font-medium">판매 중인 원두 상품을 실시간으로 관리합니다.</p>
        </div>
        <button
          onClick={handleCreateModal}
          className="bg-[#ba9470] text-white px-6 py-3 rounded-lg hover:bg-[#a67c52] shadow-md transition-all font-bold"
        >
          + 새 상품 등록
        </button>
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              {['ID', '상품명', '가격', '재고 상태', '관리'].map((header) => (
                <th key={header} className={`px-8 py-5 text-xs font-bold text-gray-400 uppercase tracking-widest ${header === '관리' ? 'text-center' : 'text-left'}`}>
                  {header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-100">
            {products.length === 0 ? (
              <tr><td colSpan={5} className="px-8 py-10 text-center text-gray-400 font-medium">등록된 상품이 없습니다.</td></tr>
            ) : (
              products.map((product) => {
                const pId = product.id || (product as any).id;
                return (
                  <tr key={pId} className="hover:bg-[#ba9470]/5 transition-colors">
                    <td className="px-8 py-5 whitespace-nowrap text-sm text-gray-500 font-medium">#{pId}</td>
                    <td className="px-8 py-5 whitespace-nowrap text-sm font-bold text-[#222222]">{product.name}</td>
                    <td className="px-8 py-5 whitespace-nowrap text-sm text-gray-600">{product.price.toLocaleString()}원</td>
                    <td className="px-8 py-5 whitespace-nowrap">
                      {product.stock > 0 ? (
                        <span className="px-3 py-1 text-xs font-bold bg-[#ba9470]/10 text-[#ba9470] rounded-md">재고 있음 ({product.stock})</span>
                      ) : (
                        <span className="px-3 py-1 text-xs font-bold bg-gray-100 text-gray-400 rounded-md">품절</span>
                      )}
                    </td>
                    <td className="px-8 py-5 whitespace-nowrap text-sm font-bold space-x-6 text-center">
                      <button
                        onClick={() => handleEditModal(product)}
                        className="text-[#ba9470] hover:text-[#a67c52] transition-colors"
                      >
                        수정
                      </button>                      <button
                        onClick={() => handleDelete(pId as number)}
                        className="text-gray-400 hover:text-red-500 transition-colors"
                      >
                        삭제
                      </button>
                    </td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 animate-fadeIn">
          <div className="bg-white p-8 rounded-2xl w-[400px] shadow-2xl">
            <h3 className="text-2xl font-bold text-[#222222] mb-6">
              {editingId ? '상품 수정' : '새 원두 등록'}
            </h3>
            <form onSubmit={handleSubmit} className="space-y-5">
              <div>
                <label className="block text-sm font-bold text-gray-700 mb-2">상품명</label>
                <input
                  type="text" required placeholder="예: 콜롬비아 수프리모"
                  className="w-full border border-gray-200 rounded-lg p-3 bg-gray-50 focus:bg-white focus:ring-2 focus:ring-[#ba9470] outline-none transition-all"
                  value={newProduct.name}
                  onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
                />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-bold text-gray-700 mb-2">가격 (원)</label>
                  <input
                    type="number" required placeholder="0" min="0"
                    className="w-full border border-gray-200 rounded-lg p-3 bg-gray-50 focus:bg-white focus:ring-2 focus:ring-[#ba9470] outline-none transition-all"
                    value={newProduct.price}
                    onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })}
                  />
                </div>
                <div>
                  <label className="block text-sm font-bold text-gray-700 mb-2">재고 (개)</label>
                  <input
                    type="number" required placeholder="0" min="0"
                    className="w-full border border-gray-200 rounded-lg p-3 bg-gray-50 focus:bg-white focus:ring-2 focus:ring-[#ba9470] outline-none transition-all"
                    value={newProduct.stock}
                    onChange={(e) => setNewProduct({ ...newProduct, stock: e.target.value })}
                  />
                </div>
              </div>
              <div className="flex space-x-3 pt-4">
                <button
                  type="button"
                  onClick={handleCloseModal}
                  className="flex-1 py-3 bg-gray-100 text-gray-600 rounded-lg font-bold hover:bg-gray-200 transition-colors"
                >
                  취소
                </button>
                <button
                  type="submit"
                  className="flex-1 py-3 bg-[#ba9470] text-white rounded-lg font-bold hover:bg-[#a67c52] transition-colors shadow-lg shadow-[#ba9470]/20"
                >
                  {editingId ? '수정하기' : '등록하기'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}