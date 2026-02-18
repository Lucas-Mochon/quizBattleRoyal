'use client';

import { LanguageContext } from '@/contexts/LanguageContext';
import { Language } from '@/types/Language';
import { useEffect, useState } from 'react';
import i18n from '@/lib/i18n';

export function LanguageProvider({ children }: { children: React.ReactNode }) {
  const [language, setLanguage] = useState<Language>(() => {
    if (typeof window !== 'undefined') {
      const savedLang = localStorage.getItem('language') as Language;
      if (savedLang === 'fr' || savedLang === 'en') {
        return savedLang;
      }
    }
    return 'fr';
  });

  useEffect(() => {
    if (i18n.changeLanguage) {
      i18n.changeLanguage(language);
    }
  }, [language]);

  const changeLanguage = (lang: Language) => {
    setLanguage(lang);
    if (i18n.changeLanguage) {
      i18n.changeLanguage(lang);
    }
    localStorage.setItem('language', lang);
  };

  return (
    <LanguageContext.Provider value={{ language, changeLanguage }}>
      {children}
    </LanguageContext.Provider>
  );
}
