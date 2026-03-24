import "./globals.css";
import { CartProvider } from "./context/CartContext";
import { Toaster } from 'sonner';

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body className="bg-amber-50/50 min-h-screen">
        {/* 헤더 */}
        {/* 전역 상태관리를 위한 컨텍스트 제공  CartProvider*/}
        <CartProvider> 
        <header className="border-b border-stone-200 px-8 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold text-stone-800">Grids & Circles</h1>
          <button className="text-stone-400 text-2xl">···</button>
        </header>

        {children}
        <Toaster position="top-center" richColors />
        </CartProvider>
      </body>
    </html>
  );
}