import { User, UserLogin } from '@/interfaces';
import { AuthService } from '@/services/auth.service';
import { StateCreator, create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';
// import { create } from 'zustand'

type StatusAuth = 'authorized' | 'unauthorized' | 'pending'

//Creamos la interfaz de nuestro
interface AuthState {
  status: StatusAuth;
  token?: string;
  user?: User;

  loginUser: (fieldsLogin: UserLogin) => Promise<void>;
  checkStatus: () => Promise<void>;
  logoutUser: () => void;
}

const storeApi: StateCreator<AuthState> = (set) => ({
  status: 'pending',
  token: undefined,
  user: undefined,

  loginUser: async (fieldsLogin: UserLogin) => {
    try {
      const {token} = await AuthService.login(fieldsLogin)
      set({status: 'pending',token:token})
    } catch (e) {
      set({status:'unauthorized',token: undefined, user: undefined})
      throw 'Unauthorized2'
    }
  },

  checkStatus: async () => {
    try {
      const user = await AuthService.checkStatus();
      set({ status: 'authorized', user:user })

    } catch (e) {
      set({status: 'unauthorized', user: undefined, token:undefined})
      throw 'Unautorized3'
    }
  },
  logoutUser: () => {
    set({status:'unauthorized', token:undefined, user: undefined})
  },
})

export const useAuthStore = create<AuthState>()(
  devtools(
    persist(
      storeApi,
      {name: 'auth-storage'}
    )
  )
)
