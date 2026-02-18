'use client';

import { createContext } from 'react';
import { LoadingContextType } from '@/interfaces/LoadingContextType';

export const LoadingContext = createContext<LoadingContextType | undefined>(undefined);
