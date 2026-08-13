package exericico_biblioteca.modelo;

public abstract class Usuario {
    private final String matricula;
    private final String nome;
    private final String email;
    private final ItemAcervo[] itensEmprestados;
    private int quantidadeEmprestada;
    private double multaAcumulada;

    private static int totalUsuariosCriados = 0;

    protected Usuario(String matricula, String nome, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.itensEmprestados = new ItemAcervo[10];
        this.quantidadeEmprestada = 0;
        this.multaAcumulada = 0;
        totalUsuariosCriados++;
    }
}
