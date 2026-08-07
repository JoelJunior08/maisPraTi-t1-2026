package aula06_encapsulamento;

/*
 * ============================================================
 * AULA 06 - ENCAPSULAMENTO (ALUNO BOM)
 * ============================================================
 * Encapsulamento nao e "getter + setter para tudo". O ponto
 * central e proteger invariantes: estado interno so muda por
 * operacoes que validam regras de negocio.
 * ============================================================
 */
/**
 * Modelo de aluno com invariantes protegidas por construtor e metodos.
 */
public class AlunoBom {

    private final String nome;
    private int idade;
    private double notaFinal;

    /**
     * Cria aluno validando estado inicial.
     *
     * @param nome nome do aluno
     * @param idade idade do aluno
     * @param notaFinal nota final entre 0 e 10
     */
    public AlunoBom(String nome, int idade, double notaFinal) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }
        if (idade < 0) {
            throw new IllegalArgumentException("Idade nao pode ser negativa.");
        }
        validarNota(notaFinal);

        this.nome = nome;
        this.idade = idade;
        this.notaFinal = notaFinal;
    }

    /**
     * Retorna o nome do aluno.
     *
     * @return nome do aluno
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a idade.
     *
     * @return idade atual
     */
    public int getIdade() {
        return idade;
    }

    /**
     * Retorna a nota final.
     *
     * @return nota final
     */
    public double getNotaFinal() {
        return notaFinal;
    }

    /**
     * Atualiza nota final apenas com faixa valida.
     *
     * @param novaNota nova nota entre 0 e 10
     */
    public void atualizarNotaFinal(double novaNota) {
        validarNota(novaNota);
        this.notaFinal = novaNota;
    }

    /**
     * Incrementa idade em 1 ano.
     */
    public void fazerAniversario() {
        this.idade++;
    }

    private void validarNota(double nota) {
        if (nota < 0.0 || nota > 10.0) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10.");
        }
    }
}
