'use client'
import { useSearchStore } from "@/storages/search/search.store";
import { ViewPost } from "./ViewPost";
import { useEffect, useState } from "react";
import { SearchService } from "@/services/search.service";
import { NoResults } from "./NoResults";
import { Separator } from "./ui/separator";

interface PostResultQuery {
  id: number;
  title: string;
  content: string;
  votes: number;
  answers: number;
  views:number
  tags: string[];
}

interface Props {
  posts: PostResultQuery[];
}
export const ResultTable = () => {
  const query = useSearchStore(state => state.query)
  const [posts,setPosts] = useState<PostResultQuery []>([])


  useEffect(() => {
    console.log("esta es la query actual :"+query)
    const functionGetPost = async() => {
      const data = await SearchService.getPostbyQuery()
      return data
    }
    const result = functionGetPost()
    result.then(posts => setPosts(posts)).catch(e => console.log("hubo un error al setiar los post"+e))
    

  },[query])
  return (
    <section className="m-2">
      {posts.length===0?<NoResults query={query}/>:posts.map(post => <div key={post.id}><ViewPost  post={post}/><Separator/></div>)}
      {/* {posts.map(post => <ViewPost key={post.id} post={post}/>)} */}
    </section>
  )
}
