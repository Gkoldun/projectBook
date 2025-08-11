import React, { useEffect, useState } from 'react';
import { getUserBooks } from '../api/books';

function MyBooks({ user }) {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    if (!user?.id) return;
    getUserBooks(user.id).then(res => setBooks(res.data.data)).catch(() => setBooks([]));
  }, [user?.id]);

  return (
    <div>
      <h2>Мои книги</h2>
      {books.length === 0 ? (
        <p>У вас пока нет книг</p>
      ) : (
        <ul>
          {books.map(book => (
            <li key={book.id}>
              <b>{book.title}</b> — {book.author}
              {book.ownerId === user.id && <span> (Добавлена мной)</span>}
              {book.currentUserId === user.id && <span> (Сейчас у меня)</span>}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default MyBooks;
