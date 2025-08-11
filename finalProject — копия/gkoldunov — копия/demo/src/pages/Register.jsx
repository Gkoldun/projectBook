import React, { useState } from 'react';
import { registerUser } from '../api/users';
import { useNavigate } from 'react-router-dom';

function Register({ onLogin }) {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = e => {
    e.preventDefault();
    registerUser({ login, password })
      .then(res => {
        onLogin(res.data.data);
        navigate('/');
      })
      .catch(error => {
        const message = error.response?.data?.message || 'Ошибка регистрации';
        setError(message);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Регистрация</h2>
      {error && <div style={{ color: 'red' }}>{error}</div>}
      <input
        placeholder="Логин"
        value={login}
        onChange={e => setLogin(e.target.value)}
        required
      />
      <input
        placeholder="Пароль"
        type="password"
        value={password}
        onChange={e => setPassword(e.target.value)}
        required
      />
      <button type="submit">Зарегистрироваться</button>
    </form>
  );
}

export default Register;
