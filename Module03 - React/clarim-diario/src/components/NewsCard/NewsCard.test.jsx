// ============================================================
//   TESTE DE COMPONENTE — React Testing Library
// ============================================================
// render  → "monta" o componente numa DOM virtual de teste.
// screen  → como quem OLHA a tela: busca elementos por texto, papel, etc.
import { render, screen } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import NewsCard from './NewsCard'

// describe agrupa testes relacionados; it (ou test) descreve UM caso.
describe('NewsCard', () => {
    it('mostra a categoria e o título recebidos por props', () => {
        // O NewsCard usa <Link>, que EXIGE um Router por volta.
        // MemoryRouter é um router "de mentira", perfeito para testes.
        render(
            <MemoryRouter>
                <NewsCard id={1} categoria="Cidade" titulo="Metrô terá horário estendido" />
            </MemoryRouter>
        )

        // Padrão AAA (Arrange-Act-Assert): aqui está o ASSERT.
        // Afirmamos que os textos passados por props aparecem na tela.
        expect(screen.getByText('Cidade')).toBeInTheDocument()
        expect(screen.getByText('Metrô terá horário estendido')).toBeInTheDocument()
    })
})