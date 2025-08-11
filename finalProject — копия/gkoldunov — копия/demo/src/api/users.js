import axios from 'axios';
const API_URL = 'http://localhost:8080/user';

export const registerUser = (data) => axios.post(`${API_URL}/signup`, data);
export const loginUser = (data) => axios.post(`${API_URL}/signin`, data);
