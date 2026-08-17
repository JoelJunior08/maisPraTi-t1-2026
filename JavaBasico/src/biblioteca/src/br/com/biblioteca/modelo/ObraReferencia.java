package br.com.biblioteca.modelo;

/*
 * ============================================================
 * OBRAREFERENCIA - A PECA MAIS IMPORTANTE DO PROJETO
 * ============================================================
 * Conceito: quando a regra de negocio vira TIPO, o compilador
 *           passa a ser seu fiscal.
 * ============================================================
 */

/**
 * Obra de consulta local: dicionarios, enciclopedias, atlas.
 * NUNCA sai da biblioteca.
 *
 * <p><b>Repare no que esta classe NAO faz:</b> ela estende
 * {@code ItemAcervo} diretamente, e nao {@code ItemEmprestavel}. Portanto
 * NAO implementa {@code Emprestavel} e simplesmente NAO POSSUI os metodos
 * {@code emprestar()}, {@code devolver()}, {@code getPrazoEmprestimoDias()}
 * nem {@code getMultaPorDia()}.</p>
 *
 * <p>A consequencia e a razao de ser deste exercicio inteiro:</p>
 *
 * <pre>
 * ObraReferencia dicionario = new ObraReferencia("R001", "Houaiss", 2009, "Dicionario");
 * dicionario.emprestar(ana);   // NAO COMPILA — o metodo nao existe
 * </pre>
 *
 * <p>A regra "obra de referencia nunca e emprestada" deixou de ser um
 * comentario, uma convencao ou um {@code if} que alguem pode esquecer.
 * Ela virou o SISTEMA DE TIPOS. E impossivel violar essa regra, porque o
 * programa nem chega a ser gerado.</p>
 *
 * <hr>
 *
 * <p><b>A alternativa ruim — e por que ela e ruim.</b></p>
 *
 * <p>Muitos alunos escrevem, no primeiro impulso:</p>
 *
 * <pre>
 * public class ObraReferencia extends ItemEmprestavel {
 *     &#64;Override
 *     public boolean emprestar(Usuario u) {
 *         throw new UnsupportedOperationException("Nao pode ser emprestado");
 *     }
 * }
 * </pre>
 *
 * <p>Isso "funciona", mas tem tres defeitos graves:</p>
 *
 * <ol>
 *   <li>O erro so aparece em EXECUCAO — potencialmente com o usuario na
 *       frente do balcao, nao na maquina do desenvolvedor.</li>
 *   <li>A classe MENTE sobre o que sabe fazer. Ela se apresenta como
 *       emprestavel e depois recusa.</li>
 *   <li>Ela quebra o contrato que herdou. Quem recebe um
 *       {@code ItemEmprestavel} tem o direito de esperar que emprestar
 *       funcione.</li>
 * </ol>
 *
 * <p>⚠️ SINAL DE ALERTA GERAL: sempre que voce se pegar sobrescrevendo um
 * metodo herdado apenas para DESATIVA-LO — lancando excecao ou deixando o
 * corpo vazio — a heranca esta errada. O tipo que voce escolheu como pai nao
 * descreve o seu objeto.</p>
 */
public class ObraReferencia extends ItemAcervo {

    private final String tipoObra;
    private final String localizacaoEstante;

    public ObraReferencia(String codigo, String titulo, int ano,
                          String tipoObra, String localizacaoEstante) {
        super(codigo, titulo, ano);

        if (tipoObra == null || tipoObra.isBlank()) {
            throw new IllegalArgumentException("Tipo da obra e obrigatorio.");
        }

        this.tipoObra = tipoObra;
        this.localizacaoEstante = localizacaoEstante;
    }

    /**
     * Registra uma consulta feita no local.
     *
     * <p>Este e o UNICO uso possivel de uma obra de referencia — e ele nao
     * altera a disponibilidade, porque a obra nunca deixa de estar
     * disponivel para o proximo consulente.</p>
     */
    public void consultarNoLocal(Usuario usuario) {
        System.out.printf("   [OK] %-21s consultou \"%s\" na estante %s%n",
                usuario.getNome(), getTitulo(), localizacaoEstante);
    }

    @Override
    public String getCategoria() {
        return "REFER.";
    }

    @Override
    public String getDescricaoDetalhada() {
        return tipoObra + " | Estante " + localizacaoEstante + " | CONSULTA LOCAL";
    }

    public String getLocalizacaoEstante() {
        return localizacaoEstante;
    }
}
