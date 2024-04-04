import { makeExecutableSchema } from '@graphql-tools/schema'

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

const usersList = [
  { id: 1, name: "Jan Konieczny", email: "jan.konieczny@wonet.pl", login: "jkonieczny" },
  { id: 2, name: "Anna Wesołowska", email: "anna.w@sad.gov.pl", login: "anna.wesolowska" },
  { id: 3, name: "Piotr Waleczny", email: "piotr.waleczny@gp.pl", login: "p.waleczny" }
];

const todosList = [
  { id: 1, title: "Naprawić samochód", completed: false, user_id: 3 },
  { id: 2, title: "Posprzątać garaż", completed: true, user_id: 3 },
  { id: 3, title: "Napisać e-mail", completed: false, user_id: 3 },
  { id: 4, title: "Odebrać buty", completed: false, user_id: 2 },
  { id: 5, title: "Wysłać paczkę", completed: true, user_id: 2 },
  { id: 6, title: "Zamówic kuriera", completed: false, user_id: 3 },
];

function findUserById(id: number) {
  return usersList.find(user => user.id === id);
}

function findTodoById(id: number) {
  return todosList.find(todo => todo.id === id);
}

const resolvers = {
  Query: {
    users: () => usersList,
    todos: () => todosList,
    user: (parent: any, args: { id: string; }) => findUserById(Number(args.id)),
    todo: (parent: any, args: { id: string; }) => findTodoById(Number(args.id)),
  },
  User: {
    todos: (parent: { id: number; }) => todosList.filter(todo => todo.user_id === parent.id)
  },
  ToDoItem: {
    user: (parent: { user_id: number; }) => usersList.find(user => user.id === parent.user_id)
  }
}

export const schema = makeExecutableSchema({
  resolvers: [resolvers],
  typeDefs: [typeDefs]
})
