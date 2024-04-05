import { makeExecutableSchema } from '@graphql-tools/schema'
import axios from 'axios';


const typeDefs = `
type ToDoItem {
  id: ID!
  title: String!
  completed: Boolean!
  user: User!
}

type User {
  id: ID!
  name: String!
  email: String!
  login: String!
  todos: [ToDoItem!]!
}

type Query {
 todos: [ToDoItem!]
 todo(id: ID!): ToDoItem
 users: [User!]
 user(id: ID!): User
}
`
type User = {
  id: number;
  name: string;
  email: string;
  login: string;
};

type ToDoItem = {
  id: number;
  title: string;
  completed: boolean;
  user_id: number;
};

type UserApiResponse = {
  id: number;
  name: string;
  email: string;
  username: string;
}[];

type ToDoApiResponse = {
  id: number;
  title: string;
  completed: boolean;
  userId: number;
}[];

async function fetchUsers(): Promise<User[]> {
  try {
    const response = await axios.get<UserApiResponse>('https://jsonplaceholder.typicode.com/users');
    console.log(response.data);
    return response.data.map(({ id, name, email, username }) => ({
      id,
      name,
      email,
      login: username
    }));
  } catch (error) {
    console.error(error);
    return []; 
  }
}

async function fetchTodos(): Promise<ToDoItem[]> {
  try {
    const response = await axios.get<ToDoApiResponse>('https://jsonplaceholder.typicode.com/todos');
    return response.data.map(({ id, title, completed, userId }) => ({
      id,
      title,
      completed,
      user_id: userId
    }));
  } catch (error) {
    console.error(error);
    return [];
  }
}


const usersList: User[] = [
  { id: 1, name: "Jan Konieczny", email: "jan.konieczny@wonet.pl", login: "jkonieczny" },
  { id: 2, name: "Anna Wesołowska", email: "anna.w@sad.gov.pl", login: "anna.wesolowska" },
  { id: 3, name: "Piotr Waleczny", email: "piotr.waleczny@gp.pl", login: "p.waleczny" }
];

const todosList: ToDoItem[] = [
  { id: 1, title: "Naprawić samochód", completed: false, user_id: 3 },
  { id: 2, title: "Posprzątać garaż", completed: true, user_id: 3 },
  { id: 3, title: "Napisać e-mail", completed: false, user_id: 3 },
  { id: 4, title: "Odebrać buty", completed: false, user_id: 2 },
  { id: 5, title: "Wysłać paczkę", completed: true, user_id: 2 },
  { id: 6, title: "Zamówic kuriera", completed: false, user_id: 3 },
];

async function findUserById(id: number) {
  const users = await fetchUsers();
  return users.find(user => user.id === id);
}

async function findTodoById(id: number) {
  const todos = await fetchTodos();
  return todos.find(todo => todo.id === id);
}

const resolvers = {
  Query: {
    users: async () => fetchUsers(),
    todos: async () => fetchTodos(),
    user: (parent: any, args: { id: string; }) => findUserById(Number(args.id)),
    todo: (parent: any, args: { id: string; }) => findTodoById(Number(args.id)),
  },
  User: {
    todos: async (parent: User) => {
      const todos = await fetchTodos();
      return todos.filter(todo => todo.user_id === parent.id);
    }
  },
  ToDoItem: {
    user: async (parent: ToDoItem) => {
      const users = await fetchUsers();
      return users.find(user => user.id === parent.user_id);
    }
  }
}

export const schema = makeExecutableSchema({
  resolvers: [resolvers],
  typeDefs: [typeDefs]
})
