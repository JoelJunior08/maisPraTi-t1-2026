package aula06_interfaces;

/**
 * Implementacao de notificador por e-mail.
 */
public class NotificadorEmail implements Notificador {

    /**
     * Envia mensagem por e-mail.
     *
     * @param destino destinatario
     * @param mensagem mensagem enviada
     */
    @Override
    public void enviar(String destino, String mensagem) {
        System.out.println("[EMAIL] Para " + destino + ": " + mensagem);
    }

    /**
     * E-mail normalmente suporta anexos.
     *
     * @return true
     */
    @Override
    public boolean suportaAnexo() {
        return true;
    }
}
