import { axiosApi } from '@/api/axiosApi';
import { User, UserLogin } from '@/interfaces/index'
import { AxiosError } from 'axios';



interface LoginResponse {
  token: string;
}

interface ForoUser {
  id:number;
}

export class AuthService {
  static login = async (fieldsLogin: UserLogin): Promise<LoginResponse> => {
    try {
      const { data } = await axiosApi.post<LoginResponse>('/auth/signin', {username:fieldsLogin.username,password:fieldsLogin.password})
      console.log( data )
      return data
    } catch (error) {
      if (error instanceof AxiosError){
        throw new Error(error.response?.data)
      }
      throw new Error('Unable to login')
    }

  }

  static checkStatus = async (): Promise<User> => {
    try {
      const {data} = await axiosApi.get<User>('/user/check-status')
      console.log(data)
      return data;

    } catch (e) {
      throw new Error('Error authorization ')
    }
  }

  static RegisterUser = async (firstName:string, lastName:string,email:string,birthDay:string,username:string,password:string):Promise<ForoUser> => {
    try {
      const { data } = await axiosApi.post<ForoUser>("/auth/signup",{firstName,lastName,email,birthDay,username,password})
      return data
    } catch (e) {
      throw new Error ("No se pudo registar el ususario")
    }
  }
}