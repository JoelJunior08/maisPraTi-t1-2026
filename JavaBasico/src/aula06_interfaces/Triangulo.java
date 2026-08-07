package aula06_interfaces;

/**
 * Implementacao de forma para triangulo.
 */
public class Triangulo implements Forma {

    private final double ladoA;
    private final double ladoB;
    private final double ladoC;

    /**
     * Cria triangulo com tres lados validos.
     *
     * @param ladoA lado A
     * @param ladoB lado B
     * @param ladoC lado C
     */
    public Triangulo(double ladoA, double ladoB, double ladoC) {
        if (ladoA <= 0 || ladoB <= 0 || ladoC <= 0) {
            throw new IllegalArgumentException("Todos os lados devem ser positivos.");
        }
        if (ladoA + ladoB <= ladoC || ladoA + ladoC <= ladoB || ladoB + ladoC <= ladoA) {
            throw new IllegalArgumentException("Lados invalidos para triangulo.");
        }
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        this.ladoC = ladoC;
    }

    @Override
    public double calcularArea() {
        double semiperimetro = calcularPerimetro() / 2.0;
        return Math.sqrt(semiperimetro * (semiperimetro - ladoA) * (semiperimetro - ladoB) * (semiperimetro - ladoC));
    }

    @Override
    public double calcularPerimetro() {
        return ladoA + ladoB + ladoC;
    }
}
