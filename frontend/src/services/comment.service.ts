import { axiosApi } from "@/api/axiosApi"
import { useAuthStore } from "@/storages/auth/auth.store"

interface Comment {
  id: number;
  content: string
}

interface CommentResponse {
  id: number;
  autorUsername: string;
  content: string;
}
export class CommentService {
  static sendComment = async (idPost:number,content: string) => {
    try {
      const idUser = useAuthStore.getState().user?.id
      const { data } = await axiosApi.post<Comment>("/comment/post",{idPost,idUser,content})
      console.log("El commentario fue registrado exitosamente"+data.id)
      return data
    } catch (e) {
      throw new Error("No se pudo comentar esta publicacion")
    }
  }

  static getComments = async (idEntry:number):Promise<CommentResponse []> => {
    try {
      const { data } = await axiosApi.get<CommentResponse []>(`http://localhost:8080/comment/getComments/${idEntry}`)
      return data
    }catch (e) {
      throw new Error("NO se pudo traer los commentarios")
    }
  }
}