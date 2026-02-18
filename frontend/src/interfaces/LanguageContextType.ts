import { Language } from '@/types/Language';

export interface LanguageContextType {
  language: Language;
  changeLanguage: (lang: Language) => void;
}
