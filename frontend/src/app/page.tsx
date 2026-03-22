import Cart from "./components/Cart";
import OrderForm from "./components/OrderForm";
import ProductList from "./components/ProductList";

export default function Home() {
  return (
    <main className="max-w-7xl mx-auto">
      <div className="flex min-h-[calc(100vh-65px)]">
        {/* 좌측: 상품 목록 */}
        <section className="w-2/3 p-8 border-r border-stone-200">
          <h2 className="text-xl font-bold text-stone-800 mb-6">커피 원두 패키지</h2>
          <ProductList />
        </section>

        {/* 우측: 장바구니 + 주문 */}
        <aside className="w-1/3 p-8">
          <Cart />
          <div className="border-t border-stone-200 mt-8 pt-8">
            <OrderForm />
          </div>
        </aside>
      </div>
    </main>
  );
}