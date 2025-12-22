import { ResultTable } from '@/components/ResultTable'
import { SideBar } from '@/components/SideBar'
import { UtilsBar } from '@/components/UtilsBar'
import PreguntasPage from '@/app/preguntas/page'
import React from 'react'

const HomePage = () => {
  return (
    <section className='flex lg:px-[50px] justify-center'>
      <SideBar/>
      <div className='flex  flex-col w-[clamp(700px,100%,900px)]'>
        <UtilsBar/>
        {/* <ResultTable/> */}
        <PreguntasPage/>
      </div>

    </section>
  )
}

export default HomePage