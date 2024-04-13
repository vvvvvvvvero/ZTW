<script>
import DataTable from "@/components/common/DataTable.vue";
import bookService from "@/api/bookService.js";
import authorService from "@/api/authorService.js";

export default {
  name: "Books",
  components: {DataTable},
  mounted() {
    this.loadBooks();
    this.loadAuthors();
  },
  data() {
    return {
      tableHeaders: ['Id', 'Title', 'Author Id', 'Pages', 'Landed By'],
      tableRows: [],
      authors: [], // Store authors here
      newBook: {
        title: '',
        authorId: '',
        pages: ''
      }
    };
  },
  methods: {
    addBook() {
      this.saveBook(this.newBook)
        .then(book => {
          this.tableRows.push(book);
          this.newBook = { title: '', authorId: '', pages: ''};
        });
    },
    onEdit(rowId, editedData) {
      this.updateBook(rowId, editedData)
        .then(updatedBook => {
          const index = this.tableRows.findIndex(book => book.id === rowId);
          if (index !== -1) {
            this.tableRows[index] = updatedBook;
          }
        });
    },
    onDelete(rowId) {
      this.deleteBook(rowId);
    },
    async loadBooks() {
      try {
        const response = await bookService.getBooks();
        this.tableRows = response.data;
      } catch (error) {
        console.error('Error fetching books:', error);
      }
    },
    async deleteBook(bookId) {
      try {
        await bookService.deleteBook(bookId);
        this.tableRows = this.tableRows.filter(book => book.id !== bookId);
      } catch (error) {
        console.error('Error deleting book:', error);
      }
    },
    async saveBook(book) {
      try {
        const response = await bookService.addBook(book);
        return response.data;
      } catch (error) {
        console.error('Error saving book:', error);
      }
    },
    async updateBook(id, book) {
      try {
        const response = await bookService.updateBook(id, book);
        return response.data;
      } catch (error) {
        console.error('Error updating book:', error);
      }
    },
    async loadAuthors() {
      this.authors = await authorService.getAuthors();
    }
  }
}
</script>

<template>
  <div class="books-container">
    <form @submit.prevent="addBook">
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="newBook.title" required>
      </div>
      <div class="form-group">
        <label for="authorId">Author:</label>
        <select id="authorId" v-model.number="newBook.authorId" required>
          <option v-for="author in authors" :value="author.id" :key="author.id">
            {{ author.name }} {{ author.surname }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="pages">Pages:</label>
        <input type="number" id="pages" v-model.number="newBook.pages" required>
      </div>
      <button type="submit">Add Book</button>
    </form>
    <div class="table-container">
      <DataTable
          :headers="tableHeaders"
          :rows="tableRows"
          :rowsPerPage="5"
          :onEdit="onEdit"
          :onDelete="onDelete"
          :nonEditableColumns="['id', 'lenderId']"
          :authors="authors"
      />
    </div>
  </div>
</template>

<style scoped>

.books-container {
  max-width: 600px;
  margin: 2rem auto;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
}

.form-group input[type="text"],
.form-group input[type="number"] {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 0.5rem 1rem;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #367c39;
}

.table-container {
  margin-top: 2.5rem;
}

</style>