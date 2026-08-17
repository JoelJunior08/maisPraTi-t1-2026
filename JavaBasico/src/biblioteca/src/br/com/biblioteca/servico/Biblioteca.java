package br.com.biblioteca.servico;

import br.com.biblioteca.contrato.Config;
import br.com.biblioteca.contrato.Emprestavel;
import br.com.biblioteca.contrato.Reservavel;
import br.com.biblioteca.modelo.ItemAcervo;
import br.com.biblioteca.modelo.ObraReferencia;
import br.com.biblioteca.modelo.Usuario;

/*
 * ============================================================
 * BIBLIOTECA - a classe onde o POLIMORFISMO aparece em acao
 * ============================================================
 * Observe, em cada laco deste arquivo, que NENHUM deles pergunta
 * "que tipo de item e este?" para decidir prazo, multa ou limite.
 * Eles apenas chamam o metodo — e cada objeto responde a seu modo.
 * ============================================================
 */

/**
 * Servico que orquestra acervo, usuarios e operacoes.
 *
 * <p>⚠️ LIMITACAO DELIBERADA: este sistema usa ARRAYS DE TAMANHO FIXO,
 * porque coleções ainda nao foram estudadas. Isso obriga a carregar um
 * contador manual ({@code totalItens}), a deslocar elementos na remocao e a
 * conviver com um teto rigido de {@code Config.CAPACIDADE_ACERVO}.</p>
 *
 * <p>Sinta o incomodo — ele e proposital. Na aula de coleções, {@code List}
 * e {@code Map} eliminam tudo isso, e o motivo de existirem ficara obvio.</p>
 */
public class Biblioteca {

    private final String nome;

    // Array do TIPO ABSTRATO: cabe qualquer subclasse de ItemAcervo.
    // E isso que torna possivel percorrer tudo em um unico laco.
    private final ItemAcervo[] acervo;
    private int totalItens;

    private final Usuario[] usuarios;
    private int totalUsuarios;

