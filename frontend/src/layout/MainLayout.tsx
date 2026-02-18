import Background from '@/components/ui/Background';
import Navbar from '@/components/Navbar';
import { LoadingProvider } from '@/providers/LoadingProvider';
import { LanguageProvider } from '@/providers/LanguageProvider';

export default function MainLayout({ children }: { children: React.ReactNode }) {
  return (
    <LanguageProvider>
      <LoadingProvider>
        <Background />
        <Navbar />
        {children}
      </LoadingProvider>
    </LanguageProvider>
  );
}
