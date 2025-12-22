import React from 'react'
import { Badge } from './ui/badge'
import Link from 'next/link';



interface PostPreview {
  id: number;
  title: string;
  content: string;
  votes: number;
  answers: number;
  views:number
  tags: string[];
}
interface Props{
  post:PostPreview;
}

export const ViewPost = ({post}:Props) => {
  return (
    <article className=' flex p-3 gap-2 items-start w-[clamp(700px,100%,900px)] justify-start '>
      <div className='md:flex flex-col gap-1 w-[120px] min-w-[120px] items-end hidden '>
          <Badge variant='outline' className='border-0 text-nowrap text-slate-500'> {post.votes} Votos</Badge>
          <Badge variant='outline' className='border-0 text-nowrap text-slate-500'>{post.answers} Respuestas</Badge>
          <Badge variant='outline' className='border-0 text-nowrap text-slate-500'>{post.views} vistas</Badge>
      </div>
      <div className='flex flex-col gap-2 '>
        <div>
          <h5 className='font-semibold text-blue-400'><Link href={`/preguntas/${post.id}`}>{post.title}</Link></h5>
          <p className='line-clamp-2 text-sm'>{post.content}</p>
        </div>
        {/* <div className='flex gap-2'>
          {post.tags.map(tag => <Badge variant='secondary' key={tag}>{tag}</Badge> )}
        </div> */}
      </div>
    </article>
  )
}
