package exericico_biblioteca.modelo;

import exericico_biblioteca.contrato.Config;

public abstract class ItemAcervo {
    private final String codigo;
    private final String titulo;
    private final int ano;
    private boolean disponivel;
    private static int totalItensCriados = 0;

    protected ItemAcervo(String codigo, String titulo, int ano) {
        if(codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Código inválido");
        }

        if(titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título inválido");
        }

        if(ano < Config.ANO_MINIMO_PUBLICACAO || ano > Config.ANO_MAXIMO_PUBLICACAO) {
            throw new IllegalArgumentException("Ano inválido");
        }

        this.codigo = codigo;
        this.titulo = titulo;
        this.ano = ano;
        this.disponivel = true;
        totalItensCriados++;
    }

    public abstract String getCategoria();
    public abstract String getDescricao();

    public String getCodigo() {
        return codigo;
    }
    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    protected void marcarComoEmprestado() {
        this.disponivel = false;
    }

    protected void marcarComoDisponivel() {
        this.disponivel = true;
    }

    public static int getTotalItensCriados() {
        return totalItensCriados;
    }
}