import Link from 'next/link';
import React from 'react';

export default function AdminLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="flex h-screen bg-gray-100 font-sans">
            {/* ⬅️ 좌측 사이드바 */}
            <aside className="w-64 bg-gray-900 text-white flex flex-col shadow-xl">
                <div className="p-6 border-b border-gray-800">
                    <h1 className="text-2xl font-bold tracking-wider">GC Admin</h1>
                    <p className="text-sm text-gray-400 mt-1">Grids & Circles</p>
                </div>

                <nav className="flex-1 px-4 py-6 space-y-2">
                    <Link href="/admin/products" className="block px-4 py-3 rounded-lg hover:bg-gray-800 transition-colors text-gray-300 hover:text-white font-medium">
                        📦 상품 관리
                    </Link>
                    <Link href="/admin/orders" className="block px-4 py-3 rounded-lg hover:bg-gray-800 transition-colors text-gray-300 hover:text-white font-medium">
                        🛒 주문 관리
                    </Link>
                </nav>
            </aside>

            {/* ➡️ 우측 메인 콘텐츠 영역 */}
            <main className="flex-1 flex flex-col overflow-hidden">
                {/* 상단 헤더바 */}
                <header className="bg-white shadow-sm h-16 flex items-center justify-between px-8 border-b border-gray-200">
                    <h2 className="text-xl font-semibold text-gray-800">관리자 대시보드</h2>
                    <button className="text-sm px-4 py-2 bg-gray-100 text-gray-700 rounded-md hover:bg-gray-200 transition font-medium">
                        로그아웃
                    </button>
                </header>

                {/* 실제 내용이 바뀌는 알맹이 영역 */}
                <div className="flex-1 overflow-y-auto p-8">
                    {children}
                </div>
            </main>
        </div>
    );
}