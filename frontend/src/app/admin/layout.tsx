'use client';

import Link from 'next/link';
import { usePathname, useRouter } from 'next/navigation';
import React, { useState, useEffect } from 'react';

export default function AdminLayout({ children }: { children: React.ReactNode }) {
    const pathname = usePathname();
    const router = useRouter();
    const [isAuthorized, setIsAuthorized] = useState(false);
    const isActive = (href: string) => pathname.startsWith(href);

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (!token) {
            alert('관리자 권한이 필요합니다. 먼저 로그인해주세요.');
            router.replace('/login');
        } else {
            setIsAuthorized(true);
        }
    }, [router]);

    const handleLogout = () => {
        localStorage.removeItem('accessToken');
        alert('로그아웃 되었습니다.');
        router.push('/login');
    };

    if (!isAuthorized) {
        return null;
    }

    return (
        <div className="flex h-screen bg-[#f8f9fa] font-sans">
            <aside className="w-64 bg-[#222222] text-white flex flex-col shadow-2xl">
                <div className="p-8 border-b border-white/10">
                    <h1 className="text-2xl font-black tracking-tight text-[#ba9470]">GC Admin</h1>
                    <p className="text-[10px] text-gray-500 mt-1 uppercase tracking-[0.2em]">Management System</p>
                </div>

                <nav className="flex-1 px-4 py-8 space-y-2">
                    {/* 상품 관리 메뉴 */}
                    <Link
                        href="/admin/products"
                        className={`flex items-center px-4 py-3 rounded-lg transition-all group relative ${isActive('/admin/products')
                            ? 'bg-white/10 text-[#ba9470]' // 활성화 시 배경/글자색
                            : 'text-gray-400 hover:text-white hover:bg-white/5' // 비활성화 시
                            }`}
                    >
                        {/* 활성화 시 왼쪽에 나타나는 세로 바 */}
                        {isActive('/admin/products') && (
                            <div className="absolute left-0 w-1 h-6 bg-[#ba9470] rounded-r-full" />
                        )}
                        <span className={`mr-3 text-lg ${isActive('/admin/products') ? 'opacity-100' : 'opacity-70'}`}>📦</span>
                        <span className="font-medium">상품 관리</span>
                    </Link>

                    {/* 주문 관리 메뉴 */}
                    <Link
                        href="/admin/orders"
                        className={`flex items-center px-4 py-3 rounded-lg transition-all group relative ${isActive('/admin/orders')
                            ? 'bg-white/10 text-[#ba9470]'
                            : 'text-gray-400 hover:text-white hover:bg-white/5'
                            }`}
                    >
                        {isActive('/admin/orders') && (
                            <div className="absolute left-0 w-1 h-6 bg-[#ba9470] rounded-r-full" />
                        )}
                        <span className={`mr-3 text-lg ${isActive('/admin/orders') ? 'opacity-100' : 'opacity-70'}`}>🛒</span>
                        <span className="font-medium">주문 관리</span>
                    </Link>
                </nav>
            </aside>

            {/* 우측 영역 */}
            <main className="flex-1 flex flex-col overflow-hidden">
                <header className="bg-white h-20 flex items-center justify-between px-10 border-b border-gray-200">
                    <h2 className="text-xl font-bold text-[#222222]">
                        {isActive('/admin/products') ? '📦 상품 관리' : '🛒 주문 관리'}
                    </h2>
                    <button
                        onClick={handleLogout}
                        className="text-sm font-bold text-[#ba9470] hover:underline"
                    >
                        로그아웃
                    </button>
                </header>
                <div className="flex-1 overflow-y-auto p-10 bg-white">
                    <div className="max-w-6xl mx-auto">{children}</div>
                </div>
            </main>
        </div>
    );
}