package aula06_interfaces;

/**
 * Interface funcional para validacoes simples.
 */
@FunctionalInterface
public interface Validador {

    /**
     * Valida o valor informado.
     *
     * @param valor valor a validar
     * @return true quando valido
     */
    boolean validar(String valor);
}
