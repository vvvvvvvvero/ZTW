import axios from 'axios';

const API_URL = '/api/v1/authors'; // Adjust if you have a different base URL

const getAuthors = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error('Error fetching authors:', error);
        throw error;
    }
};

const getAuthorById = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching author:', error);
        throw error;
    }
};

const addAuthor = async (authorData) => {
    try {
        const response = await axios.post(API_URL, authorData);
        return response.data;
    } catch (error) {
        console.error('Error adding author:', error);
        throw error;
    }
};

const updateAuthor = async (id, authorData) => {
    try {
        const response = await axios.put(`${API_URL}/${id}`, authorData);
        return response.data;
    } catch (error) {
        console.error('Error updating author:', error);
        throw error;
    }
};

const deleteAuthor = async (id) => {
    try {
        await axios.delete(`${API_URL}/${id}`);
    } catch (error) {
        console.error('Error deleting author:', error);
        throw error;
    }
};

export default {
    getAuthors,
    getAuthorById,
    addAuthor,
    updateAuthor,
    deleteAuthor
};
