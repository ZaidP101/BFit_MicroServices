import React from 'react'
import ReactDOM from 'react-dom/client'

import { Provider } from 'react-redux'
import { store } from './Stores/stores.js'
import { AuthProvider } from 'react-oauth2-code-pkce'

import App from './App'
import { authConfig } from './AuthConfig.js'

// As of React 18
const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <AuthProvider authConfig={authConfig}>
    <Provider store={store}>
      <App />
    </Provider>,
  </AuthProvider>
)