package biblioteca.modelo;

/*
 * ============================================================
 * ALUNOGRADUACAO - subclasse concreta de Usuario
 * ============================================================
 */

/**
 * Aluno de graduacao. Limite 3 itens, sem desconto, sem acesso a teses.
 *
 * <p>Toda a logica de emprestar, devolver, acumular multa e listar itens ja
 * esta em {@code Usuario}. Esta classe responde apenas as quatro perguntas
 * que a diferenciam das irmas.</p>
 */
public class AlunoGraduacao extends Usuario {

    private final String curso;
    private final int periodo;

    public AlunoGraduacao(String matricula, String nome, String email,
                          String curso, int periodo) {
        super(matricula, nome, email);

        if (periodo < 1 || periodo > 12) {
            throw new IllegalArgumentException("Periodo deve estar entre 1 e 12.");
        }

        this.curso = curso;
        this.periodo = periodo;
    }

    @Override
    public int getLimiteItens() {
        return 3;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.0;      // sem desconto
    }

    @Override
    public String getCategoria() {
        return "Graduacao";
    }

    @Override
    public boolean podeRetirarMaterialRestrito() {
        return false;    // teses sao restritas a pos-graduacao
    }

    public String getCurso() {
        return curso;
    }

    public int getPeriodo() {
        return periodo;
    }
}
