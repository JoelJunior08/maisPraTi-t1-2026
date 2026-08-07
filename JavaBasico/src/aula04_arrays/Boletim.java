package aula04_arrays;

/*
 * ============================================================
 * AULA 04 - BOLETIM (MATRIZ DE NOTAS)
 * ============================================================
 * Esta classe demonstra matriz no contexto de boletim escolar:
 * - cada linha representa um aluno;
 * - cada coluna representa uma materia.
 *
 * Ao executar, observe que trocar a ordem dos lacos muda o
 * sentido do percurso: por linha (aluno) ou por coluna (materia).
 * ============================================================
 */
public class Boletim {

    public void imprimirBoletim(double[][] notas) {
        System.out.println("Tabela de notas (linhas=alunos, colunas=materias):"); // Tabela de notas (linhas=alunos, colunas=materias):

        for (int linha = 0; linha < notas.length; linha++) {
            System.out.printf("Aluno %d -> ", linha + 1);
            for (int coluna = 0; coluna < notas[linha].length; coluna++) {
                System.out.printf("M%d: %.1f  ", coluna + 1, notas[linha][coluna]);
            }
            System.out.println();
        }

        System.out.println("\nMedia por aluno (percorrendo por LINHA):"); // Media por aluno (percorrendo por LINHA):
        for (int linha = 0; linha < notas.length; linha++) {
            double somaLinha = 0.0;
            for (int coluna = 0; coluna < notas[linha].length; coluna++) {
                somaLinha += notas[linha][coluna];
            }
            double mediaAluno = somaLinha / notas[linha].length;
            System.out.printf("Aluno %d -> media: %.2f%n", linha + 1, mediaAluno);
        }

        System.out.println("\nMedia por materia (percorrendo por COLUNA):"); // Media por materia (percorrendo por COLUNA):
        // ⚠️ ARMADILHA: aqui a inversao da ordem dos lacos e ESSENCIAL.
        // Primeiro fixamos a coluna (materia) e depois percorremos as linhas (alunos).
        int totalMaterias = notas[0].length;
        for (int coluna = 0; coluna < totalMaterias; coluna++) {
            double somaColuna = 0.0;
            for (double[] notaAluno : notas) {
                somaColuna += notaAluno[coluna];
            }
            double mediaMateria = somaColuna / notas.length;
            System.out.printf("Materia %d -> media: %.2f%n", coluna + 1, mediaMateria);
        }
    }
}
