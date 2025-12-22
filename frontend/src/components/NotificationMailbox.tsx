
import { Button } from './ui/button'
import { BellIcon } from '@radix-ui/react-icons'
import { Popover, PopoverContent, PopoverTrigger} from './ui/popover'
export const NotificationMailbox = () => {
  return (
    <Popover>
      <PopoverTrigger asChild>
        <Button variant="outline" size='icon'>
          <BellIcon className='h-4 w-4'/>
        </Button>
      </PopoverTrigger>
      <PopoverContent className='bg-slate-700'>
        <div>Hola</div>
      </PopoverContent>
    </Popover>
  )
}
