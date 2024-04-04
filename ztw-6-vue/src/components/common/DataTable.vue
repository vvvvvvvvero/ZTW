<template>
  <div>
    <table>
      <thead>
      <tr>
        <th v-for="header in headers" :key="header">{{ header }}</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="row in paginatedData" :key="row.id">
        <td v-for="(value, key) in row" :key="key">
          <template v-if="editingRow !== row.id || nonEditableColumns.includes(key)">
            {{value}}
          </template>
          <template v-else-if="key === 'authorId'">
            <select v-model="editedRowData[key]">
              <option v-for="author in authors" :value="author.id" :key="author.id">
                {{ author.name }} {{ author.surname }}
              </option>
            </select>
          </template>
          <template v-else>
            <input v-model="editedRowData[key]" :type="typeof value === 'number' ? 'number' : 'text'">
          </template>
        </td>
        <td>
          <div class="action-buttons">
            <button v-if="editingRow !== row.id" class="edit-button" @click="startEdit(row)">
              Edit
            </button>
            <button v-else class="save-button" @click="saveEdit(row.id)">
              Save
            </button>
            <button class="remove-button" @click="removeRow(row.id)">Remove</button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage <= 1">Previous</button>
      <span>Page {{ currentPage }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages">Next</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "DataTable",
  props: {
    headers: Array,
    rows: Array,
    rowsPerPage: {
      type: Number,
      default: 10
    },
    onEdit: {
      type: Function
    },
    onAdd: {
      type: Function
    },
    onDelete: {
      type: Function
    },
    nonEditableColumns: {
      type: Array
    },
    authors: Array
  },
  data() {
    return {
      currentPage: 1,
      editingRow: null,
      editedRowData: {},
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.rows.length / this.rowsPerPage);
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.rowsPerPage;
      const end = start + this.rowsPerPage;
      return this.rows.slice(start, end);
    }
  },
  methods: {
    nextPage() {
      if (this.currentPage < this.totalPages) this.currentPage++;
    },
    prevPage() {
      if (this.currentPage > 1) this.currentPage--;
    },

    startEdit(row) {
      this.editingRow = row.id;
      this.editedRowData = { ...row };
    },
    saveEdit(rowId) {
      this.onEdit(rowId, this.editedRowData);
      this.editingRow = null;
    },
    removeRow(rowId) {
      this.onDelete(rowId);
    },
    authorName(id){
      const author = this.authors.find(author => author.id === id);
      return author ? `${author.name} ${author.surname}` : '';
    }
  }
};
</script>

<style scoped>
table {
  width: 80%;
  margin: 0 auto; /* Center the table in its container */
  border-collapse: collapse;
  background-color: white; /* Light background for the table */
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ddd; /* Light border for separation */
}

th {
  background-color: #333; /* Dark background matching the navbar */
  color: white; /* White text for contrast */
  border-radius: 4px 4px 0 0; /* Rounded corners for the table header */
}

tbody tr:hover {
  background-color: #f2f2f2; /* Hover effect for rows */
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 1em;
  align-items: center;
}

.pagination button {
  background-color: #4CAF50; /* Matching the navbar's hover effect */
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  margin: 0 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.pagination button:hover {
  background-color: #367c39; /* Darker shade on hover */
}

.pagination button:disabled {
  background-color: #cccccc;
  cursor: default;
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

/* Style for the edit button to slightly differentiate it */
.edit-button {
  background-color: #6c757d; /* Dark gray for edit */
}

.edit-button:hover {
  background-color: #545b62;
}

.remove-button {
  background-color: #dc3545; /* Red for remove, keeping a clear action distinction */
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

</style>
