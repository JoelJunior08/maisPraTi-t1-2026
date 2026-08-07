package aula06_encapsulamento;

import java.util.Arrays;

/*
 * ============================================================
 * AULA 06 - ENCAPSULAMENTO (TURMA)
 * ============================================================
 * Esta classe demonstra um problema comum: vazamento de estado
 * interno ao expor diretamente arrays mutaveis.
 *
 * Versao insegura: guarda/devolve a mesma referencia.
 * Versao segura: usa copia defensiva na entrada e na saida.
 * ============================================================
 */
/**
 * Guarda notas de uma turma em modo inseguro ou seguro.
 */
public class Turma {

    private final String nome;
    private final boolean usarCopiaDefensiva;
    private final double[] notas;

    private Turma(String nome, double[] notas, boolean usarCopiaDefensiva) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da turma e obrigatorio.");
        }
        if (notas == null || notas.length == 0) {
            throw new IllegalArgumentException("A turma precisa ter pelo menos uma nota.");
        }
        this.nome = nome;
        this.usarCopiaDefensiva = usarCopiaDefensiva;
        this.notas = usarCopiaDefensiva ? Arrays.copyOf(notas, notas.length) : notas;
    }

    /**
     * Cria turma em modo inseguro (com vazamento de referencia).
     *
     * @param nome nome da turma
     * @param notas notas da turma
     * @return turma insegura
     */
    public static Turma criarInsegura(String nome, double[] notas) {
        return new Turma(nome, notas, false);
    }

    /**
     * Cria turma em modo seguro (com copias defensivas).
     *
     * @param nome nome da turma
     * @param notas notas da turma
     * @return turma segura
     */
    public static Turma criarSegura(String nome, double[] notas) {
        return new Turma(nome, notas, true);
    }

    /**
     * Retorna o nome da turma.
     *
     * @return nome da turma
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna as notas da turma.
     *
     * @return referencia original (modo inseguro) ou copia (modo seguro)
     */
    public double[] getNotas() {
        return usarCopiaDefensiva ? Arrays.copyOf(notas, notas.length) : notas;
    }

    /**
     * Calcula media da turma sem expor estrutura interna.
     *
     * @return media das notas
     */
    public double getMedia() {
        double soma = 0.0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }
}
