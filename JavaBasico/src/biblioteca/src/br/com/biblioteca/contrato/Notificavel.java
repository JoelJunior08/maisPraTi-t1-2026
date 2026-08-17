package br.com.biblioteca.contrato;

/*
 * ============================================================
 * NOTIFICAVEL - contrato de quem pode receber avisos
 * ============================================================
 */

/**
 * Contrato de qualquer entidade capaz de receber notificacoes.
 *
 * <p>Repare que esta interface nao sabe NADA sobre biblioteca. Ela poderia
 * ser reaproveitada em qualquer sistema. Esse e o sinal de uma boa interface:
 * ela descreve uma CAPACIDADE, nao um pedaco do seu dominio.</p>
 */
public interface Notificavel {

    /** Identificacao de quem recebe o aviso (nome, e-mail, telefone...). */
    String getIdentificacaoContato();

    /**
     * Envia um aviso comum.
     *
     * <p>Metodo {@code default}: quem implementa a interface ja ganha este
     * comportamento pronto e so precisa fornecer
     * {@code getIdentificacaoContato()}.</p>
     */
    default void notificar(String mensagem) {
        System.out.println("      [AVISO -> " + getIdentificacaoContato() + "] " + mensagem);
    }

    /**
     * Envia um aviso urgente.
     *
     * <p>Um metodo {@code default} pode chamar outro {@code default}.
     * Aqui reaproveitamos {@code notificar()} em vez de duplicar a
     * formatacao — mesma logica de {@code super.toString()} na heranca.</p>
     */
    default void notificarUrgente(String mensagem) {
        notificar("*** URGENTE *** " + mensagem);
    }
}
