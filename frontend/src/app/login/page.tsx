'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { customFetch } from '../api/customFetch';

export default function LoginPage() {
    const router = useRouter();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMsg, setErrorMsg] = useState('');

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setErrorMsg('');

        try {
            const response = await customFetch('/api/members/login', {
                method: 'POST',
                body: JSON.stringify({ email, password }),
            });

            if (!response.ok) {
                throw new Error('로그인 실패');
            }

            const data = await response.json();
            const { accessToken } = data;

            localStorage.setItem('accessToken', accessToken);
            alert('로그인에 성공했습니다!');
            router.push('/admin');

        } catch (error: any) {
            console.error('Login failed:', error);
            setErrorMsg('아이디 또는 비밀번호가 올바르지 않습니다.');
        }
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <form onSubmit={handleLogin} className="bg-white p-8 rounded shadow-md w-96">
                <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">로그인</h2>

                {errorMsg && (
                    <div className="mb-4 text-red-500 text-sm text-center">{errorMsg}</div>
                )}

                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2">이메일</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="w-full px-3 py-2 border rounded text-gray-700 focus:outline-none focus:border-blue-500"
                        required
                    />
                </div>

                <div className="mb-6">
                    <label className="block text-gray-700 text-sm font-bold mb-2">비밀번호</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="w-full px-3 py-2 border rounded text-gray-700 focus:outline-none focus:border-blue-500"
                        required
                    />
                </div>

                <button
                    type="submit"
                    className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
                >
                    로그인
                </button>
            </form>
        </div>
    );
}