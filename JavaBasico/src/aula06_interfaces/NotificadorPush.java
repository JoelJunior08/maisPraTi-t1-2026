package aula06_interfaces;

/**
 * Implementacao de notificador por push.
 */
public class NotificadorPush implements Notificador {

    /**
     * Envia mensagem push.
     *
     * @param destino destinatario
     * @param mensagem mensagem enviada
     */
    @Override
    public void enviar(String destino, String mensagem) {
        System.out.println("[PUSH] Para " + destino + ": " + mensagem);
    }
}
