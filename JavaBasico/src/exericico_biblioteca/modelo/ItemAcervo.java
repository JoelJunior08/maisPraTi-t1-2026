package exericico_biblioteca.modelo;

import exericico_biblioteca.contrato.Config;

/*
 * ============================================================
 * ITEMACERVO - classe abstrata, raiz da hierarquia de itens
 * ============================================================
 * Pilares demonstrados:
 *   ABSTRACAO      - so o que TODO item do acervo compartilha
 *   ENCAPSULAMENTO - atributos private, invariantes no construtor,
 *                    disponibilidade sem setter publico
 *   HERANCA        - base comum de todas as categorias
 *   POLIMORFISMO   - getCategoria() e getDescricaoDetalhada() abstratos
 * ============================================================
 */

/**
 * Representa qualquer item do acervo, emprestavel ou nao.
 *
 * <p><b>Por que esta classe e {@code abstract}?</b> Porque "item de acervo"
 * e um CONCEITO UNIFICADOR, nao uma coisa concreta. Ninguem cadastra um
 * "item generico" na biblioteca: cadastra um livro, uma revista, um DVD.
 * Marcar a classe como abstrata transforma essa regra em erro de compilacao:</p>
 *
 * <pre>
 * new ItemAcervo("A1", "Qualquer coisa", 2020);   // NAO COMPILA
 * </pre>
 *
 * <p><b>O que NAO esta aqui:</b> prazo de emprestimo e multa por dia. Eles
 * vivem na interface {@code Emprestavel}, porque nao fazem sentido para
 * {@code ObraReferencia}. Colocar um membro em uma classe base "porque quase
 * todos os filhos usam" e a origem de muito codigo ruim — o quase e o problema.</p>
 */
public abstract class ItemAcervo {

    // ------------------------------------------------------------
    // ENCAPSULAMENTO — todos os atributos sao private
    // ------------------------------------------------------------
    // ✅ BOA PRATICA: private, nao protected. E tentador abrir os atributos
    // para as subclasses, mas atributo protected acopla os filhos a
    // REPRESENTACAO INTERNA do pai. Se um dia "ano" virar LocalDate, todas as
    // subclasses quebram. Com private + getter protegido, so o pai muda.
    // ------------------------------------------------------------

    /** Identificador unico. {@code final}: definido no construtor e nunca mais muda. */
    private final String codigo;

    private final String titulo;
    private final int ano;

    /**
     * Estado de disponibilidade.
     *
     * ⚠️ ARMADILHA: NAO existe {@code setDisponivel()} publico nesta classe,
     * e isso e intencional. Disponibilidade nao e algo que alguem "define" de
     * fora — e CONSEQUENCIA de emprestar e devolver. Um setter publico aqui
     * permitiria marcar como disponivel um item que esta na mao de um usuario,
     * corrompendo o estado do sistema inteiro.
     */
    private boolean disponivel;

    /**
     * Contador compartilhado por TODAS as instancias.
     *
     * <p>{@code static} significa "pertence a classe, nao ao objeto". Existe
     * uma unica copia desta variavel na memoria, independentemente de quantos
     * itens forem criados.</p>
     */
    private static int totalItensCriados = 0;

    /**
     * Construtor da classe abstrata.
     *
     * <p>Uma classe abstrata TEM construtor, mesmo nao podendo ser instanciada
     * diretamente. Ele e executado quando uma subclasse chama {@code super(...)},
     * e serve para inicializar a parte comum do objeto.</p>
     *
     * <p><b>Primeira barreira do encapsulamento:</b> o construtor garante que o
     * objeto NASCA valido. De nada adianta validar nos metodos se o objeto ja
     * pode ser criado quebrado.</p>
     *
     * @throws IllegalArgumentException se algum dado violar as invariantes
     */
    protected ItemAcervo(String codigo, String titulo, int ano) {

        // Invariante 1: codigo obrigatorio
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo do item e obrigatorio.");
        }

