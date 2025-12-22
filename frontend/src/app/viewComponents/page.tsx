import { TopBar } from "@/components/TopBar"
import { ImboxItem } from '@/components/ImboxItem'
import { NotificationItem } from "@/components/NotificationItem"
import { MarkdownEditor } from "@/components/MarkdownEditor"
import { ViewPost } from "@/components/ViewPost"
import { CommentItem } from "@/components/CommentItem"
import { Comments } from "@/components/Comments"

const ViewComponentsPage = () => {
  const post1 = {id:1,title:"Titulo inventado xd",
    contentPrev:"nose que clase de contenido sera  un texto lago",
    votes:12,answers:33,views:133,tags:['c++','javascript','spring boot']}
  return (
    <div>
      <TopBar/>
      <ImboxItem sender={{username:"Juan Pable",avatar:"xd"}} message="nose que mensaje va aqui xd" read={false}/>
      <NotificationItem actor={{username:"Juan Pable",avatar:"xd"}} date="12 sem" post={{id:1,titlle:"Como se borra una base de datos nose estoy desde ahce dias"}} to="Post" type="Comento"/>
      <CommentItem comment={{autorUsername:"MAUPZ",content:"contenido del comentario que puede ser largo en determianda ocasioncontenido del comentario que puede ser largo en determianda ocasion",id:1}}/>
    </div>
  )
}

export default ViewComponentsPage