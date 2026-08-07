package aula06_interfaces;

/**
 * Pagamento em boleto com taxa fixa maior.
 */
public final class Boleto implements FormaPagamento {

    @Override
    public double calcularTaxa(double valor) {
        return 2.99;
    }
}
