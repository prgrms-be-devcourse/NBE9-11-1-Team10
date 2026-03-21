import Cart from "./components/Cart";
import OrderForm from "./components/OrderForm";
import ProductList from "./components/ProductList";

export default function Home() {
  return (
    <main className="max-w-7xl mx-auto p-6">
      <div className="flex gap-6">
        {/* 좌측: 상품 목록 */}
        <section className="w-2/3">
          <h2 className="text-xl font-bold mb-4">커피 원두 패키지</h2>
          <ProductList />
        </section>

        {/* 우측: 장바구니 + 주문 */}
        <aside className="w-1/3 space-y-6">
          <Cart />
          <OrderForm />
        </aside>
      </div>
    </main>
  );
}