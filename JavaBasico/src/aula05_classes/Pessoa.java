package aula05_classes;

/*
 * ============================================================
 * AULA 05 - CLASSES E OBJETOS (PESSOA)
 * ============================================================
 * Esta classe e a versao minima para introduzir classe como
 * "molde" e objeto como "instancia" desse molde.
 *
 * Ao estudar, observe que os atributos representam estado
 * (dados) e os metodos representam comportamento (acoes).
 * ============================================================
 */
/**
 * Representa uma pessoa de forma simples, com nome e idade.
 */
public class Pessoa {

    private String nome;
    private int idade;

    /**
     * Cria uma pessoa com nome e idade.
     *
     * @param nome  nome da pessoa
     * @param idade idade da pessoa
     */
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    /**
     * Gera uma apresentacao curta da pessoa.
     *
     * @return mensagem de apresentacao
     */
    public String apresentar() {
        return "Oi! Eu sou " + this.nome + " e tenho " + this.idade + " anos.";
    }
}
