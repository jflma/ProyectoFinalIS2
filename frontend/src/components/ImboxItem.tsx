import React from 'react'
import { Avatar,AvatarFallback} from './ui/avatar'
import { EnvelopeClosedIcon, EnvelopeOpenIcon} from '@radix-ui/react-icons'

interface Sender {
  username: string;
  avatar: string;
}

interface Props {
  sender: Sender;
  message: string;
  read: boolean
}
export const ImboxItem = ({sender,message,read}:Props) => {
  const visto = false
  return (
    <div className='bg-slate-400 flex gap-2 items-center'>
      <Avatar className='h-12 w-12'>
        <AvatarFallback>Us</AvatarFallback>
      </Avatar>
      <div className='flex flex-col bg-blue-500 w-[clamp(250px,270px,300px)]'>
        <strong>{sender.username}</strong>
        <div className=' flex justify-between gap-1'>
        <span className='text-sm truncate bg-red-400  '>{message}</span>
        <span className='bg-slate-50 text-nowrap text-sm'>16 sem</span>
        </div>
      </div>
      <div className='bg-slate-600 h-5 w-5'>
        {read?<EnvelopeOpenIcon className='w-5 h-5'/>:<EnvelopeClosedIcon className='w-5 h-5'/>}
      </div>


    </div>
  )
}
