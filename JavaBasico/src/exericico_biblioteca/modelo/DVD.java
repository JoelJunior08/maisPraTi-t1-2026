package exericico_biblioteca.modelo;

/*
 * ============================================================
 * DVD - subclasse concreta
 * ============================================================
 */

/**
 * Midia audiovisual. Prazo 3 dias, multa R$ 2,00/dia, nao renovavel.
 *
 * <p>Prazo curto e multa alta refletem a alta rotatividade do item.
 * Note como uma regra de negocio inteira cabe em dois metodos de uma
 * linha — e como adicionar essa regra nao exigiu tocar em nenhuma outra
 * classe do sistema.</p>
 */
public class DVD extends ItemEmprestavel {

    private final int duracaoMinutos;
    private final String classificacaoIndicativa;

    public DVD(String codigo, String titulo, int ano, int duracaoMinutos,
               String classificacaoIndicativa) {
        super(codigo, titulo, ano);

        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Duracao deve ser positiva.");
        }

        this.duracaoMinutos = duracaoMinutos;
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 3;
    }

    @Override
    public double getMultaPorDia() {
        return 2.00;
    }

    @Override
    public String getCategoria() {
        return "DVD";
    }

    @Override
    public String getDescricaoDetalhada() {
        return duracaoMinutos + " min | Classificacao: " + classificacaoIndicativa;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }
}
