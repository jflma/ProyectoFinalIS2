import { Avatar, AvatarFallback,AvatarImage } from '@radix-ui/react-avatar'
import React from 'react'

interface Actor {
  username: string;
  avatar: string;
}
interface Post {
  id: number;
  titlle: string;
}

interface Notification {
  actor: Actor;
  type: string;
  to: string;
  post: Post;
  date: string
}

export const NotificationItem = (notification: Notification) => {
  const {actor, type, to, post, date} = notification
  return (
    <div className='bg-slate-700 flex gap-2 items-center'>
      <Avatar className='h-12 w-12 bg-red-100'>
        <AvatarImage src='https://github.com/shadcn.png' alt="@shadcn"/>

        <AvatarFallback>CN</AvatarFallback>
      </Avatar>
      <div className='bg-blue-500 w-[clamp(250px,270px,300px)] h-15 flex flex-col'>
      <strong>{actor.username}</strong>
        <span className='text-sm'>{type} tu {to==='post'?'post :':'respuesta en :'}</span>
        <span className='text-sm truncate'>{post.titlle}</span>
        
        
      </div>
      <div className='bg-slate-600 text-sm '>
        {date}
      </div>


    </div>
  )
}
