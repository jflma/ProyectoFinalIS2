import React from 'react'
import { Button } from './ui/button'
import { ChatBubbleIcon } from '@radix-ui/react-icons'
import { Popover, PopoverContent, PopoverTrigger} from './ui/popover'

export const Imbox = () => {
  return (
    <Popover>
    <PopoverTrigger asChild>
      <Button variant="outline" size='icon'>
        <ChatBubbleIcon className='h-4 w-4'/>
      </Button>
    </PopoverTrigger>
    <PopoverContent className='bg-slate-700'>
      <div>Hola</div>
    </PopoverContent>
  </Popover>
  )
}
