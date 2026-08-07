// ============================================================
//   ROTA PROTEGIDA — "porteiro" que bloqueia páginas privadas
// ============================================================
// Padrão comum em SPAs: um componente que envolve a página privada.
// Se houver usuário logado, ele deixa passar; senão, redireciona.
import { Navigate } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'

function RotaProtegida({ children }) {
    // Lemos o usuário direto do contexto de autenticação.
    const { usuario } = useAuth()

    // Sem usuário? Renderizamos <Navigate>, que muda a URL para /login.
    // `replace` troca a entrada no histórico em vez de empilhar uma nova,
    // então o botão "voltar" não retorna para a página protegida.
    if (!usuario) {
        return <Navigate to='/login' replace />
    }

    // Com usuário logado, devolvemos `children` = a página que envolvemos
    // (no App.jsx, o <Painel />).
    return children
}

export default RotaProtegida