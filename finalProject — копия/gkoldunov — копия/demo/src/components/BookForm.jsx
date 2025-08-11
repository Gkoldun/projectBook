import React, { useState } from 'react';
import { addBook } from '../api/books';

function BookForm({ user, onSuccess }) {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [edition, setEdition] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user || !user.id) {
      setError('Пользователь не авторизован');
      return;
    }

    try {
      await addBook({ title, author,edition}, user.id);
      setTitle('');
      setAuthor('');
      setError('');
      setEdition('');
      onSuccess();
    } catch (err) {
      setError('Ошибка при добавлении книги');
      console.error(err);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Название книги"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Автор книги"
        value={author}
        onChange={(e) => setAuthor(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder = "Издательство книги"
        value ={edition}
        onChange ={(e)=> setEdition(e.target.value)}
        required
       />
      {error && <div style={{ color: 'red' }}>{error}</div>}
      <button type="submit">Добавить книгу</button>
    </form>
  );
}

export default BookForm;
