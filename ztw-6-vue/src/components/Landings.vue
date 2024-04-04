<script>
import DataTable from "@/components/common/DataTable.vue";
import landingService from "@/api/landingsService.js";

export default {
  name: "Landings",
  components: {DataTable},
  data() {
    return {
      tableHeaders: ['Book ID'],
      selectedLenderId: null,
      landings: [],
      lenders: [],
      newLending: {
        bookId: ''
      },
      newLender: {
        name: '',
        surname: '',
        email: ''
      },
      showAddLenderForm: false,
    };
  },
  mounted() {
    this.loadLenders();
  },
  watch: {
    selectedLenderId(newVal) {
      if (newVal) {
        this.loadLandings();
      } else {
        this.landings = [];
      }
    }
  },
  methods: {
    async lendBook() {
      if (!this.selectedLenderId) return;
      try {
        await landingService.lendBook(this.newLending.bookId, this.selectedLenderId);
        await this.loadLandings();
        this.newLending.bookId = '';
      } catch (error) {
        console.error('Error lending book:', error);
      }
    },
    async returnBook(lendingId) {
      try {
        await landingService.returnBook(lendingId);
        await this.loadLandings();
      } catch (error) {
        console.error('Error returning book:', error);
      }
    },
    async loadLandings() {
      try {
        this.landings = await landingService.getUserLendings(this.selectedLenderId)


        this.landings = this.landings.map(lending => {
          console.log('Lending:', lending)
          return {
            bookId: lending.bookId
          };
        });
        console.log('Landings:', JSON.stringify(this.landings))
      } catch (error) {
        console.error('Error loading lendings:', error);
      }
    },
    async loadLenders() {
      try {
        const response = await landingService.getAllLenders();
        this.lenders = response;
      } catch (error) {
        console.error('Error loading lenders:', error);
      }
    },

    async addLender() {
      try {
        await landingService.addLender(this.newLender);
        this.newLender = { name: '', surname: '', email: '' };
        await this.loadLenders(); // Reload the lenders list
        this.showAddLenderForm = false; // Hide the form after adding the lender
      } catch (error) {
        console.error('Error adding lender:', error);
      }
    },
    toggleAddLenderForm() {
      this.showAddLenderForm = !this.showAddLenderForm;
    },
  }
}
</script>


<template>
  <div class="landings-container">

    <button v-if="!showAddLenderForm" @click="toggleAddLenderForm">Add Lender</button>
    <form v-if="showAddLenderForm" @submit.prevent="addLender">
      <div class="form-group">
        <label for="lenderName">Lender Name:</label>
        <input type="text" id="lenderName" v-model="newLender.name" required>
      </div>
      <div class="form-group">
        <label for="lenderSurname">Lender Surname:</label>
        <input type="text" id="lenderSurname" v-model="newLender.surname" required>
      </div>
      <div class="form-group">
        <label for="lenderEmail">Lender Email:</label>
        <input type="email" id="lenderEmail" v-model="newLender.email" required>
      </div>
      <button type="submit">Add New Lender</button>
    </form>

    <div class="form-group">
      <label for="lender">Select Lender:</label>
      <select id="lender" v-model="selectedLenderId">
        <option disabled value="">Please select a lender</option>
        <option v-for="lender in lenders" :value="lender.id" :key="lender.id">
          {{ lender.name }}
        </option>
      </select>
    </div>
    <div v-if="selectedLenderId">
      <form @submit.prevent="lendBook">
        <div class="form-group">
          <label for="bookId">Book ID:</label>
          <input type="number" id="bookId" v-model.number="newLending.bookId" required>
        </div>
        <button type="submit">Lend Book</button>
      </form>
      <div class="table-container">
        <DataTable
            :headers="tableHeaders"
            :rows="landings"
            :rowsPerPage="5"
            :onDelete="returnBook"
            :nonEditableColumns="[]"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.landings-container {
  max-width: 600px;
  margin: 2rem auto;
  padding: 1rem;
  background-color: white;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
}

.form-group input[type="number"],
.form-group input[type="text"] {
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
  margin-top: 0.5rem;
}

button:hover {
  background-color: #367c39;
}

.table-container {
  margin-top: 2.5rem;
}

/* DataTable styles */
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #333;
  color: white;
  border-radius: 4px 4px 0 0;
}

tbody tr:hover {
  background-color: #f2f2f2;
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
  background-color: #6c757d;
}

.edit-button:hover {
  background-color: #545b62;
}

.remove-button {
  background-color: #dc3545;
}

.remove-button:hover {
  background-color: #c82333;
}

.save-button {
  background-color: #4CAF50;
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
