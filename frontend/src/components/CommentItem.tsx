import React from 'react'

interface Comment {
  id: number;
  autorUsername: string;
  content: string;
}
interface Props {
  comment:Comment
}
export const CommentItem = ({comment}:Props) => {
  return (
    <div className='flex gap-2 w-[clamp(500px,100%,700px)] items-center'>
      <span className='text-orange-500 font-semibold p-1'>{comment.id}</span>
      <p className='text-sm'>{comment.content} - <span className='text-blue-500'>{comment.autorUsername}</span> </p>
    </div>
  )
}
