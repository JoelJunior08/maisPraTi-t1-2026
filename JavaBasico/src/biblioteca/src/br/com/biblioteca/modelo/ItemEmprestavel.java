package br.com.biblioteca.modelo;

import br.com.biblioteca.contrato.Emprestavel;

/*
 * ============================================================
 * ITEMEMPRESTAVEL - classe abstrata INTERMEDIARIA
 * ============================================================
 * Conceito: classe abstrata que implementa uma interface para
 *           concentrar o codigo comum de varias subclasses.
 * ============================================================
 */

/**
 * Base de todos os itens que podem sair da biblioteca.
 *
 * <p><b>Por que esta camada intermediaria existe?</b></p>
 *
 * <p>A logica de emprestar e devolver e IDENTICA para livro, revista, DVD,
 * tese e audiolivro: verificar disponibilidade, verificar o limite do
 * usuario, registrar e marcar. Se cada classe implementasse
 * {@code Emprestavel} diretamente, esse mesmo bloco apareceria cinco vezes.
 * No dia em que a regra mudasse, seriam cinco lugares para corrigir — e um
 * esquecido vira bug silencioso.</p>
 *
 * <p>A solucao e uma classe abstrata que <b>implementa</b> a interface e
 * escreve o codigo comum uma unica vez. As subclasses so precisam informar
 * o que realmente varia: prazo e multa.</p>
 *
 * <p><b>Interface x classe abstrata, na pratica:</b> a interface diz O QUE
 * um emprestavel sabe fazer (contrato). Esta classe diz COMO isso e feito
 * (implementacao compartilhada). Elas nao competem — colaboram.</p>
 *
 * <p>✅ BOA PRATICA: note que a classe permanece {@code abstract}. Ela
 * resolve metade do problema e deixa o resto para os filhos.</p>
 */
public abstract class ItemEmprestavel extends ItemAcervo implements Emprestavel {

    /**
     * Quem esta com o item, ou {@code null} se estiver no acervo.
     *
     * <p>Guardamos a REFERENCIA ao usuario, e nao apenas o nome, para que a
     * devolucao possa atualizar os dois lados da relacao sem que quem chama
     * precise informar o usuario de novo.</p>
     */
    private Usuario usuarioAtual;

    protected ItemEmprestavel(String codigo, String titulo, int ano) {
        // super(...) chama o construtor de ItemAcervo e PRECISA ser a primeira
        // instrucao. A construcao acontece de cima para baixo na hierarquia:
        // ItemAcervo termina de montar sua parte antes desta classe comecar.
        super(codigo, titulo, ano);
        this.usuarioAtual = null;
    }

    /**
     * Regra de emprestimo comum a todos os itens emprestaveis.
     *
     * <p>Escrita UMA vez, herdada por cinco classes.</p>
     *
     * <p>Repare que este metodo nao pergunta o TIPO do usuario em nenhum
     * momento. Ele chama {@code usuario.podeEmprestarMais()}, e cada tipo de
     * usuario responde conforme seu proprio limite. Polimorfismo substituindo
     * uma cadeia de {@code if}.</p>
     */
    @Override
    public boolean emprestar(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        }

        if (!isDisponivel()) {
            System.out.printf("   [X] %-22s tentou \"%s\" - item indisponivel%n",
                    usuario.getNome(), getTitulo());
            return false;
        }

        if (!usuario.podeEmprestarMais()) {
            System.out.printf("   [X] %-22s tentou \"%s\" - limite de %d item(s) atingido%n",
                    usuario.getNome(), getTitulo(), usuario.getLimiteItens());
            // ✅ %d item(s): evita a concordancia errada quando o limite e 1
            return false;
        }

        usuario.registrarEmprestimo(this);
        marcarComoEmprestado();               // metodo protected herdado de ItemAcervo
        this.usuarioAtual = usuario;

        System.out.printf("   [OK] %-21s pegou \"%s\"  [%d/%d]%n",
                usuario.getNome(), getTitulo(),
                usuario.getQuantidadeEmprestada(), usuario.getLimiteItens());
        return true;
    }

    /**
     * Devolucao comum a todos os itens emprestaveis.
     *
     * <p>Atualiza os DOIS lados da relacao: o item volta a ficar disponivel e
     * o usuario deixa de conta-lo entre os seus. Deixar isso a cargo de quem
     * chama seria pedir para o estado ficar inconsistente.</p>
     */
    @Override
    public boolean devolver() {
        if (isDisponivel()) {
            return false;                     // ja estava no acervo
        }
        if (usuarioAtual != null) {
            // registrarDevolucao tem visibilidade de PACOTE. Item e Usuario
            // moram no mesmo pacote, entao a colaboracao entre eles e possivel
            // sem expor esse metodo ao resto do sistema.
            usuarioAtual.registrarDevolucao(this);
        }
        marcarComoDisponivel();
        this.usuarioAtual = null;
        return true;
    }

    /** Quem esta com o item, ou {@code null} se estiver disponivel. */
    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    /** Nome de quem esta com o item, ou "-" se estiver disponivel. */
    public String getNomeUsuarioAtual() {
        return usuarioAtual == null ? "-" : usuarioAtual.getNome();
    }
}
