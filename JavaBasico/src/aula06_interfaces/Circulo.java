package aula06_interfaces;

/**
 * Implementacao de forma para circulo.
 */
public class Circulo implements Forma {

    private final double raio;

    /**
     * Cria circulo com raio informado.
     *
     * @param raio raio do circulo
     */
    public Circulo(double raio) {
        if (raio <= 0) {
            throw new IllegalArgumentException("Raio deve ser positivo.");
        }
        this.raio = raio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * raio;
    }
}
