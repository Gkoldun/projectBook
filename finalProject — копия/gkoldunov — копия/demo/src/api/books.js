import axios from "axios";

const API_URL = "http://localhost:8080/api/books";

export const getBooks = () => axios.get(API_URL);

export const addBook = (data, ownerId) =>
  axios.post(`${API_URL}?ownerId=${ownerId}`, data);

export const takeBook = (bookId, userId) =>
  axios.post(`${API_URL}/take?bookId=${bookId}&userId=${userId}`);

export const returnBook = (bookId, userId) =>
  axios.post(`${API_URL}/return?bookId=${bookId}&userId=${userId}`);

export const getUserBooks = (userId) => axios.get(`${API_URL}/user-books?userId=${userId}`);

