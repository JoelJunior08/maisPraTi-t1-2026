package biblioteca.modelo;

import biblioteca.contrato.Reservavel;

/*
 * ============================================================
 * LIVRO - subclasse concreta, unica que implementa DUAS interfaces
 * ============================================================
 */

/**
 * Livro do acervo. Prazo 14 dias, multa R$ 0,50/dia, renovavel.
 *
 * <p><b>Multiplas interfaces:</b> esta classe herda de
 * {@code ItemEmprestavel} (que ja implementa {@code Emprestavel}) e ainda
 * implementa {@code Reservavel}. Em Java a heranca e simples — uma classe
 * estende UMA classe — mas as interfaces sao ilimitadas.</p>
 *
 * <p>O mesmo objeto pode entao ser visto de formas diferentes conforme a
 * necessidade de quem o usa:</p>
 *
 * <pre>
 * Livro livro       = new Livro(...);
 * ItemAcervo item   = livro;   // visto como "algo do acervo"
 * Emprestavel emp   = livro;   // visto como "algo que se empresta"
 * Reservavel res    = livro;   // visto como "algo que se reserva"
 * </pre>
 */
public class Livro extends ItemEmprestavel implements Reservavel {

    private final String autor;
    private final String isbn;
    private int renovacoesUtilizadas;
    private String nomeReservante;

    /** Limite de renovacoes. private static final = constante interna da classe. */
    private static final int MAXIMO_RENOVACOES = 2;

    public Livro(String codigo, String titulo, int ano, String autor, String isbn) {
        super(codigo, titulo, ano);

        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor e obrigatorio para um livro.");
        }

        this.autor = autor;
        this.isbn = isbn;
        this.renovacoesUtilizadas = 0;
        this.nomeReservante = null;
    }

    // ------------------------------------------------------------
    // IMPLEMENTACAO DO CONTRATO Emprestavel
    // ------------------------------------------------------------
    // ✅ BOA PRATICA: @Override em TODA sobrescrita e implementacao.
    // Sem ela, um erro de digitacao (getPrazoEmprestimoDia, sem o "s")
    // criaria um metodo NOVO em vez de implementar o contrato — e o erro
    // so apareceria muito depois. Com @Override, o compilador acusa na hora.
    // ------------------------------------------------------------

    @Override
    public int getPrazoEmprestimoDias() {
        return 14;
    }

    @Override
    public double getMultaPorDia() {
        return 0.50;
    }

    /**
     * Sobrescreve o metodo {@code default} da interface.
     *
     * <p>{@code Emprestavel.permiteRenovacao()} devolve {@code false} por
     * padrao. Livro e o unico item do acervo que muda essa resposta — os
     * demais herdam o comportamento padrao sem escrever uma linha.</p>
     */
    @Override
    public boolean permiteRenovacao() {
        return renovacoesUtilizadas < MAXIMO_RENOVACOES;
    }

    // ------------------------------------------------------------
    // IMPLEMENTACAO DO CONTRATO Reservavel
    // ------------------------------------------------------------

    @Override
    public void reservar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        }
        if (isDisponivel()) {
            throw new IllegalStateException(
                    "Item disponivel nao precisa de reserva: " + getTitulo());
        }
        this.nomeReservante = usuario.getNome();
    }

    @Override
    public boolean temReserva() {
        return nomeReservante != null;
    }

    @Override
    public String getNomeReservante() {
        return nomeReservante == null ? "-" : nomeReservante;
    }

    // ------------------------------------------------------------
    // IMPLEMENTACAO DO CONTRATO DA CLASSE ABSTRATA ItemAcervo
    // ------------------------------------------------------------

    @Override
    public String getCategoria() {
        return "LIVRO";
    }

    @Override
    public String getDescricaoDetalhada() {
        return "Autor: " + autor + " | ISBN: " + isbn;
    }

    /** Registra o uso de uma renovacao. */
    public boolean renovar() {
        if (!permiteRenovacao()) {
            return false;
        }
        renovacoesUtilizadas++;
        return true;
    }

    public String getAutor() {
        return autor;
    }

    public int getRenovacoesUtilizadas() {
        return renovacoesUtilizadas;
    }
}
