package aula05_classes;

/*
 * ============================================================
 * AULA 05 - CLASSES E OBJETOS (CALCULADORA COM MEMORIA)
 * ============================================================
 * Diferente da Calculadora estatica, aqui existe ESTADO interno.
 * Cada objeto guarda seu proprio valor em memoria.
 *
 * Ao estudar, observe que esse estado e o que justifica criar
 * uma instancia com new: cada objeto pode evoluir de forma unica.
 * ============================================================
 */
/**
 * Representa uma calculadora que guarda resultado acumulado.
 */
public class CalculadoraComMemoria {

    private double memoria;

    /**
     * Cria calculadora com memoria iniciada em zero.
     */
    public CalculadoraComMemoria() {
        this.memoria = 0.0;
    }

    /**
     * Soma valor na memoria atual.
     *
     * @param valor valor para somar
     */
    public void somar(double valor) {
        this.memoria += valor;
    }

    /**
     * Subtrai valor da memoria atual.
     *
     * @param valor valor para subtrair
     */
    public void subtrair(double valor) {
        this.memoria -= valor;
    }

    /**
     * Retorna valor atual da memoria.
     *
     * @return valor armazenado
     */
    public double getMemoria() {
        return memoria;
    }

    /**
     * Limpa a memoria, voltando para zero.
     */
    public void limpar() {
        this.memoria = 0.0;
    }
}
