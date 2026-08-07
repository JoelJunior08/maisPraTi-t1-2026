package aula05_classes;

/*
 * ============================================================
 * AULA 05 - CLASSES E OBJETOS (CALCULADORA ESTATICA)
 * ============================================================
 * Esta classe mostra metodos static: operacoes sem estado, em que
 * o resultado depende apenas dos parametros recebidos.
 *
 * ✅ BOA PRATICA: quando nao ha estado para preservar, metodos
 * static podem simplificar o uso (sem new), como ocorre em Math.
 * ============================================================
 */
/**
 * Fornece operacoes matematicas simples sem manter estado interno.
 */
public final class Calculadora {

    private Calculadora() {
        // Evita instanciacao de classe utilitaria.
    }

    /**
     * Soma dois valores.
     *
     * @param primeiro valor 1
     * @param segundo  valor 2
     * @return soma dos valores
     */
    public static double somar(double primeiro, double segundo) {
        return primeiro + segundo;
    }

    /**
     * Subtrai dois valores.
     *
     * @param primeiro valor 1
     * @param segundo  valor 2
     * @return diferenca
     */
    public static double subtrair(double primeiro, double segundo) {
        return primeiro - segundo;
    }

    /**
     * Multiplica dois valores.
     *
     * @param primeiro valor 1
     * @param segundo  valor 2
     * @return produto
     */
    public static double multiplicar(double primeiro, double segundo) {
        return primeiro * segundo;
    }

    /**
     * Divide dois valores.
     *
     * @param dividendo valor que sera dividido
     * @param divisor   valor que divide
     * @return resultado da divisao
     * @throws ArithmeticException quando divisor e zero
     */
    public static double dividir(double dividendo, double divisor) {
        if (divisor == 0.0) {
            throw new ArithmeticException("Divisao por zero nao e permitida.");
        }
        return dividendo / divisor;
    }
}
