import axios from "axios"
import { configs } from "eslint-plugin-react-refresh";

const API_URL = 'http://localhost:8080/api'

const api = axios.create({
    baseURL: API_URL
})

api.interceptors.request.use((config) => {
    const userId =  localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    if(token){
        config.headers['Authorization'] = `Bearer ${token}`;
        config.headers['X-User-ID'] = userId;
    }
    return config;
})

export const getActivities = () => api.get('/activities');
export const addActivity = (activity) => api.post('/activities', activity);
export const getActivityDetails = (id) => api.get(`/recommendations/activity/${id}`);
