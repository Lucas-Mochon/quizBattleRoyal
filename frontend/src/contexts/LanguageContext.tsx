'use client';

import { LanguageContextType } from '@/interfaces/LanguageContextType';
import { createContext } from 'react';

export const LanguageContext = createContext<LanguageContextType | undefined>(undefined);
