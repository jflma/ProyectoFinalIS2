'use client'
import React from 'react'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { Person, UserLogin} from '@/interfaces/index'
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { useForm } from 'react-hook-form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { AuthService } from '@/services/auth.service'
import Link from 'next/link'

const formSchema = z.object({
  firstName:  z.string().min(2).max(20),
  lastName:  z.string().min(2).max(20),
  email: z.string().email(),
  birthDay: z.string().min(10).max(10),
  username: z.string().min(2).max(20),
  password: z.string().min(5).max(50),
});

function SignInPage() {

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      birthDay: "",
      username:"",
      password: "",
    },
  })

  const onSubmit = async (values: z.infer<typeof formSchema> ) => {
    try {
      const userRegister = await AuthService.RegisterUser(values.firstName,values.lastName,values.email,values.birthDay,values.username,values.password)
      console.log("Registro exitoso")
    }catch (e) {
      throw new Error("No se pudo registar al usuario")
    }
  }

  return (
    <section className='flex flex-row'>
      <div className='h-screen  md:w-1/2 lg:w-2/3 w-0'></div>
      <div className=' h-screen md:w-1/2 lg:w-1/3 bg-slate-100 flex flex-col items-center w-full '>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className='p-10 w-full flex flex-col gap-4 justify-center'>
          <h2 className='text-3xl font-medium mx-auto'>Registrarse</h2>
          <FormField
            control={form.control}
            name='firstName'
            render={({field}) => (
              <FormItem>
                <FormLabel>Primer nombre</FormLabel>
                <FormControl>
                  <Input placeholder='firstname' {...field}/>
                </FormControl>
                <FormDescription>
                  Ingrese su primer nombre.
                </FormDescription>
                <FormMessage/>
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name='lastName'
            render={({field}) => (
              <FormItem>
                <FormLabel>Apellidos</FormLabel>
                <FormControl>
                  <Input placeholder='lastname' {...field}/>
                </FormControl>
                <FormDescription>
                  Ingrese sus apellidos.
                </FormDescription>
                <FormMessage/>
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name='email'
            render={({field}) => (
              <FormItem>
                <FormLabel>Email</FormLabel>
                <FormControl>
                  <Input placeholder='email' {...field}/>
                </FormControl>
                <FormDescription>
                  Ingrese su correo.
                </FormDescription>
                <FormMessage/>
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name='birthDay'
            render={({field}) => (
              <FormItem>
                <FormLabel>Fecha de nacimineto</FormLabel>
                <FormControl>
                  <Input placeholder='birthDay' type='date'{...field}/>
                </FormControl>
                <FormDescription>
                  Ingrese su correo.
                </FormDescription>
                <FormMessage/>
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name='username'
            render={({field}) => (
              <FormItem>
                <FormLabel>Nombre de Usuario</FormLabel>
                <FormControl>
                  <Input placeholder='username' {...field}/>
                </FormControl>
                <FormDescription>
                  Cree un nombre de usuario.
                </FormDescription>
                <FormMessage/>
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name='password'
            render={({field}) => (
              <FormItem>
                <FormLabel>Contraseña</FormLabel>
                <FormControl>
                  <Input placeholder='password' {...field}/>
                </FormControl>
                <FormDescription>
                  Cree una contraseña.
                </FormDescription>
                <FormMessage/>
              </FormItem>
            )}
          />

          <Button type='submit'>Registrarse</Button>
        </form>
        <span>¿Ya tienes cuenta?<Button variant="link"><Link href="/auth/login">Iniciar session</Link></Button></span>
      </Form>
      </div>
    </section>
  )
}

export default SignInPage