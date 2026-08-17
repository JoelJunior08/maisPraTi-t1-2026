package br.com.biblioteca.contrato;

import br.com.biblioteca.modelo.Usuario;

/*
 * ============================================================
 * EMPRESTAVEL - o contrato mais importante do sistema
 * ============================================================
 * Conceitos: interface como contrato, metodo default,
 *            "a regra de negocio vira TIPO".
 * ============================================================
 */

/**
 * Contrato de tudo que pode sair da biblioteca.
 *
 * <p><b>ESTE E O PONTO MAIS IMPORTANTE DO PROJETO INTEIRO.</b></p>
 *
 * <p>A classe {@code ObraReferencia} NAO implementa esta interface. Isso
 * significa que a regra de negocio "obra de referencia nunca e emprestada"
 * deixou de ser um {@code if} espalhado pelo codigo e virou o proprio
 * SISTEMA DE TIPOS. O compilador impede o emprestimo:</p>
 *
 * <pre>
 * ObraReferencia dicionario = new ObraReferencia(...);
 * dicionario.emprestar(usuario);   // NAO COMPILA — metodo nao existe
 * </pre>
 *
 * <p>Compare com a alternativa ruim, que muitos alunos escrevem primeiro:
 * fazer {@code ObraReferencia} herdar {@code emprestar()} e sobrescrever
 * o metodo lancando uma excecao. Isso "funciona", mas o erro so aparece em
 * EXECUCAO, e a classe passa a mentir sobre o que sabe fazer.</p>
 *
 * <p>Sinal de alerta geral de projeto: se voce esta sobrescrevendo um
 * metodo herdado apenas para DESATIVA-LO, a heranca esta errada.</p>
 *
 * <p><b>Por que getPrazoEmprestimoDias() e getMultaPorDia() estao AQUI,
 * e nao em ItemAcervo?</b> Porque prazo e multa so fazem sentido para
 * itens que podem ser emprestados. Colocar esses metodos na classe
 * abstrata {@code ItemAcervo} obrigaria {@code ObraReferencia} a responder
 * "quantos dias de prazo?" — uma pergunta que nao existe para ela.
 * Cada membro deve viver no tipo mais restrito onde ainda faz sentido.</p>
 */
public interface Emprestavel {

    /** Prazo de devolucao, em dias, especifico de cada tipo de item. */
    int getPrazoEmprestimoDias();

    /** Valor da multa por dia de atraso, especifico de cada tipo de item. */
    double getMultaPorDia();

    /**
     * Tenta emprestar o item ao usuario informado.
     *
     * @return {@code true} se o emprestimo foi efetivado
     */
    boolean emprestar(Usuario usuario);

    /**
     * Devolve o item ao acervo.
     *
     * @return {@code true} se a devolucao foi efetivada
     */
    boolean devolver();

    // ------------------------------------------------------------
    // METODOS DEFAULT (Java 8+)
    // ------------------------------------------------------------
    // Metodos default tem CORPO dentro da interface. Toda classe que
    // implementa Emprestavel ganha estes metodos de graca, e pode
    // sobrescreve-los se precisar.
    //
    // Por que isso existe? Para permitir ADICIONAR metodos a uma interface
    // sem quebrar todo o codigo que ja a implementava. Antes do Java 8,
    // acrescentar um metodo a uma interface publica quebrava o mundo.
    // ------------------------------------------------------------

    /**
     * Indica se o item aceita renovacao do prazo.
     *
     * <p>O padrao e {@code false}: a maioria dos itens nao renova.
     * Apenas {@code Livro} sobrescreve para {@code true}.</p>
     */
    default boolean permiteRenovacao() {
        return false;
    }

    /**
     * Calcula a multa bruta de atraso, ANTES do desconto do usuario.
     *
     * <p>Repare que um metodo {@code default} pode chamar metodos
     * ABSTRATOS da propria interface ({@code getMultaPorDia()}).
     * Em tempo de execucao sempre havera uma implementacao real
     * respondendo — o mesmo mecanismo do polimorfismo.</p>
     *
     * @param diasAtraso dias de atraso; valores nao positivos geram multa zero
     */
    default double calcularMulta(int diasAtraso) {
        if (diasAtraso <= 0) {
            return 0.0;
        }
        return diasAtraso * getMultaPorDia();
    }
}
