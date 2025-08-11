import axios from 'axios';
const API_URL = 'http://localhost:8080/api/exchanges';

export const getExchanges = (userId) => axios.get(`${API_URL}?userId=${userId}`);
