package aula06_interfaces;

/**
 * Hierarquia selada para formas de pagamento.
 */
public sealed interface FormaPagamento permits Pix, CartaoCredito, Boleto {

    /**
     * Calcula taxa para determinado valor.
     *
     * @param valor valor base
     * @return taxa cobrada
     */
    double calcularTaxa(double valor);
}
