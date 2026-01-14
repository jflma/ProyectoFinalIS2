'use client'
import React, { ChangeEvent, useState, KeyboardEvent } from 'react'
import { Input } from './ui/input'
import { CommentService } from '@/services/comment.service';

interface Props {
  postId: number;
  onCommentAdded?: (comment: any) => void;
}
export const AddComment = ({ postId, onCommentAdded }: Props) => {
  const [comment, setComment] = useState<string>("")
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value)
  }

  const handleEnter = async (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') {
      return
    }
    const response = await CommentService.sendComment(postId, comment)
    setComment("")
    if (onCommentAdded) {
      onCommentAdded(response)
    }
    console.log({ response })
  }

  return (
    <div className='w-[clamp(500px,100%,700px)]'>
      <Input placeholder='Agregar un commentario' value={comment} onChange={handleChange} className='border-0' onKeyDown={handleEnter} />
    </div>
  )
}
