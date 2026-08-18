package biblioteca.modelo;

/*
 * ============================================================
 * TESE - subclasse que ESTENDE o comportamento do pai com super
 * ============================================================
 * Conceitos: sobrescrita com super.metodo(), regra de acesso
 *            resolvida sem instanceof.
 * ============================================================
 */

/**
 * Tese ou dissertacao. Prazo 7 dias, multa R$ 1,50/dia.
 * Restrita a usuarios com acesso a material de pos-graduacao.
 *
 * <p><b>Onde mora a regra "so pos-graduacao pode retirar"?</b> Essa foi a
 * decisao de projeto mais discutivel de todo o sistema. Tres caminhos foram
 * considerados:</p>
 *
 * <ol>
 *   <li><b>Verificar o tipo do usuario aqui</b>, com
 *       {@code if (usuario instanceof AlunoPosGraduacao || usuario instanceof Professor)}.
 *       <i>Rejeitado:</i> toda vez que um novo tipo de usuario com acesso
 *       surgir, seria preciso voltar aqui e editar a condicao. E o oposto do
 *       que polimorfismo veio resolver.</li>
 *
 *   <li><b>Perguntar ao usuario se ele tem a permissao</b>, via
 *       {@code usuario.podeRetirarMaterialRestrito()}.
 *       <i>Escolhido.</i> A permissao e uma caracteristica do USUARIO, entao
 *       e ele quem deve responder. Cada tipo responde conforme sua propria
 *       regra, e novos tipos se encaixam sozinhos.</li>
 *
 *   <li><b>Criar uma interface {@code AcessoRestrito}</b> implementada pelos
 *       itens restritos. <i>Valido tambem</i>, e seria o caminho natural se
 *       houvesse varios niveis de restricao. Para um unico nivel, seria
 *       estrutura demais para pouco ganho.</li>
 * </ol>
 *
 * <p>Nao ha resposta unica aqui — ha uma escolha justificada. Saber defender
 * a decisao vale mais que a decisao em si.</p>
 */
public class Tese extends ItemEmprestavel {

    private final String autor;
    private final String orientador;
    private final String programaPosGraduacao;

    public Tese(String codigo, String titulo, int ano, String autor,
                String orientador, String programaPosGraduacao) {
        super(codigo, titulo, ano);

        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor da tese e obrigatorio.");
        }

        this.autor = autor;
        this.orientador = orientador;
        this.programaPosGraduacao = programaPosGraduacao;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 7;
    }

    @Override
    public double getMultaPorDia() {
        return 1.50;
    }

    /**
     * Acrescenta a verificacao de acesso e delega o resto ao pai.
     *
     * <p>✅ BOA PRATICA: {@code super.emprestar(usuario)} REAPROVEITA toda a
     * logica ja escrita em {@code ItemEmprestavel} — disponibilidade, limite
     * do usuario, registro e mensagens. Esta classe acrescenta apenas o que
     * lhe e especifico, sem duplicar uma linha sequer.</p>
     *
     * <p>Sobrescrever nao significa obrigatoriamente SUBSTITUIR. Muitas vezes
     * o certo e ESTENDER: fazer o seu pedaco e chamar o pai.</p>
     */
    @Override
    public boolean emprestar(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        }

        // A regra especifica desta classe — sem perguntar o TIPO do usuario.
        if (!usuario.podeRetirarMaterialRestrito()) {
            System.out.printf("   [X] %-22s tentou \"%s\" - restrito a pos-graduacao%n",
                    usuario.getNome(), getTitulo());
            return false;
        }

        return super.emprestar(usuario);   // o pai cuida do resto
    }

    @Override
    public String getCategoria() {
        return "TESE";
    }

    @Override
    public String getDescricaoDetalhada() {
        return autor + " | Orient.: " + orientador + " | " + programaPosGraduacao;
    }
}
