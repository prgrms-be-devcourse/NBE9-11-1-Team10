const BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export async function customFetch(endpoint: string, options: RequestInit = {}) {
  const headers = new Headers(options.headers);
  headers.set('Content-Type', 'application/json');

  if (typeof window !== 'undefined') {
    const token = localStorage.getItem('accessToken');
    if (token) {
      headers.set('Authorization', `Bearer ${token}`);
    }
  }

  const response = await fetch(`${BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (response.status === 401 && !endpoint.includes('/login')) {
    if (typeof window !== 'undefined') {
      alert('인증이 만료되었습니다. 다시 로그인해주세요.');
      localStorage.removeItem('accessToken');
      window.location.href = '/login';
    }
  }

  return response;
}