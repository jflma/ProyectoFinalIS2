'use client'
import React, { useEffect, useState } from 'react'
import { CommentItem } from './CommentItem'
import { AddComment } from './AddComment'
import { Separator } from './ui/separator'
import { CommentService } from '@/services/comment.service'
interface CommentResponse {
  id: number;
  autorUsername: string;
  content: string;
}

interface Props {
  idEntry: number;
  postId:number;
}

export const Comments = ({idEntry, postId}: Props) => {
  const [comments, setComments] = useState<CommentResponse[]>([])
  useEffect(() => {
    const getComments = async () => {
      const res = await CommentService.getComments(idEntry)
      return res
    }

    const result = getComments()
    result.then(comments =>setComments(comments))
  },[idEntry])

  return (
    <div className='flex flex-col w-[clamp(500px,100%,700px)] gap-1'>
      {comments.map(comment => <div key={comment.id}><CommentItem key={comment.id} comment={comment}/><Separator className='m-2'/></div>)}
      <AddComment postId={postId}/>
    </div>
  )
}
