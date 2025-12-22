import { useAuthStore } from "@/storages/auth/auth.store";
import axios from "axios";

export const axiosApi = axios.create({
  baseURL: 'http://localhost:8080'
})

// Todo: interceptors
axiosApi.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().token;
    console.log({token})

    if ( token ) {
      config.headers['Authorization'] = `Bearer ${ token }`
    }
    return config
  }
)