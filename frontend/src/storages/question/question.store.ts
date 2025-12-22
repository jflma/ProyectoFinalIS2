import { QuestionService } from "@/services/question.service";
import { StateCreator, create } from "zustand";
import { devtools, persist } from 'zustand/middleware';

type SendStatus = "nothing"|"pending"|"successfully"|"failed"

interface QuestionState {
  title: string;
  content: string;
  sendStatus:SendStatus;
  
  setTitle: (newTitle: string) => void;
  setContent: (value: string | undefined) => void;

  sendQuestion: () => Promise<void>;
}

const storeApi: StateCreator<QuestionState> = (set,get) => ({
  title:"",
  content:"",
  sendStatus:"nothing",

  setTitle: (newTitle) => {
    set({title:newTitle})
  },
  setContent: ( value ) => {
    set({content:value,sendStatus:"pending"})
  },
  sendQuestion: async () => {
      try {
        const title = get().title
        const content = get().content
        const data = await QuestionService.sendQuestion(title,content)
        if (data.id) {
          set({sendStatus:"successfully",title:"",content:""})
        } 
        else {
          set({sendStatus:"failed"})
        }
      } catch (e) {
        throw new Error("Hubo un problema con el envio de la pregunta ")
      }
  },
})


export const useQuestion = create<QuestionState>()(
  devtools(
    persist(
      storeApi,
      {name: 'question-storage'}
    )
  )
)
