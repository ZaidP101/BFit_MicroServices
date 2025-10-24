import { createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
  name: 'auth',
  initialState : {
    user : JSON.parse(localStorage.getItem("user")) || null,   //
    token : localStorage.getItem("token") || null,            // } first we set some default values initailly 
    userId : localStorage.getItem("userId") || null,         //
  },
  reducers: {                                              // later we update them with actual values
    setCredentials : (state, action) => {
        state.user = action.payload.user
        state.token = action.payload.token
        state.userId = action.payload.user.sub // sub has the userId

        localStorage.setItem('token', action.payload.token)
        localStorage.setItem('user', JSON.stringify(action.payload.user))
        localStorage.setItem('userId', action.payload.user.sub)
    },
    logout : (state) =>{
      state.user = null
        state.token = null
        state.userId = null 

        localStorage.removeIteam('token')
        localStorage.removeIteam('user')
        localStorage.removeItem('userId')
    },
  },
})

export const { setCredentials, logout} = authSlice.actions
export default authSlice.reducer