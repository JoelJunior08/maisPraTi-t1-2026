package aula06_interfaces;

/**
 * Implementacao de forma para retangulo.
 */
public class Retangulo implements Forma {

    private final double largura;
    private final double altura;

    /**
     * Cria retangulo com largura e altura.
     *
     * @param largura largura
     * @param altura altura
     */
    public Retangulo(double largura, double altura) {
        if (largura <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Largura e altura devem ser positivas.");
        }
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return largura * altura;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * (largura + altura);
    }
}
