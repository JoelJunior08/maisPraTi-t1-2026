import axios from 'axios'

const api = axios.create({ baseURL: 'http://localhost:3333' })

const esperar = (ms) => new Promise(resolve => setTimeout(resolve, ms))

export async function listarNoticias() {
    await esperar(1000)
    const { data } = await api.get('/noticias')
    return data
}

export async function buscarNoticia(id) {
    await esperar(1000)
    const { data } = await api.get(`/noticias/${id}`)
    return data
}