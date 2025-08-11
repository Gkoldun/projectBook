import React, { useEffect, useState } from 'react';
import { getExchanges } from '../api/exchanges';

function Exchanges({ user }) {
  const [exchanges, setExchanges] = useState([]);

  useEffect(() => {
    getExchanges(user.id).then(res => setExchanges(res.data.data));
  }, [user.id]);

  return (
    <div>
      <h2>Мои обмены</h2>
      <ul>
        {exchanges.map(ex => (
          <li key={ex.id}>
            Книга: {ex.bookTitle} <br />
            От кого: {ex.fromUserName} <br />
            Кому: {ex.toUserName} <br />
            Статус: {ex.status}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Exchanges;
