import { Comments } from '@/components/Comments';
import { MarkdownPreview } from '@/components/MarkdownPreview';
import React from 'react'

interface PostId {
  postId: string;
}
interface Props {
  params: PostId
}

interface Response {
  id:number;
  idEntry: number;
  title: string;
  content: string;
  createdAt:string;
  username:string;
}
const getPost = async (id: string) => {
  const data = await fetch(`http://localhost:8080/post/details/${id}`)
  const res= await data.json()
  return res
}

const PostDetails = async ({params}: Props) => {
  const res:Response = await getPost(params.postId)

  return (
    <div className='flex justify-center'>
      <section className='p-5 flex flex-col gap-2'>
        <div className='flex flex-col w-[clamp(500px,100%,800px)] bg-slate-100 p-3 rounded-md'>
          <h1 className='text-2xl'>{res.title}</h1>
          <div className='flex justify-between'>
            <div className='flex gap-2'><strong>Pregunto: </strong><span>{res.createdAt}</span></div>
            <div className='flex gap-2'><strong>Vistas : </strong><span>{0}</span></div>
          </div>
        </div>
        <MarkdownPreview content={res.content}/>
        <div>
          <Comments idEntry={res.idEntry} postId={res.id}/>
        </div>
      </section>
    </div>
  )
}

export default PostDetails