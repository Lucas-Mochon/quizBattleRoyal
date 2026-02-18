'use client';

import Link from 'next/link';
import { useTranslation } from 'react-i18next';
import { navbarContent, authLinks } from '@/utils/navbarContent';
import { LanguageSwitcher } from './LanguageSwitcher';

export default function Navbar() {
  const { t } = useTranslation();

  return (
    <nav
      className="w-full backdrop-blur-sm border-b border-white/10"
      style={{ backgroundColor: '#1E293B' }}
    >
      <div className="max-w-7xl mx-auto px-6 p-4">
        <div className="flex items-center justify-between gap-8">
          <div className="flex gap-4">
            {navbarContent.map((item) => (
              <Link
                key={item.label}
                href={item.href}
                className="px-8 py-3 rounded-full text-white font-medium transition-all hover:scale-105"
                style={{ backgroundColor: '#7C3AED' }}
              >
                {t(`navigation.${item.label}`)}
              </Link>
            ))}
          </div>

          <div className="flex items-center gap-14">
            <div className="flex gap-4">
              {authLinks.map((item) => (
                <Link
                  key={item.label}
                  href={item.href}
                  className="px-8 py-3 rounded-full text-white font-medium transition-all hover:scale-105"
                  style={{ backgroundColor: '#7C3AED' }}
                >
                  {t(`navigation.${item.label}`)}
                </Link>
              ))}
            </div>

            <LanguageSwitcher />
          </div>
        </div>
      </div>
    </nav>
  );
}
