package biblioteca.modelo;

/*
 * ============================================================
 * PROFESSOR - subclasse concreta de Usuario
 * ============================================================
 */

/**
 * Docente da instituicao. Limite 10 itens, 50% de desconto,
 * com acesso a material restrito.
 */
public class Professor extends Usuario {

    private final String departamento;
    private final String titulacao;

    public Professor(String matricula, String nome, String email,
                     String departamento, String titulacao) {
        super(matricula, nome, email);
        this.departamento = departamento;
        this.titulacao = titulacao;
    }

    @Override
    public int getLimiteItens() {
        return 10;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.50;     // 50%
    }

    @Override
    public String getCategoria() {
        return "Professor";
    }

    @Override
    public boolean podeRetirarMaterialRestrito() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + titulacao + ", " + departamento;
    }

    public String getDepartamento() {
        return departamento;
    }
}
