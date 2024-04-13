<script>
import DataTable from "@/components/common/DataTable.vue";
import authorService from "@/api/authorService.js";

export default {
  name: "Authors",
  components: { DataTable },
  mounted() {
    this.loadAuthors();
  },
  data() {
    return {
      tableHeaders: ['Id', 'First Name', 'Last Name'],
      tableRows: [],
      newAuthor: {
        name: '',
        surname: ''
      }
    };
  },
  methods: {
    addAuthor() {
      console.log('Adding author:', this.newAuthor)
      authorService.addAuthor(this.newAuthor)
          .then(author => {
            this.tableRows.push(author);
            this.newAuthor = { name: '', surname: '' };
          })
          .catch(error => console.error('Error adding author:', error));
    },
    onEdit(rowId, editedData) {
      authorService.updateAuthor(rowId, editedData)
          .then(updatedAuthor => {
            const index = this.tableRows.findIndex(author => author.id === rowId);
            if (index !== -1) {
              this.tableRows[index] = updatedAuthor;
            }
          })
          .catch(error => console.error('Error updating author:', error));
    },
    onDelete(rowId) {
      authorService.deleteAuthor(rowId)
          .then(() => {
            this.tableRows = this.tableRows.filter(author => author.id !== rowId);
          })
          .catch(error => console.error('Error deleting author:', error));
    },
    loadAuthors() {
      authorService.getAuthors()
          .then(response => {
            this.tableRows = response;
          })
          .catch(error => console.error('Error fetching authors:', error));
    }
  }
}
</script>

<template>
  <div class="authors-container">
    <form @submit.prevent="addAuthor">
      <div class="form-group">
        <label for="name">First Name:</label>
        <input type="text" id="name" v-model="newAuthor.name" required>
      </div>
      <div class="form-group">
        <label for="surname">Last Name:</label>
        <input type="text" id="surname" v-model="newAuthor.surname" required>
      </div>
      <button type="submit">Add Author</button>
    </form>
    <div class="table-container">
      <DataTable
          :headers="tableHeaders"
          :rows="tableRows"
          :rowsPerPage="5"
          :onEdit="onEdit"
          :onDelete="onDelete"
          :nonEditableColumns="['id']"
      />
    </div>
  </div>
</template>

<style scoped>
.authors-container {
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

.form-group input[type="text"] {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 0.5rem 1rem;
  background-color: #4CAF50; /* Green color for buttons */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #367c39; /* Darker green on hover */
}

.table-container {
  margin-top: 2.5rem;
}

/* DataTable styles */
table {
  width: 100%;
  margin: 0 auto;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #333; /* Dark background for headers */
  color: white;
  border-radius: 4px 4px 0 0;
}

tbody tr:hover {
  background-color: #f2f2f2; /* Hover effect for rows */
}

.action-buttons {
  display: flex;
  justify-content: center;
}

.action-buttons button {
  color: white;
  border: none;
  padding: 6px 12px;
  margin: 0 5px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.edit-button {
  background-color: #6c757d; /* Dark gray for edit */
}

.edit-button:hover {
  background-color: #545b62;
}

.remove-button {
  background-color: #dc3545; /* Red for remove */
}

.remove-button:hover {
  background-color: #c82333;
}

.save-button {
  background-color: #4CAF50; /* Green for save */
}

.save-button:hover {
  background-color: #367c39;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 1em;
  align-items: center;
}

.pagination button {
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  margin: 0 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.pagination button:hover {
  background-color: #367c39;
}

.pagination button:disabled {
  background-color: #cccccc;
  cursor: default;
}
</style>
