'use client'
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form'
import React, { use } from 'react'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { useAuthStore } from '@/storages/auth/auth.store'
import { useRouter } from 'next/navigation'
import Link from 'next/link'

const formSchema = z.object({
  username: z.string().min(2).max(20),
  password: z.string().min(4),
});

const LoginPage = () => {
  const signInUser =useAuthStore(state => state.loginUser)
  const router = useRouter()

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: '',
      password: ''
    }
  });

  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    console.log({values})
    try {
      await signInUser(values)
      router.push('/home')
    } catch (e) {
      console.log('No se pudo authenticar')
    }
  }
  return (
  <section className='flex  flex-row-reverse'>
    <div className='h-screen w-0 md:w-1/2 lg:w-2/3'>
    </div>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className='p-14 h-screen w-full flex flex-col gap-4 bg-slate-100 md:w-1/2 lg:w-1/3 justify-center'>
        <h2 className='text-3xl font-medium '>Iniciar session</h2>
          <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Username</FormLabel>
                  <FormControl>
                    <Input placeholder="username" {...field} />
                  </FormControl>
                  <FormDescription>
                    Ingresa su email.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />


            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input placeholder="password" type='password' {...field} />
                  </FormControl>
                  <FormDescription>
                    Ingrese su contraseña
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type='submit'>Acceder</Button>
            <span className='mx-auto'>¿No tienes Cuenta?<Button variant="link"><Link href="/auth/signin">Registrate</Link></Button></span>
        </form>
      </Form>
  </section>
  )
}

export default LoginPage 