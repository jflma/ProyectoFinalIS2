'use client'
import {ChangeEvent, useState, KeyboardEvent} from 'react'
import { Input } from './ui/input'
import { useSearchStore } from '@/storages/search/search.store'
export const SearchBar = () => {
  const [queryInput, setQueryInput] = useState<string>("")
  const setQuery = useSearchStore(state => state.setQuery)

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setQueryInput(e.target.value)
  }

  const handlePressKey = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') {
      return
    }
    setQuery(queryInput)
    console.log("presiono enter")
    console.log(queryInput)
  }
  return (
    <Input type='text' placeholder='Busqueda' className='w-[clamp(300px,80%,500px)]' value={queryInput} onChange={handleChange} onKeyDown={handlePressKey}/>
  )
}
