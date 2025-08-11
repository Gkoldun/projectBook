import React from 'react';

function Profile({ user }) {
  return (
    <div>
      <h2>Профиль</h2>
      <p><b>Логин:</b> {user.username}</p>
      <p><b>ID:</b> {user.id}</p>
    </div>
  );
}

export default Profile;
