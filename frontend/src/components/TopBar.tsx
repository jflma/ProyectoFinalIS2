'use client'
import React from 'react'
import { Imbox } from './Imbox'
import { NotificationMailbox } from './NotificationMailbox'
import { Button } from './ui/button'
import { PlusCircledIcon} from '@radix-ui/react-icons'
import { ProfileMenu } from './ProfileMenu'
import { SearchBar } from './SearchBar'
import Link from 'next/link'


export const TopBar = () => {
  return (
    <section className='bg-slate-400 flex gap-5 justify-between p-3 px-20'>
      <div className='flex gap-5 w-[clamp(700px,100%,1000px)]'>
        <h1 className='text-2xl font-extrabold'><Link href="/home">ForoEPCC</Link></h1>
        <SearchBar/>
      </div>
      <div>
        {/* <Imbox/> */}
        {/* <NotificationMailbox/> */}
        <ProfileMenu/>
      </div>
      
    </section>
  )
}
