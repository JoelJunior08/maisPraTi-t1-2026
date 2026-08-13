package exericico_biblioteca.modelo;

import exericico_biblioteca.contrato.Reservavel;

public class Livro extends ItemEmprestavel implements Reservavel {
    private final String autor;
    private final String isbn;
    private int renovacoesUtilizadas;
    private String nomeReservante;

    private static final int MAXIMO_RENOVACOES = 2;

    public Livro(String codigo, String titulo, int ano, String autor, String isbn) {
        super(codigo, titulo, ano);
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor inválido");
        }

        this.autor = autor;
        this.isbn = isbn;
        this.renovacoesUtilizadas = 0;
        this.nomeReservante = null;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 14;
    }

    @Override
    public double getMultaPordia() {
        return 0.50;
    }

    @Override
    public boolean permiteRenovacao() {
        return renovacoesUtilizadas < MAXIMO_RENOVACOES;
    }

    @Override
    public void reservar(Usuario usuario) {
        if(usuario == null) {
            throw new IllegalArgumentException("Usuário inválido");
        }
        if(isDisponivel()) {
            this.nomeReservante = usuario.getNome();
        }
    }

    @Override
    public boolean temReserva() {
        return nomeReservante != null;
    }

    @Override
    public String getReservante() {
        return nomeReservante == null ? "-" : this.nomeReservante;
    }

    @Override
    public String getCategoria() {
        return "Livro";
    }

    @Override
    public String getDescricao() {
        return "Autor: " + this.autor + "| ISBN: " + this.isbn;
    }

    public boolean renovar() {
        if(!permiteRenovacao()) {
            return false;
        }

        renovacoesUtilizadas++;
        return true;
    }

    public String getAutor() {
        return this.autor;
    }

    public int getRenovacoesUtilizadas() {
        return this.renovacoesUtilizadas;
    }
}
