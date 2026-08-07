package aula06_interfaces;

/*
 * ============================================================
 * AULA 06 - INTERFACES (PEDIDO)
 * ============================================================
 * Esta classe recebe um Notificador por construtor: isso e
 * injecao de dependencia. O codigo depende da ABSTRACAO
 * (interface), nao de uma implementacao concreta.
 *
 * ✅ BOA PRATICA: programe para a interface, nao para implementacao.
 * ============================================================
 */
/**
 * Representa pedido que confirma status por um notificador externo.
 */
public class Pedido {

    private final int numero;
    private final Notificador notificador;

    /**
     * Cria pedido com numero e notificador.
     *
     * @param numero identificador do pedido
     * @param notificador estrategia de notificacao
     */
    public Pedido(int numero, Notificador notificador) {
        if (notificador == null) {
            throw new IllegalArgumentException("Notificador e obrigatorio.");
        }
        this.numero = numero;
        this.notificador = notificador;
    }

    /**
     * Fecha pedido e notifica cliente.
     *
     * @param destino canal de destino
     */
    public void fechar(String destino) {
        notificador.enviar(destino, "Pedido #" + numero + " confirmado e em preparacao.");
    }
}
