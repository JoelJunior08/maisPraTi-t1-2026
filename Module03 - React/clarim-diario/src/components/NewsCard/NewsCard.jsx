// ============================================================
//   NEWSCARD — componente REUTILIZÁVEL de cartão de notícia
// ============================================================
import './NewsCard.css'
import { Link } from 'react-router-dom'

// Componente "burro"/de apresentação: não tem estado próprio, só recebe
// dados por PROPS e os exibe. Isso o torna fácil de reusar e de testar
// (veja NewsCard.test.jsx). Desestruturamos as props direto nos parênteses.
function NewsCard ({ id, categoria, titulo, resumo }) {
    return (
        <article className='card'>
            <span className='card__categoria'>{categoria}</span>
            <h3 className='card__titulo'>
                {/* <Link> navega SEM recarregar a página (SPA).
                    Template string monta a URL: /materia/1, /materia/2... */}
                <Link to={`/materia/${id}`}>{titulo}</Link>
            </h3>

            {/* `resumo &&` = só renderiza o <p> se `resumo` tiver conteúdo.
                A manchete tem resumo; cartões menores podem não ter. */}
            {resumo && <p className='card__resumo'>{resumo}</p>}
        </article>
    )
}

export default NewsCard