export interface Person {
  firstName: string;
  lastName: string;
  email: string;
  birthDay: string;
}

export interface User {
  id:number;
  person: Person;
  roles: Role[];
  username: string;
  password: string;
}

export interface Role {
  id: number;
  name: string;
}

export type UserLogin = Pick<User, "username"| "password">