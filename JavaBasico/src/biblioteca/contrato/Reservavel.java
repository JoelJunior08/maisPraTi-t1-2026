package biblioteca.contrato;

import biblioteca.modelo.Usuario;

/*
 * ============================================================
 * RESERVAVEL - segundo contrato, para demonstrar multiplas interfaces
 * ============================================================
 */

/**
 * Contrato de itens que aceitam fila de reserva.
 *
 * <p>Nem todo item emprestavel e reservavel, e vice-versa em tese.
 * Sao capacidades INDEPENDENTES — e e exatamente por isso que sao
 * interfaces separadas, e nao metodos de uma classe base.</p>
 *
 * <p>No sistema, {@code Livro} implementa {@code Emprestavel} (via
 * {@code ItemEmprestavel}) E {@code Reservavel} ao mesmo tempo. Uma classe
 * estende UMA classe, mas implementa QUANTAS interfaces quiser — foi assim
 * que o Java resolveu o problema da heranca multipla.</p>
 */
public interface Reservavel {

    /** Registra a reserva do item para o usuario informado. */
    void reservar(Usuario usuario);

    /** Informa se ja existe alguem na fila de reserva. */
    boolean temReserva();

    /** Nome de quem reservou, ou "-" se nao houver reserva. */
    String getNomeReservante();
}
