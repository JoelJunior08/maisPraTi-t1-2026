package aula05_classes;

/*
 * ============================================================
 * AULA 05 - CLASSES E OBJETOS (CONTADOR STATIC)
 * ============================================================
 * Esta classe demonstra membros static compartilhados por todas
 * as instancias da classe.
 *
 * Ao estudar, observe que totalInstancias pertence a CLASSE,
 * nao a um objeto especifico.
 * ============================================================
 */
/**
 * Conta quantas instancias foram criadas ao longo da execucao.
 */
public class Contador {

    private static int totalInstancias = 0;

    /**
     * Cria instancia e incrementa o contador global da classe.
     */
    public Contador() {
        totalInstancias++;
    }

    /**
     * Retorna o total de instancias criadas.
     *
     * @return total de objetos Contador
     */
    public static int getTotalInstancias() {
        return totalInstancias;
    }
}
