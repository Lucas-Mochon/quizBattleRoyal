'use client';

import { useLanguage } from '@/hooks/useLanguage';

export function LanguageSwitcher() {
  const { language, changeLanguage } = useLanguage();

  return (
    <div
      className="flex gap-1 p-1 rounded-full backdrop-blur-sm border border-white/10"
      style={{
        background: 'linear-gradient(135deg, #1E1B4B 0%, #4C1D95 100%)',
      }}
    >
      <button
        onClick={() => changeLanguage('fr')}
        className={`px-4 py-2 rounded-full text-sm font-medium transition-all ${
          language === 'fr'
            ? 'bg-white/20 text-white shadow-lg scale-105'
            : 'text-white/70 hover:text-white hover:bg-white/10'
        }`}
      >
        🇫🇷 FR
      </button>
      <button
        onClick={() => changeLanguage('en')}
        className={`px-4 py-2 rounded-full text-sm font-medium transition-all ${
          language === 'en'
            ? 'bg-white/20 text-white shadow-lg scale-105'
            : 'text-white/70 hover:text-white hover:bg-white/10'
        }`}
      >
        🇬🇧 EN
      </button>
    </div>
  );
}
