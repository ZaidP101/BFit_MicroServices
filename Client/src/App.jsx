import { useContext, useEffect, useState } from 'react'
import './App.css'
import { BrowserRouter as Router, Route, Routes, Navigate, useLocation } from 'react-router'
import { Box, Button } from '@mui/material'
import { useDispatch } from 'react-redux'
import { setCredentials } from './Stores/authSlice.js'
import { AuthContext } from 'react-oauth2-code-pkce'
import ActivityForm from './Components/ActivityForm.jsx'
import ActivityList from './Components/ActivityList.jsx'
import ActivityDetails from './Components/ActivityDetails.jsx'

const ActivitiesPage = () => {
  return (
    <Box sx={{ p: 2, border: '1px dashed grey' }}>
      <ActivityForm onActivityAdded = {() => window.location.reload()} />
      <ActivityList/>
    </Box>
  );
}

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
          onClick={() =>{logIn()}}
        >
          Login
        </Button>
      ) : (
        <div>
          <Box component="section" sx={{ p: 2, border: '1px dashed grey' }}>
            <Button
              variant='contained'
              onClick={logout}
            >
              Logout
            </Button>
            <Routes>
              <Route path="activities" element={<ActivitiesPage/>}/>
              <Route path="activities/:id" element={<ActivityDetails/>}/>
              <Route path='/' element={token ? 
                <Navigate to="/activities" replace/> :
                <div>Welcome Please Login</div> } 
              />

            </Routes>
          </Box>
        </div>
      )}
    </Router>
    
  )
}

export default App
