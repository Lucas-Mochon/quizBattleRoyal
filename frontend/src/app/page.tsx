'use client';

import { useTranslation } from 'react-i18next';
import { LanguageSwitcher } from '@/components/LanguageSwitcher';

export default function Home() {
  const { t } = useTranslation();

  return (
    <div className="flex min-h-screen flex-col items-center justify-center p-8">
      <div className="w-full max-w-2xl space-y-8">
        <div className="flex justify-end">
          <LanguageSwitcher />
        </div>
        
        <div className="space-y-4 text-center">
          <h1 className="text-4xl font-bold">
            {t('common.welcome')}
          </h1>
          
          <div className="space-y-2">
            <p className="text-xl text-muted-foreground">
              {t('common.loading')}
            </p>
            <p className="text-xl text-muted-foreground">
              {t('common.error')}
            </p>
          </div>

          <nav className="flex gap-4 justify-center pt-4">
            <a href="#" className="text-blue-500 hover:underline">
              {t('navigation.home')}
            </a>
            <a href="#" className="text-blue-500 hover:underline">
              {t('navigation.about')}
            </a>
            <a href="#" className="text-blue-500 hover:underline">
              {t('navigation.contact')}
            </a>
          </nav>
        </div>
      </div>
    </div>
  );
}