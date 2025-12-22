import { MarkdownPreview } from '@/components/MarkdownPreview'
import React from 'react'

const PagePostItem = () => {
  return (
    <div className='p-[20px] flex justify-center'>
      <section>
        <div>
          <h1 className='text-2xl font-semibold py-[20px] bg-red-400'>Titulo de la Pregunta !</h1>
          <div>
            <span>Creado</span>
            <span>Modificado</span>
            <span>Vistas</span>
          </div>
        </div>
         <MarkdownPreview content='# Hola mundoxd  # Nose que sera esto ttttttttttttttttttttttttttttttttttttttt'/>
      </section>
    </div>
  )
}

export default PagePostItem