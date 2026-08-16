package exericico_biblioteca.modelo;

/*
 * ============================================================
 * AUDIOLIVRO - a PROVA DO POLIMORFISMO
 * ============================================================
 */

/**
 * Audiolivro. Prazo 10 dias, multa R$ 0,75/dia.
 *
 * <p><b>ESTA CLASSE E O TESTE FINAL DO PROJETO.</b></p>
 *
 * <p>Ela foi adicionada DEPOIS de o sistema inteiro estar pronto e
 * funcionando. Verifique voce mesmo: nenhum laco de {@code Biblioteca}
 * precisou ser alterado, nenhum {@code if} novo foi acrescentado em lugar
 * algum, e {@code Main} continuou funcionando.</p>
 *
 * <p>Bastou:</p>
 * <ol>
 *   <li>estender {@code ItemEmprestavel};</li>
 *   <li>informar prazo, multa e categoria.</li>
 * </ol>
 *
 * <p>Compare com a alternativa sem polimorfismo, onde o prazo seria decidido
 * por uma cadeia de {@code if (item instanceof Livro) ... else if ...}.
 * Nesse desenho, cada nova categoria obrigaria a cacar TODAS as cadeias
 * espalhadas pelo sistema — e a esquecida vira bug em producao.</p>
 *
 * <p>Se voce precisou mudar algum laco para adicionar esta classe, o projeto
 * esta errado. Corrija antes de seguir.</p>
 */
public class AudioLivro extends ItemEmprestavel {

    private final String narrador;
    private final int duracaoMinutos;

    public AudioLivro(String codigo, String titulo, int ano, String narrador,
                      int duracaoMinutos) {
        super(codigo, titulo, ano);

        if (narrador == null || narrador.isBlank()) {
            throw new IllegalArgumentException("Narrador e obrigatorio.");
        }

        this.narrador = narrador;
        this.duracaoMinutos = duracaoMinutos;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 10;
    }

    @Override
    public double getMultaPorDia() {
        return 0.75;
    }

    @Override
    public String getCategoria() {
        return "AUDIO";
    }

    @Override
    public String getDescricaoDetalhada() {
        return "Narrado por " + narrador + " | " + duracaoMinutos + " min";
    }
}
