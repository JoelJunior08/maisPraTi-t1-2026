package biblioteca.modelo;

/*
 * ============================================================
 * REVISTA - subclasse concreta
 * ============================================================
 */

/**
 * Revista ou periodico. Prazo 7 dias, multa R$ 1,00/dia, nao renovavel.
 *
 * <p>Repare em tudo que esta classe NAO precisou escrever: emprestar(),
 * devolver(), getCodigo(), getTitulo(), isDisponivel(), equals(),
 * hashCode(), toString() e permiteRenovacao(). Tudo herdado.</p>
 *
 * <p>Ela declara apenas o que a torna DIFERENTE das irmas. E esse o ganho
 * concreto da heranca: nao repetir o que ja foi resolvido.</p>
 */
public class Revista extends ItemEmprestavel {

    private final int edicao;
    private final String mesAnoPublicacao;

    public Revista(String codigo, String titulo, int ano, int edicao, String mesAnoPublicacao) {
        super(codigo, titulo, ano);

        if (edicao <= 0) {
            throw new IllegalArgumentException("Numero da edicao deve ser positivo.");
        }

        this.edicao = edicao;
        this.mesAnoPublicacao = mesAnoPublicacao;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 7;
    }

    @Override
    public double getMultaPorDia() {
        return 1.00;
    }

    // permiteRenovacao() NAO foi sobrescrito: herda o default false da
    // interface Emprestavel. Nao escrever codigo tambem e uma decisao.

    @Override
    public String getCategoria() {
        return "REVISTA";
    }

    @Override
    public String getDescricaoDetalhada() {
        return "Edicao " + edicao + " | " + mesAnoPublicacao;
    }

    public int getEdicao() {
        return edicao;
    }
}
