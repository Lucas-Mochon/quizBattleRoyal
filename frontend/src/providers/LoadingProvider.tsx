'use client';

import { LoadingContext } from '@/contexts/LoadingContext';
import { useState } from 'react';
import Loading from '@/components/Loading';

export function LoadingProvider({ children }: { children: React.ReactNode }) {
  const [isLoading, setIsLoading] = useState(false);

  return (
    <LoadingContext.Provider value={{ isLoading, setIsLoading }}>
      {isLoading && <Loading />}
      {children}
    </LoadingContext.Provider>
  );
}
