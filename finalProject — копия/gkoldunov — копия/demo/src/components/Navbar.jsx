import React from 'react';
import { Link } from 'react-router-dom';

function Navbar({ user, onLogout }) {
  return (
    <nav className="navbar">
      <Link to="/">Каталог</Link>
      {user && <Link to="/add-book">Добавить книгу</Link>}
      {user && <Link to="/my-books">Мои книги</Link>}
      {user && <Link to="/exchanges">Обмены</Link>}
      {user && <Link to="/profile">Профиль</Link>}
      {user ? (
        <button className="logout" onClick={onLogout}>Выйти</button>
      ) : (
        <>
          <Link to="/login">Войти</Link>
          <Link to="/register">Регистрация</Link>
        </>
      )}
    </nav>
  );
}

export default Navbar;
