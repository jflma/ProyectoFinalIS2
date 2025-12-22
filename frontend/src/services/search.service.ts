import { axiosApi } from "@/api/axiosApi"
import { useSearchStore } from "@/storages/search/search.store"

interface PostResultQuery {
  id: number;
  idEntry:number;
  title: string;
  content: string;
  votes: number;
  answers: number;
  views:number
  tags: string[];
}

export class SearchService {
  static getPostbyQuery = async (): Promise<PostResultQuery []> => {
    try {
      const query = useSearchStore.getState().query
      if (query === "") {
        const { data }= await axiosApi.get<PostResultQuery []>("/post/ultimatePost")
        return data
      }
      else {
        const { data } = await axiosApi.get<PostResultQuery []>(`/search/posts?keyword=${query}`)
        return data
      }
    } catch (e) {
      throw new Error("no se pudo obtener respuesat dela consulta")
    }
  }
}