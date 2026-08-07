package aula06_encapsulamento;

/*
 * ============================================================
 * AULA 06 - ENCAPSULAMENTO (ALUNO RUIM)
 * ============================================================
 * Esta classe representa o anti-exemplo: dados expostos como
 * public permitem qualquer alteracao externa, inclusive valores
 * invalidos para a regra de negocio.
 * ============================================================
 */
/**
 * Anti-exemplo de modelagem sem encapsulamento.
 */
public class AlunoRuim {

    public String nome;
    public int idade;
    public double notaFinal;
}
