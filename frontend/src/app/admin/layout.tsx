import Link from 'next/link';
import React from 'react';

export default function AdminLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="flex h-screen bg-[#f8f9fa] font-sans">
            {/* 좌측 사이드바 */}
            <aside className="w-64 bg-[#222222] text-white flex flex-col shadow-2xl">
                <div className="p-8 border-b border-white/10">
                    <h1 className="text-2xl font-black tracking-tight text-[#ba9470]">GC Admin</h1>
                    <p className="text-[10px] text-gray-500 mt-1 uppercase tracking-[0.2em]">Coffee Management</p>
                </div>

                <nav className="flex-1 px-4 py-8 space-y-1">
                    <Link href="/admin/products" className="flex items-center px-4 py-3 rounded-lg hover:bg-white/5 transition-all text-gray-400 hover:text-[#ba9470] group">
                        <span className="mr-3 text-lg opacity-70 group-hover:opacity-100">📦</span>
                        <span className="font-medium">상품 관리</span>
                    </Link>
                    <Link href="/admin/orders" className="flex items-center px-4 py-3 rounded-lg hover:bg-white/5 transition-all text-gray-400 hover:text-[#ba9470] group">
                        <span className="mr-3 text-lg opacity-70 group-hover:opacity-100">🛒</span>
                        <span className="font-medium">주문 관리</span>
                    </Link>
                </nav>
            </aside>

            {/* 우측 영역 */}
            <main className="flex-1 flex flex-col overflow-hidden">
                <header className="bg-white h-20 flex items-center justify-between px-10 border-b border-gray-200">
                    <h2 className="text-xl font-bold text-[#222222]">관리자 대시보드</h2>
                    <button className="text-sm font-bold text-[#ba9470] hover:underline">로그아웃</button>
                </header>
                <div className="flex-1 overflow-y-auto p-10 bg-white">
                    <div className="max-w-6xl mx-auto">{children}</div>
                </div>
            </main>
        </div>
    );
}