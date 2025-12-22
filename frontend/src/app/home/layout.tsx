'use client'

import { TopBar } from "@/components/TopBar";
import { useAuthStore } from "@/storages/auth/auth.store";
import { useRouter,   } from "next/navigation";

export default function HomeLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const router = useRouter()
  const authStatus = useAuthStore(state => state.status)
  const checkStatus = useAuthStore(state => state.checkStatus)
  console.log({authStatus})
  if (authStatus === 'pending') {
    checkStatus()
    return <div>Cargando...</div>
  }
  if (authStatus === 'unauthorized'){
    return router.push('/auth/login')
  }
  return (
    <div>
      <TopBar/>
      {children}
      
    </div>
  );
}
