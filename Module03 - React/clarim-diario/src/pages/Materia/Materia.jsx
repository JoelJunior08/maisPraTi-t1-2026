// ============================================================
//   PÁGINA MATÉRIA — exibe UMA notícia, escolhida pela URL
// ============================================================
import { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import { buscarNoticia } from '../../services/noticias'
import './Materia.css'

function Materia() {
  // useParams lê os parâmetros dinâmicos da rota. No App.jsx definimos
  // "/materia/:id" — então aqui `id` recebe o que estiver na URL.
  const { id } = useParams()

  // Mesmo trio de estados da Home (dado + carregando + erro).
  const [noticia, setNoticia] = useState(null)
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')

  useEffect(() => {
    async function carregar() {
      try {
        setCarregando(true)
        setErro('')
        const dados = await buscarNoticia(id)
        setNoticia(dados)
      } catch {
        setErro('Matéria não encontrada — nem o Homem-Aranha some tão rápido.')
      } finally {
        setCarregando(false)
      }
    }
    carregar()
    // A dependência é [id]: se o usuário navegar de /materia/1 para
    // /materia/2, o `id` muda e o efeito RE-executa, buscando a nova matéria.
  }, [id])

  if (carregando) {
    return <p className="aviso-tela">Carregando a matéria…</p>
  }

  if (erro) {
    return (
      <main className="container materia">
        <p className="aviso-tela">{erro}</p>
        <p style={{ textAlign: 'center' }}>
          <Link to="/">← Voltar à capa</Link>
        </p>
      </main>
    )
  }

  return (
    <main className="container materia">
      <Link to="/" className="materia__voltar">← Voltar à capa</Link>

      <span className="materia__categoria">{noticia.categoria}</span>
      <h1>{noticia.titulo}</h1>
      <p className="materia__resumo">{noticia.resumo}</p>

      <div className="materia__texto">
        <p>{noticia.texto}</p>
      </div>
    </main>
  )
}

export default Materia