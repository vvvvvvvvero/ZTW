import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  const all = await prisma.user.findMany()
  console.log(all)
}
 
main()
  .finally(async () => {
    await prisma.$disconnect()
  })