import axios from 'axios'

export const api = axios.create({
    baseURL: '/', // 프록시 사용하니까 상대경로 기반
    timeout: 10000,
})