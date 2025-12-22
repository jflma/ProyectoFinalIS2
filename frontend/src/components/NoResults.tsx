import React from 'react'
import { Cross1Icon } from '@radix-ui/react-icons'

interface Props {
  query: string;
}
export const NoResults = ({query}:Props) => {
  return (
    <div className='text-xl flex flex-col items-center p-14'>
      <Cross1Icon className='w-10 h-10'/>
      <span>Lo sentimos 😞.</span>
      <span>No pudimos encontrar nada para {`"${query}"`}.</span>
      <span>Prueva con otra pregunta 😎</span>
    </div>
  )
}
