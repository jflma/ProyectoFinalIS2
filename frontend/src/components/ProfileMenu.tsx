import React from 'react'
import { Button } from './ui/button'
import { HamburgerMenuIcon } from '@radix-ui/react-icons'
import { DropdownMenuContent, DropdownMenuTrigger, DropdownMenu, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuGroup, DropdownMenuItem } from './ui/dropdown-menu'
import { useAuthStore } from '@/storages/auth/auth.store'

export const ProfileMenu = () => {
  const logOut = useAuthStore(state => state.logoutUser)
  const handleLogOut = () => {
    logOut()
  }
  return (
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant='outline'><HamburgerMenuIcon className='h-4 w-4'/></Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent className="w-56">
        <DropdownMenuLabel>My Account</DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuGroup>
          <DropdownMenuItem>Perfil</DropdownMenuItem>

        </DropdownMenuGroup>
        <DropdownMenuSeparator />
        <DropdownMenuGroup>
          <DropdownMenuItem onClick={handleLogOut}>Cerrar Sesion</DropdownMenuItem>

        </DropdownMenuGroup>
        </DropdownMenuContent>
      </DropdownMenu>
  )
}
