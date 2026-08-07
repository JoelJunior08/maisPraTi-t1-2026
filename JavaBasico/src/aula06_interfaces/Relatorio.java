package aula06_interfaces;

/**
 * Exemplo de classe que implementa multiplas interfaces.
 */
public class Relatorio implements Imprimivel, Exportavel {

    private final String titulo;

    /**
     * Cria relatorio com titulo.
     *
     * @param titulo titulo do relatorio
     */
    public Relatorio(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String imprimir() {
        return "Imprimindo relatorio: " + titulo;
    }

    @Override
    public String exportar(String formato) {
        return "Exportando '" + titulo + "' em " + formato;
    }
}
