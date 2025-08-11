import React from 'react';
import BookForm from '../components/BookForm';

function AddBook({ user }) {
  return (
    <div>
      <h2>Добавить новую книгу</h2>
      <BookForm
        user={user}
        onSuccess={() => window.location.href = '/'}
      />
    </div>
  );
}

export default AddBook;
