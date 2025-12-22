import React from 'react'
import { Button } from './ui/button'
import { PlusCircledIcon } from '@radix-ui/react-icons'
import Link from 'next/link'
import { Separator } from './ui/separator'

export const UtilsBar = () => {
  
  return (
    <div className=' p-6 h-20 m-1 flex flex-col gap-2 justify-center'>
      <div className='w-[clamp(800px,100%,1024px)] flex flex-row-reverse'>
        <Button variant="outline" className='bg-blue-500 text-slate-50'>
          <Link href="/home/newpost" className='flex gap-1'><PlusCircledIcon className='w-5 h-5 mr-2'/>Preguntar</Link>
        </Button>
      </div>
      <div>
        
      </div>
      <Separator/>
    </div>
  )
}
