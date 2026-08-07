package aula06_interfaces;

/**
 * Pagamento em cartao com taxa percentual.
 */
public final class CartaoCredito implements FormaPagamento {

    @Override
    public double calcularTaxa(double valor) {
        return valor * 0.029;
    }
}
