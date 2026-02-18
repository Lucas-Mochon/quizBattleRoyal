'use client';

import logo from '@/assets/pictures/logo.png';
import Image from 'next/image';

export default function Loading() {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-linear-to-br from-[#0B0F1A] via-[#1E1B4B] to-[#4C1D95]">
      <div className="relative flex flex-col items-center justify-center">
        <div className="absolute">
          <div className="w-75 h-75 rounded-full border-4 border-purple-500/30 animate-ping" />
        </div>
        <div className="absolute">
          <div
            className="w-62.5 h-62.5 rounded-full border-4 border-purple-400/40"
            style={{
              animation: 'spin 2s linear infinite',
            }}
          />
        </div>

        <div className="relative z-10 animate-pulse">
          <Image
            src={logo}
            alt="Logo"
            width={200}
            height={200}
            className="object-contain drop-shadow-2xl"
            priority
          />
        </div>

        <div className="mt-8 flex gap-2">
          <span
            className="w-2 h-2 bg-purple-400 rounded-full animate-bounce"
            style={{ animationDelay: '0ms' }}
          />
          <span
            className="w-2 h-2 bg-purple-400 rounded-full animate-bounce"
            style={{ animationDelay: '150ms' }}
          />
          <span
            className="w-2 h-2 bg-purple-400 rounded-full animate-bounce"
            style={{ animationDelay: '300ms' }}
          />
        </div>
      </div>

      <style jsx>{`
        @keyframes spin {
          from {
            transform: rotate(0deg);
          }
          to {
            transform: rotate(360deg);
          }
        }
      `}</style>
    </div>
  );
}
