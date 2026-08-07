package aula06_encapsulamento;

/*
 * ============================================================
 * AULA 06 - ENCAPSULAMENTO (TEMPERATURA IMUTAVEL)
 * ============================================================
 * Imutabilidade e uma forma forte de encapsulamento: depois da
 * criacao, o estado nao muda. Qualquer "alteracao" retorna um
 * novo objeto.
 * ============================================================
 */
/**
 * Representa temperatura em Celsius de forma imutavel.
 */
public final class Temperatura {

    private final double celsius;

    /**
     * Cria temperatura em Celsius.
     *
     * @param celsius valor em graus Celsius
     */
    public Temperatura(double celsius) {
        this.celsius = celsius;
    }

    /**
     * Retorna a temperatura atual.
     *
     * @return valor em Celsius
     */
    public double getCelsius() {
        return celsius;
    }

    /**
     * Retorna nova instancia com acrescimo informado.
     *
     * @param delta variacao em Celsius
     * @return nova temperatura
     */
    public Temperatura comAcrescimo(double delta) {
        return new Temperatura(this.celsius + delta);
    }
}
