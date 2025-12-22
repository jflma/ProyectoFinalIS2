'use client'
import { MarkdownEditor } from "@/components/MarkdownEditor"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useQuestion } from "@/storages/question/question.store"
import { RocketIcon, CheckCircledIcon } from '@radix-ui/react-icons'
import { ChangeEvent } from "react"


const PagePost = () => {
  const title = useQuestion(state => state.title)
  const setTitle = useQuestion(state => state.setTitle)
  const sendQuestion = useQuestion(state => state.sendQuestion)

  const handleChangeTitle = (e: ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value)
  }

  const successfullySend = () => {
    console.log(111111111)
  }
  const handleSendPost = async () => {
    try {
      await sendQuestion();
      successfullySend()

    }catch (e) {

    }
  }


  return (
    <section className="md:px-[20px] lg:px-[200px] xl:px-[300px] p-[20px]">
      <h3 className="text-2xl font-semibold">Crea tu Pregunta!</h3>
      <div className="py-[30px]">
        <Label htmlFor="titleNewPost" className="text-sm font-semibold">1.Crea un titulo que descriptivo!</Label>
        <Input placeholder="Que puedo preguntar ?" id="titlenewPost" type="text" value={title} onChange={handleChangeTitle}/>
      </div>
      <div>
        <h4 className="pb-[15px] text-sm font-semibold">2.Ahora crea tu Pregunta!</h4>
        <MarkdownEditor/>
      </div>
      <div className="bg-slate-200 flex justify-between p-[20px] items-center rounded-md my-[15px]">
        <h4 className="text-sm font-semibold">3.Listo! Solo queda 1 paso :D</h4>
        <Button variant="outline" onClick={handleSendPost}><span className="px-2">Publicar!</span> <RocketIcon className="w-5 h-5"/></Button>
      </div>
    </section>
  )
}

export default PagePost