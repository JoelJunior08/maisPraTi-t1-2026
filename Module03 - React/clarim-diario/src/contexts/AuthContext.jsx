// ============================================================
//   CONTEXT API — compartilhando o "usuário logado" com o app todo
// ============================================================
// Problema que o Context resolve: sem ele, para o Header saber quem
// está logado, teríamos que passar o `usuario` por props através de
// TODOS os componentes no caminho (o chamado "prop drilling").
// O Context cria um "canal direto": quem quiser o dado, se inscreve.

import { createContext, useContext, useState } from "react";

// createContext cria o "canal". O valor null é o padrão caso alguém
// tente ler o contexto sem um Provider por cima (tratamos isso no useAuth).
const AuthContext = createContext(null);

// O Provider é o componente que GUARDA o estado e o DISPONIBILIZA.
// `children` são todos os componentes que ele envolve (veja o main.jsx).
export function AuthProvider({ children }) {
    // Lazy init: tentamos "reidratar" o usuário salvo no localStorage,
    // para que o login sobreviva a um F5 (refresh) da página.
    const [usuario, setUsuario] = useState(() => {
        const salvo = localStorage.getItem('usuario')
        return salvo ? JSON.parse(salvo) : null   // JSON.parse: texto → objeto
    })

    // ⚠️ Didático: em produção NUNCA valide senha no front-end assim.
    // A verificação de credenciais deve acontecer no back-end/API.
    function login(email, senha) {
        if (email === 'jonah@clarim.com' && senha === 'odeioaranha123') {
            const dados = { nome: 'J. Jonah Jameson', email }
            setUsuario(dados)
            // JSON.stringify: objeto → texto (localStorage só guarda strings)
            localStorage.setItem('usuario', JSON.stringify(dados))
            return
        }

        // Lançar erro deixa a tela de Login decidir COMO exibir a falha.
        throw new Error('E-mail ou senha incorretos.')
    }

    function logout() {
        setUsuario(null)
        localStorage.removeItem('usuario')
    }

    // value = o "pacote" que fica disponível para quem consumir o contexto.
    return (
        <AuthContext.Provider value={{ usuario, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}

// Hook customizado: em vez de cada componente chamar useContext(AuthContext),
// eles chamam useAuth(). Além de mais legível, centralizamos aqui a checagem
// de uso correto (evita bugs difíceis quando falta o Provider).
export function useAuth() {
    const contexto = useContext(AuthContext)
    if (!contexto) {
        throw new Error('useAuth deve ser usado dentro de <AuthProvider>')
    }

    return contexto
}
