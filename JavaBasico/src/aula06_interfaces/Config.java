package aula06_interfaces;

/*
 * ============================================================
 * AULA 06 - INTERFACES (CONFIG)
 * ============================================================
 * Em interfaces, campos sao implicitamente public static final.
 * Ou seja: sao constantes de classe por definicao.
 * ============================================================
 */
/**
 * Exemplo de interface com constantes.
 */
public interface Config {

    String AMBIENTE = "DEV";
    int TEMPO_LIMITE_MS = 5_000;
}
