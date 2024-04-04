import axios from 'axios';

const API_URL = '/api/v1/lendings';

const lendBook = async (bookId, lenderId) => {
    try {
        await axios.post(API_URL, { bookId, lenderId });
    } catch (error) {
        console.error('Error lending book:', error);
        throw error;
    }
};

const returnBook = async (bookId, lenderId) => {
    try {
        await axios.post(`${API_URL}/return`, { bookId, lenderId });
    } catch (error) {
        console.error('Error returning book:', error);
        throw error;
    }
};

const getUserLendings = async (lenderId) => {
    try {
        const response = await axios.get(`${API_URL}/lender/${lenderId}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching user lendings:', error);
        throw error;
    }
};

const addLender = async (lenderData) => {
    try {
        const response = await axios.post(`${API_URL}/lender`, lenderData);
        return response.data;
    } catch (error) {
        console.error('Error adding lender:', error);
        throw error;
    }
};

const getAllLenders = async () => {
    try {
        const response = await axios.get(`${API_URL}/lender`);
        return response.data;
    } catch (error) {
        console.error('Error fetching lenders:', error);
        throw error;
    }
}

export default {
    lendBook,
    returnBook,
    getUserLendings,
    addLender,
    getAllLenders
};
