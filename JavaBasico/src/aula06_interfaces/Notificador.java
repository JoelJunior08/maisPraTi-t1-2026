package aula06_interfaces;

/*
 * ============================================================
 * AULA 06 - INTERFACES (NOTIFICADOR)
 * ============================================================
 * Interface define contrato comum: quem implementa se compromete
 * com o comportamento descrito, independentemente da tecnologia.
 * ============================================================
 */
/**
 * Contrato para envio de notificacoes.
 */
public interface Notificador {

    /**
     * Envia mensagem para um destino.
     *
     * @param destino destinatario da mensagem
     * @param mensagem conteudo da mensagem
     */
    void enviar(String destino, String mensagem);

    /**
     * Envio urgente reaproveitando o envio padrao.
     *
     * @param destino destinatario
     * @param mensagem mensagem base
     */
    default void enviarUrgente(String destino, String mensagem) {
        enviar(destino, "[URGENTE] " + mensagem);
    }

    /**
     * Informa se o canal suporta anexo.
     *
     * @return true quando suporta anexo
     */
    default boolean suportaAnexo() {
        return false;
    }
}
