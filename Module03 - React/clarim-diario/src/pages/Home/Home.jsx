// ============================================================
//   PÁGINA HOME — a "capa" do jornal, lista as notícias da API
// ============================================================
import { useState, useEffect } from 'react'
import NewsCard from '../../components/NewsCard/NewsCard'
import { listarNoticias } from '../../services/noticias'
import './Home.css'

function Home() {
  // Trio clássico de estados para carregar dados de uma API:
  const [ noticias, setNoticias ] = useState([])     // os dados em si
  const [ carregando, setCarregando ] = useState(true) // enquanto busca (loading)
  const [ erro, setErro ] = useState('')             // se algo der errado

  // useEffect com array de dependências VAZIO [] roda só UMA vez,
  // logo após a primeira renderização — ideal para buscar dados iniciais.
  useEffect(() => {
    // Definimos uma função async DENTRO do efeito porque a função do
    // useEffect não pode ser async diretamente.
    async function carregar() {
      try {
        setCarregando(true)
        setErro('')
        const dados = await listarNoticias()   // espera a API responder
        setNoticias(dados)
      } catch {
        setErro('Não foi possível carregar as notícias')
      } finally {
        setCarregando(false)  // finally roda deu certo OU errado
      }
    }

    carregar()
  }, [])

  // "Early return": mostramos telas alternativas antes do conteúdo principal.
  if(carregando) return <p className='aviso-tela'>Carregando a edição...</p>
  if(erro) return <p className='aviso-tela'>{erro}</p>

  // Desestruturação com rest (...): a 1ª notícia vira a manchete e o
  // resto vai para `demais`. Ex.: [a, b, c] → manchete=a, demais=[b, c].
  const [ manchete, ...demais ] = noticias

    return (
      <main className='container'>
        <section className='manchete'>
          {/* A manchete usa o mesmo NewsCard, só que em destaque via CSS */}
          <NewsCard
            id = {manchete.id}
            categoria = {manchete.categoria}
            titulo = {manchete.titulo}
            resumo = {manchete.resumo}
          />
        </section>

        <section className='grade'>
          {/* .map() transforma cada notícia em um <NewsCard>.
              A prop `key` é OBRIGATÓRIA em listas: é o "RG" de cada item,
              que o React usa para atualizar só o que mudou (performance). */}
          {demais.map((noticia) => (
            <NewsCard
              key={noticia.id}
              id={noticia.id}
              categoria={noticia.categoria}
              titulo={noticia.titulo}
              resumo={noticia.resumo}
            />
          ))}
        </section>
      </main>
    )
}

export default Home