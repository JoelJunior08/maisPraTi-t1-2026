package aula06_interfaces;

/**
 * Pagamento por Pix com taxa fixa baixa.
 */
public final class Pix implements FormaPagamento {

    @Override
    public double calcularTaxa(double valor) {
        return 0.50;
    }
}
