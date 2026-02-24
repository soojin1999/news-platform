import { api } from './client'

export async function fetchTopKeywords({ time } = {}) {
    const params = {}
    if (time) params.time = time

    const res = await api.get('/api/dashboard/top', { params })
    return res.data
}