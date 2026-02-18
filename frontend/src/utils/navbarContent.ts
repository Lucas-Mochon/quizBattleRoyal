import { NavbarItem } from '@/interfaces/NavbarItem';

export const navbarContent: NavbarItem[] = [
  { label: 'home', href: '/' },
  { label: 'discover', href: '/discover' },
  { label: 'myQuiz', href: '/my-quiz' },
  { label: 'history', href: '/history' },
];

export const authLinks: NavbarItem[] = [
  { label: 'login', href: '/login' },
  { label: 'register', href: '/register' },
];
