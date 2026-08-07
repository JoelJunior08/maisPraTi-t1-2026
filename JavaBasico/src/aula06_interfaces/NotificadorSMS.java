package aula06_interfaces;

/**
 * Implementacao de notificador via SMS.
 */
public class NotificadorSMS implements Notificador {

    private static final int LIMITE_SMS = 160;

    /**
     * Envia SMS truncando para o limite tradicional de 160 caracteres.
     *
     * @param destino destinatario
     * @param mensagem mensagem enviada
     */
    @Override
    public void enviar(String destino, String mensagem) {
        String mensagemSegura = mensagem == null ? "" : mensagem;
        String mensagemTruncada = mensagemSegura.length() > LIMITE_SMS
            ? mensagemSegura.substring(0, LIMITE_SMS)
            : mensagemSegura;
        System.out.println("[SMS] Para " + destino + ": " + mensagemTruncada);
    }
}
