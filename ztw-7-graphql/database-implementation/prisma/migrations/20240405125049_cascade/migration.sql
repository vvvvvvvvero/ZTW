-- RedefineTables
PRAGMA foreign_keys=OFF;
CREATE TABLE "new_ToDoItem" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "title" TEXT NOT NULL,
    "completed" BOOLEAN NOT NULL DEFAULT false,
    "userId" INTEGER NOT NULL,
    CONSTRAINT "ToDoItem_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO "new_ToDoItem" ("completed", "id", "title", "userId") SELECT "completed", "id", "title", "userId" FROM "ToDoItem";
DROP TABLE "ToDoItem";
ALTER TABLE "new_ToDoItem" RENAME TO "ToDoItem";
PRAGMA foreign_key_check;
PRAGMA foreign_keys=ON;