    public Biblioteca(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da biblioteca e obrigatorio.");
        }
        this.nome = nome;
        this.acervo = new ItemAcervo[Config.CAPACIDADE_ACERVO];
        this.usuarios = new Usuario[Config.CAPACIDADE_USUARIOS];
        this.totalItens = 0;
        this.totalUsuarios = 0;
    }

    // ============================================================
    // CADASTROS
    // ============================================================

    /**
     * Cadastra qualquer item do acervo.
     *
     * <p>O parametro e do tipo abstrato {@code ItemAcervo}. Um unico metodo
     * aceita livro, revista, DVD, tese, obra de referencia e audiolivro — e
     * aceitara tambem qualquer categoria criada no futuro, sem alteracao.
     * Isso e UPCASTING: a subclasse vira o tipo do pai automaticamente.</p>
     */
    public void cadastrarItem(ItemAcervo item) {
        if (item == null) {
            throw new IllegalArgumentException("Item nao pode ser nulo.");
        }
        if (totalItens >= acervo.length) {
            throw new IllegalStateException("Acervo lotado (limite do array).");
        }
        if (buscarItemPorCodigo(item.getCodigo()) != null) {
            throw new IllegalStateException("Ja existe item com o codigo " + item.getCodigo());
        }
        acervo[totalItens] = item;
        totalItens++;
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        }
        if (totalUsuarios >= usuarios.length) {
            throw new IllegalStateException("Limite de usuarios atingido.");
        }
        usuarios[totalUsuarios] = usuario;
        totalUsuarios++;
    }

    // ============================================================
    // CONSULTAS
    // ============================================================

    /** Busca linear pelo codigo. Devolve {@code null} se nao encontrar. */
    public ItemAcervo buscarItemPorCodigo(String codigo) {
        for (int i = 0; i < totalItens; i++) {
            if (acervo[i].getCodigo().equals(codigo)) {   // equals, nunca ==
                return acervo[i];
            }
        }
        return null;
    }

    // ============================================================
    // OPERACOES
    // ============================================================

    /**
     * Tenta emprestar um item a um usuario.
     *
     * <p><b>Aqui esta o unico {@code instanceof} legitimo do sistema.</b>
     * Ele nao decide COMPORTAMENTO por tipo — ele verifica se o item cumpre
     * um CONTRATO. Sao coisas diferentes:</p>
     *
     * <ul>
     *   <li>ruim: {@code if (item instanceof Livro) prazo = 14;} — decide
     *       comportamento por tipo, e precisa crescer a cada nova categoria;</li>
     *   <li>legitimo: {@code if (item instanceof Emprestavel e)} — verifica
     *       capacidade, e continua funcionando para categorias futuras.</li>
     * </ul>
     *
     * <p>A sintaxe {@code instanceof Emprestavel emprestavel} e o
     * <i>pattern matching</i> do Java 16+: testa e ja declara a variavel
     * convertida, dispensando o cast manual e o risco de
     * {@code ClassCastException}.</p>
     */
    public boolean emprestar(String codigoItem, Usuario usuario) {

        ItemAcervo item = buscarItemPorCodigo(codigoItem);

        if (item == null) {
            System.out.println("   [X] Item nao encontrado: " + codigoItem);
            return false;
        }

        if (item instanceof Emprestavel emprestavel) {
            return emprestavel.emprestar(usuario);
        }

        // Chegou aqui: o item existe, mas nao cumpre o contrato Emprestavel.
        System.out.printf("   [X] %-22s tentou \"%s\" - obra de consulta local%n",
                usuario.getNome(), item.getTitulo());
        return false;
    }

    /**
     * Processa a devolucao de um item, com calculo de multa.
     *
     * <p>Repare na cadeia polimorfica de tres passos, sem um unico
     * {@code if} de tipo:</p>
     *
     * <ol>
     *   <li>{@code emprestavel.calcularMulta(dias)} — metodo {@code default}
     *       da interface, que usa a multa/dia de cada categoria;</li>
     *   <li>{@code usuario.aplicarDesconto(bruta)} — metodo concreto do pai,
     *       que usa o percentual de cada tipo de usuario;</li>
     *   <li>{@code usuario.acumularMulta(liquida)} — regra unica para todos.</li>
     * </ol>
     */
    public double processarDevolucao(String codigoItem, Usuario usuario, int diasAtraso) {

        ItemAcervo item = buscarItemPorCodigo(codigoItem);

        if (item == null || !(item instanceof Emprestavel emprestavel)) {
            return 0.0;
        }

        // devolver() ja atualiza os dois lados: o item volta ao acervo e o
        // usuario deixa de conta-lo. A Biblioteca nao precisa saber disso.
        if (!emprestavel.devolver()) {
            return 0.0;
        }

        double multaBruta = emprestavel.calcularMulta(diasAtraso);
        double multaLiquida = usuario.aplicarDesconto(multaBruta);

        if (multaLiquida > 0) {
            usuario.acumularMulta(multaLiquida);
        }

        return multaLiquida;
    }

    // ============================================================
    // RELATORIOS — todos polimorficos
    // ============================================================

    /** Cabecalho padrao dos relatorios. */
    private void imprimirSeparador() {
        System.out.println("-".repeat(Config.LARGURA_RELATORIO));
    }

    /**
     * Lista o acervo inteiro.
     *
     * <p><b>UM laco, seis comportamentos.</b> {@code getCategoria()} e
     * {@code getDescricaoDetalhada()} sao abstratos em {@code ItemAcervo};
     * cada objeto responde com sua propria implementacao, decidida em tempo
     * de execucao (vinculacao dinamica).</p>
     */
    public void listarAcervo() {
        System.out.println("[1] ACERVO CADASTRADO (" + totalItens + " itens)");
        imprimirSeparador();

        for (int i = 0; i < totalItens; i++) {
            ItemAcervo item = acervo[i];

            String prazoEMulta;
            // instanceof verificando CONTRATO, nao decidindo comportamento
            if (item instanceof Emprestavel e) {
                prazoEMulta = String.format("%2d dias | R$ %.2f/dia",
                        e.getPrazoEmprestimoDias(), e.getMultaPorDia());
            } else {
                prazoEMulta = "CONSULTA LOCAL";
            }

            System.out.printf("%-8s | %-31s | %s%n",
                    item.getCategoria(), item.getTitulo(), prazoEMulta);
        }
        System.out.println();
    }

    /**
     * Soma as multas de todos os usuarios.
     *
     * <p>Laco sobre o tipo abstrato {@code Usuario}, somando um valor que
     * cada subclasse calcula a seu modo.</p>
     */
    public double calcularMultasTotais() {
        double total = 0;
        for (int i = 0; i < totalUsuarios; i++) {
            total += usuarios[i].getMultaAcumulada();
        }
        return total;
    }

    /** Relatorio de usuarios com debito. */
    public void listarSituacaoUsuarios() {
        System.out.println("[5] SITUACAO DOS USUARIOS");
        imprimirSeparador();

        for (int i = 0; i < totalUsuarios; i++) {
            // toString() polimorfico: AlunoPosGraduacao e Professor acrescentam
            // informacao com super.toString(); os demais usam a versao do pai.
            System.out.println("   " + usuarios[i]);
        }

        imprimirSeparador();
        System.out.printf("   TOTAL EM MULTAS: R$ %.2f%n%n", calcularMultasTotais());
    }

    /** Lista os itens com reserva ativa. */
    public void listarReservas() {
        boolean encontrou = false;
        for (int i = 0; i < totalItens; i++) {
            if (acervo[i] instanceof Reservavel r && r.temReserva()) {
                System.out.printf("   %-32s reservado por %s%n",
                        acervo[i].getTitulo(), r.getNomeReservante());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("   (nenhuma reserva ativa)");
        }
        System.out.println();
    }

    /** Envia aviso a todos os usuarios com debito, via interface Notificavel. */
    public void notificarDevedores() {
        System.out.println("[7] NOTIFICACOES AUTOMATICAS");
        imprimirSeparador();

        for (int i = 0; i < totalUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u.getMultaAcumulada() > 0) {
                // notificar() e notificarUrgente() sao metodos DEFAULT da
                // interface Notificavel — nenhuma subclasse os implementou.
                if (u.getMultaAcumulada() >= 10.0) {
                    u.notificarUrgente(String.format(
                            "Debito de R$ %.2f pendente.", u.getMultaAcumulada()));
                } else {
                    u.notificar(String.format(
                            "Voce possui R$ %.2f em multas.", u.getMultaAcumulada()));
                }
            }
        }
        System.out.println();
    }

    /** Contagem de obras de consulta local. */
    public int contarObrasDeReferencia() {
        int total = 0;
        for (int i = 0; i < totalItens; i++) {
            if (acervo[i] instanceof ObraReferencia) {
                total++;
            }
        }
        return total;
    }

    public String getNome() {
        return nome;
    }

    public int getTotalItens() {
        return totalItens;
    }

    /**
     * Devolve o acervo — em COPIA DEFENSIVA.
     *
     * <p>Sem {@code Arrays.copyOf}, quem chamasse este metodo poderia
     * substituir itens do acervo real por {@code null}. O array e privado,
     * mas o objeto para o qual ele aponta seria compartilhado.</p>
     */
    public ItemAcervo[] getAcervo() {
        return java.util.Arrays.copyOf(acervo, totalItens);
    }
}
