package aula05_classes;

/*
 * ============================================================
 * AULA 05 - CLASSES E OBJETOS (PASSAGEM DE PARAMETROS)
 * ============================================================
 * Esta classe demonstra um ponto fundamental do Java: parametros
 * sao sempre passados por VALOR.
 *
 * Para tipos primitivos, copia-se o valor literal.
 * Para arrays/objetos, copia-se o valor da referencia.
 * ============================================================
 */
/**
 * Demonstra como a passagem por valor funciona em cenarios comuns.
 */
public class PassagemDeParametros {

    /**
     * Tenta alterar um primitivo recebido por parametro.
     *
     * @param idade valor primitivo copiado
     */
    public void tentarAlterarPrimitivo(int idade) {
        idade = 99;
    }

    /**
     * Altera o conteudo do array recebido.
     *
     * @param notas referencia copiada para o mesmo array original
     */
    public void alterarConteudoArray(int[] notas) {
        if (notas != null && notas.length > 0) {
            notas[0] = 10;
        }
    }

    /**
     * Reatribui a referencia local para outro array.
     *
     * @param notas copia da referencia original
     */
    public void reatribuirArrayLocalmente(int[] notas) {
        notas = new int[] {0, 0, 0};
        notas[0] = 50;
    }
}
