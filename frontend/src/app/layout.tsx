import "./globals.css";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body className="bg-stone-50 min-h-screen">
        {/* 헤더 */}
        <header className="bg-white border-b px-6 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold">Grids & Circles</h1>
          <button className="text-gray-500">···</button>
        </header>

        {children}
      </body>
    </html>
  );
}