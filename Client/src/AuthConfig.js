import { AuthContext, AuthProvider, TAuthConfig, TRefreshTokenExpiredEvent } from "react-oauth2-code-pkce"

export const authConfig  = {
  clientId: 'OAuth-client',
  authorizationEndpoint: 'http://localhost:8181/realms/B-Fit_MicroServices/protocol/openid-connect/auth',
  tokenEndpoint: 'http://localhost:8181/realms/B-Fit_MicroServices/protocol/openid-connect/token',
  redirectUri: 'http://localhost:5173/',
  scope: 'openid profile roles email',
  onRefreshTokenExpire: (event) => event.logIn(),
}