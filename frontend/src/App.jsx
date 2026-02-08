import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Routes, Route } from 'react-router-dom'
import AuthPages from './pages/AuthPages'
import Home from './pages/Home'
import DuelLobby from './pages/DuelLobby'
import { connectWS } from './webscoketConfig/duelSocket'
import { useAuth } from './context/authContext'
import { useEffect } from 'react'
function App() {
  const [count, setCount] = useState(0);
   const { user, loading } = useAuth();
   useEffect(() => {
    if (!loading && user?.username) {
      connectWS(user.username);
    }
  }, [loading, user]);
  if (loading) return <div>Loading...</div>;
  return (
    <Routes>
      <Route path='/' element={<Home />}/>
      <Route path='/duel' element={<DuelLobby />}/>
      <Route path='/auth' element={<AuthPages />}/>
    </Routes>
  )
}

export default App
