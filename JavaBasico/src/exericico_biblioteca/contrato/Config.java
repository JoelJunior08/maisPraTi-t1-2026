package exericico_biblioteca.contrato;

/*
 * ============================================================
 * CONFIG - constantes globais do sistema
 * ============================================================
 * Conceito demonstrado: constantes em interface.
 * ============================================================
 */

/**
 * Constantes de configuracao do sistema.
 *
 * <p>Esta interface nao declara nenhum metodo: ela existe apenas para
 * agrupar constantes. Note que NENHUM campo abaixo tem modificador.</p>
 *
 * <p>Isso acontece porque, em uma interface, todo campo ja e
 * implicitamente {@code public static final}. Escrever os tres
 * modificadores seria redundante — o compilador os adiciona sozinho.</p>
 *
 * <p>Consequencias praticas:</p>
 * <ul>
 *   <li>{@code public}  - visivel de qualquer lugar</li>
 *   <li>{@code static}  - acessado pela interface: {@code Config.CAPACIDADE_ACERVO}</li>
 *   <li>{@code final}   - impossivel reatribuir</li>
 * </ul>
 *
 * <p>Interface NAO tem estado de instancia. Se voce sentir necessidade de
 * um atributo mutavel aqui, o que voce quer e uma classe, nao uma interface.</p>
 */
public interface Config {

    /** Capacidade maxima do acervo. Limite do array — ver comentario em Biblioteca. */
    int CAPACIDADE_ACERVO = 50;

    /** Capacidade maxima de usuarios cadastrados. */
    int CAPACIDADE_USUARIOS = 30;

    /** Ano minimo aceito para publicacao (invencao da imprensa de tipos moveis). */
    int ANO_MINIMO_PUBLICACAO = 1400;

    /** Ano maximo aceito. Em producao real viria de LocalDate.now().getYear(). */
    int ANO_MAXIMO_PUBLICACAO = 2026;

    /** Largura das linhas separadoras dos relatorios. */
    int LARGURA_RELATORIO = 62;
}