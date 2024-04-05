import { makeExecutableSchema } from '@graphql-tools/schema'
import type { User } from '@prisma/client'
import type { ToDoItem } from '@prisma/client'
import type { GraphQLContext } from './context'


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

type Mutation {
  createUser(name: String!, email: String!, login: String!): User!
  createToDoItem(title: String!, userId: ID!): ToDoItem!
  deleteUser(id: ID!): User!
  deleteToDoItem(id: ID!): ToDoItem!
}
`

const resolvers = {
  Query: {
    users: (parent: unknown, args: {}, context: GraphQLContext) => context.prisma.user.findMany(),
    todos: (parent: unknown, args: {}, context: GraphQLContext) => context.prisma.toDoItem.findMany(),
    user: (parent: unknown, args: { id: string }, context: GraphQLContext) => context.prisma.user.findUnique({ where: { id: Number(args.id) } }),
    todo: (parent: unknown, args: { id: string }, context: GraphQLContext) => context.prisma.toDoItem.findUnique({ where: { id: Number(args.id) } }),
  },
  Mutation: {
    async createUser(parent: unknown, args: { name: string, email: string, login: string }, context: GraphQLContext) {
      const newUser = await context.prisma.user.create({
        data: {
          name: args.name,
          email: args.email,
          login: args.login
        }

      })
      return newUser
    },
    async createToDoItem(parent: unknown, args: { title: string, userId: string }, context: GraphQLContext) {
      const newToDoItem = await context.prisma.toDoItem.create({
        data: {
          title: args.title,
          userId: Number(args.userId)
        }
      })
      return newToDoItem
    },
    async deleteUser(parent: unknown, args: { id: string }, context: GraphQLContext) {
      const user = await context.prisma.user.delete({
        where: {
          id: Number(args.id)
        }
      })
      return user
    },
    async deleteToDoItem(parent: unknown, args: { id: string }, context: GraphQLContext) {
      const toDoItem = await context.prisma.toDoItem.delete({
        where: {
          id: Number(args.id)
        }
      })
      return toDoItem
    }
  },
  User: {
    todos: (parent: User, args: {}, context: GraphQLContext) => context.prisma.toDoItem.findMany({ where: { userId: Number(parent.id) } })
  },
  ToDoItem: {
    user: (parent: ToDoItem, args: {}, context: GraphQLContext) => context.prisma.user.findUnique({ where: { id: Number(parent.userId) } })
  }

}

export const schema = makeExecutableSchema({
  resolvers: [resolvers],
  typeDefs: [typeDefs]
})
