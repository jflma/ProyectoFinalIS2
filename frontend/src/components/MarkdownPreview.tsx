'use client'
import MDPreview from '@uiw/react-markdown-preview'
interface Props {
  content: string;
}

export const MarkdownPreview = ({content}:Props) => {
  return (
    <MDPreview source={content}  style={{position:'relative',width: '800px',padding:'20px'}}/>
  )
}