        // Invariante 2: titulo obrigatorio
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo do item e obrigatorio.");
        }

        // Invariante 3: ano dentro de faixa plausivel
        if (ano < Config.ANO_MINIMO_PUBLICACAO || ano > Config.ANO_MAXIMO_PUBLICACAO) {
            throw new IllegalArgumentException(
                    "Ano invalido: " + ano + ". Esperado entre "
                            + Config.ANO_MINIMO_PUBLICACAO + " e " + Config.ANO_MAXIMO_PUBLICACAO + ".");
        }

        this.codigo = codigo;
        this.titulo = titulo;
        this.ano = ano;
        this.disponivel = true;   // todo item nasce disponivel

        totalItensCriados++;
    }

    // ------------------------------------------------------------
    // METODOS ABSTRATOS — o contrato imposto as subclasses
    // ------------------------------------------------------------
    // Metodo abstrato nao tem corpo. Toda subclasse concreta e OBRIGADA
    // a implementa-lo, sob pena de nao compilar. O compilador passa a
    // garantir o que antes seria apenas convencao.
    // ------------------------------------------------------------

    /** Rotulo curto da categoria, usado nos relatorios. Ex.: "LIVRO", "DVD". */
    public abstract String getCategoria();

    /** Informacao especifica do tipo. Ex.: autor do livro, duracao do DVD. */
    public abstract String getDescricaoDetalhada();

    // ------------------------------------------------------------
    // METODOS CONCRETOS — herdados por todos, escritos uma unica vez
    // ------------------------------------------------------------

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    /**
     * Marca o item como emprestado.
     *
     * <p>{@code protected}: visivel apenas para subclasses e para o proprio
     * pacote. E o meio-termo exato de que precisamos — {@code ItemEmprestavel}
     * precisa alterar a disponibilidade, mas o mundo la fora nao pode.</p>
     */
    protected void marcarComoEmprestado() {
        this.disponivel = false;
    }

    /** Marca o item como devolvido. Mesma justificativa de visibilidade acima. */
    protected void marcarComoDisponivel() {
        this.disponivel = true;
    }

    /** Total de itens criados desde o inicio do programa. */
    public static int getTotalItensCriados() {
        return totalItensCriados;
    }

    // ------------------------------------------------------------
    // SOBRESCRITA DOS METODOS HERDADOS DE Object
    // ------------------------------------------------------------
    // Toda classe em Java herda de Object, mesmo sem escrever "extends".
    // As implementacoes padrao sao pobres: toString() devolve o endereco de
    // memoria e equals() compara referencias (igual ao ==).
    // ------------------------------------------------------------

    /**
     * Igualdade de negocio: dois itens sao o mesmo item se tem o mesmo codigo.
     *
     * <p>Titulo e ano nao entram na comparacao — dois exemplares distintos
     * do mesmo livro sao objetos DIFERENTES, com codigos diferentes.</p>
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;                       // mesma referencia: atalho
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;                      // null ou tipo diferente
        }
        ItemAcervo outro = (ItemAcervo) obj;    // downcast seguro apos a checagem
        return codigo.equals(outro.codigo);
    }

    /**
     * ⚠️ ARMADILHA: sobrescrever {@code equals()} e esquecer {@code hashCode()}
     * e um dos bugs mais dificeis de rastrear em Java. Objetos iguais PRECISAM
     * ter o mesmo hash; se nao tiverem, eles se perdem dentro de HashMap e
     * HashSet — voce guarda um objeto e nao consegue mais encontra-lo.
     *
     * <p>Regra: sobrescreveu um, sobrescreva o outro, usando os MESMOS campos.</p>
     */
    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    /**
     * Representacao textual comum a todos os itens.
     *
     * <p>Note que este metodo concreto chama {@code getCategoria()}, que e
     * ABSTRATO — nao existe implementacao aqui. Funciona porque, em tempo de
     * execucao, o objeto real sempre e de uma subclasse concreta. Esse padrao
     * (a classe base define o esqueleto e delega os passos variaveis aos
     * filhos) e conhecido como <i>Template Method</i>.</p>
     */
    @Override
    public String toString() {
        return String.format("%-8s | %-32s | %d", getCategoria(), titulo, ano);
    }
}