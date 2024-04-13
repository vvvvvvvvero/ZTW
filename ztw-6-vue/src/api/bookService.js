import axios from 'axios';

const API_URL = '/api/v1/books';

const getBooks = async () => {
    return axios.get(API_URL);
};

const getBookById = async (id) => {
    return axios.get(`${API_URL}/${id}`);
};

const addBook = async (bookData) => {
    return axios.post(API_URL, bookData);
};

const updateBook = async (id, bookData) => {
    return axios.put(`${API_URL}/${id}`, bookData);
};

const deleteBook = async (id) => {
    return axios.delete(`${API_URL}/${id}`);
};

export default {
    getBooks,
    getBookById,
    addBook,
    updateBook,
    deleteBook
};
