import { axiosApi } from "@/api/axiosApi"
import { useAuthStore } from "@/storages/auth/auth.store";
// import { useAuthStore } from "@/storages/auth/auth.store";




interface PostResponse {
  id: number;
  title: string;
}


export class QuestionService {


  static sendQuestion = async (title: string, content: string): Promise<PostResponse> => {
    try {
      const idUser = useAuthStore.getState().user?.id
      const { data } = await axiosApi.post<PostResponse>('/post/create', {idUser,title,content})
      console.log(data)
      return data;

    } catch (e) {
      console.log(e)
      throw new Error("Error al enviar pregunta :c")
      
    }
  }
}