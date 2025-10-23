import { useContext, useEffect, useState } from 'react'
import './App.css'
import { BrowserRouter as Router, Route, Routes, Navigate, useLocation } from 'react-router'
import { Button } from '@mui/material'
import { useDispatch } from 'react-redux'
import { setCredentials } from './Stores/authSlice.js'
import { AuthContext } from 'react-oauth2-code-pkce'

function App() {
  const {token, tokenData, logIn, logout, isAuthenticated} = useContext(AuthContext)
  const dispatch = useDispatch(); // Used for sending actions to Redux store to modify global state.
  const [authReady, setIsAuthReady] = useState(false);
  useEffect(() => {
    if (token) {
      dispatch(setCredentials({token, user: tokenData})) // dispact means store in local storage in out stores
    }
  }, [token, tokenData, dispatch])

  return (
    <Router>
        {!token ? (
        <Button 
          variant="contained"
          onClick={() =>{logIn();}}
        >
          Login
        </Button>
      ) : (
        <div>
          <pre>{JSON.stringify(tokenData, null, 2)}</pre>
        </div>
      )}
    </Router>
    
  )
}

export default App
