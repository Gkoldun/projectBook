import React, { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import Books from './pages/Books';
import AddBook from './pages/AddBook';
import MyBooks from './pages/MyBooks';
import Exchanges from './pages/Exchanges';
import Login from './pages/Login';
import Register from './pages/Register';
import Profile from './pages/Profile';

function App() {



  const [user, setUser] = useState(
    JSON.parse(localStorage.getItem('user')) || null
  );

  const handleLogin = (user) => {
    setUser(user);
    localStorage.setItem('user', JSON.stringify(user));
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  return (
    <BrowserRouter>
      <Navbar user={user} onLogout={handleLogout} />
      <div className="container">
        <Routes>
          <Route path="/" element={<Books user={user} />} />
          <Route path="/books" element={<Books user={user} />} />
          <Route path="/add-book" element={user ? <AddBook user={user} /> : <Navigate to="/login" />} />
          <Route path="/my-books" element={user ? <MyBooks user={user} /> : <Navigate to="/login" />} />
          <Route path="/exchanges" element={user ? <Exchanges user={user} /> : <Navigate to="/login" />} />
          <Route path="/profile" element={user ? <Profile user={user} /> : <Navigate to="/login" />} />
          <Route path="/login" element={<Login onLogin={handleLogin} />} />
          <Route path="/register" element={<Register onLogin={handleLogin} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
