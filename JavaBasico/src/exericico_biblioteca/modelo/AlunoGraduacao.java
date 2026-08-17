package exericico_biblioteca.modelo;

public class AlunoGraduacao extends Usuario {
    private final String curso;
    private final int periodo;

    public AlunoGraduacao(String matricula, String nome, String email,
                          String curso, int periodo) {
        super(matricula, nome, email);

        this.curso = curso;
        this.periodo = periodo;
    }
}
