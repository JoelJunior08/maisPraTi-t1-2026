package aula06_interfaces;

/**
 * Contrato para algo que pode ser exportado em formato textual.
 */
public interface Exportavel {

    /**
     * Exporta conteudo em formato solicitado.
     *
     * @param formato formato de saida (ex.: PDF, CSV)
     * @return representacao exportada
     */
    String exportar(String formato);
}
