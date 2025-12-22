'use client'
import { useQuestion } from '@/storages/question/question.store';
import MDEditor from '@uiw/react-md-editor'
import { useState } from 'react'
import rehypeSanitize from 'rehype-sanitize'

export const MarkdownEditor = () => {
  // const [value, setValue] =useState<string | undefined>()
  const value = useQuestion(state => state.content)
  const setValue = useQuestion(state => state.setContent)
  return (
    <MDEditor
      value={value}
      // onChange={setValue}
      onChange={(e) => {setValue(e)}}

      style={{ whiteSpace: 'pre-wrap',height: '500px',padding: '5px',borderRadius:'5px' }}
      height={500}
      
      previewOptions={{
        rehypePlugins:[[rehypeSanitize]]
      }}
      />
  )
}
