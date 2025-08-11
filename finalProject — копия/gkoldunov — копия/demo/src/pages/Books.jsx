import React, { useEffect, useState } from 'react';
import { getBooks, takeBook, returnBook } from '../api/books';
import BookForm from '../components/BookForm';

function Books({ user }) {
  const [books, setBooks] = useState([]);
  const [filter, setFilter] = useState('');
  const [adding, setAdding] = useState(false);

   console.log('User in Books component:', user);

  useEffect(() => {
    getBooks()
      .then(res => setBooks(res.data.data))
      .catch(err => console.error(err));
  }, []);

  const refresh = () => {
    getBooks()
      .then(res => setBooks(res.data.data))
      .catch(err => console.error(err));
  };

  const handleTake = (bookId) => {
    takeBook(bookId, user.id).then(refresh);
  };

  const handleReturn = (bookId) => {
    returnBook(bookId, user.id).then(refresh);
  };

  return (
    <div>
      <h2>Каталог книг</h2>
      <input
        placeholder="Поиск по названию..."
        value={filter}
        onChange={e => setFilter(e.target.value)}
      />
      <ul>
        {Array.isArray(books) && books
          .filter(b => b.title.toLowerCase().includes(filter.toLowerCase()))
          .map(book => (
            <li key={book.id}>
              <b>{book.title}</b> — {book.author} [{book.available ? 'Свободна' : 'Занята'}]
              {user && (
                book.available ? (
                  <button onClick={() => handleTake(book.id)}>Взять</button>
                ) : (
                  book.currentUserId === user.id && (
                    <button onClick={() => handleReturn(book.id)}>Вернуть</button>
                  )
                )
              )}
            </li>
          ))}
      </ul>
      {user && (
        <button onClick={() => setAdding(a => !a)}>
          {adding ? "Скрыть форму" : "Добавить новую книгу"}
        </button>
      )}
      {adding && <BookForm onSuccess={() => { setAdding(false); refresh(); }} user={user} />}
    </div>
  );
}

export default Books;

