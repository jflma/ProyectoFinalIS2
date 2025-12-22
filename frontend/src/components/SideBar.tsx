'use client'
import React from 'react'
import { HomeIcon , BookmarkFilledIcon}from '@radix-ui/react-icons'
import { Separator } from './ui/separator'
import { ToggleGroup, ToggleGroupItem} from './ui/toggle-group'

export const SideBar = () => {
  return (
    <section className='w-48 flex gap-2 items-start rounded-sm h-[600px] my-10'>
      <ToggleGroup type='single' className='flex flex-col w-full ' defaultValue='home' onValueChange={(e)=> console.log(e)}>
        <ToggleGroupItem value='home'>
          <div className='w-[167px] p-2 flex justify-start gap-2 items-center'><HomeIcon className='w-4 h-4'/>Home</div>
        </ToggleGroupItem>
        <ToggleGroupItem value='guardados'>
          <div className='w-[167px] p-2 flex justify-start gap-2 items-center'><BookmarkFilledIcon className='w-4 h-4'/>Guardados</div>
        </ToggleGroupItem>

      </ToggleGroup>
      <Separator orientation='vertical'/>
    
    </section>
  )
}
