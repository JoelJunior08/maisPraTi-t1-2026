package biblioteca.modelo;

import biblioteca.contrato.Notificavel;
import java.util.Arrays;

/*
 * ============================================================
 * USUARIO - classe abstrata, raiz da hierarquia de usuarios
 * ============================================================
 * Pilares demonstrados:
 *   ENCAPSULAMENTO - invariantes, ausencia de setters perigosos
 *                    e COPIA DEFENSIVA do array interno
 *   HERANCA        - base comum dos tipos de usuario
 *   POLIMORFISMO   - limite e desconto variam por tipo
 *   INTERFACE      - implementa Notificavel
 * ============================================================
 */

/**
 * Qualquer pessoa cadastrada na biblioteca.
 *
 * <p>Classe abstrata pelo mesmo motivo de {@code ItemAcervo}: nao existe
 * "usuario generico" no mundo real. Existe aluno, professor, visitante.</p>
 *
 * <p>Note que ela e abstrata E implementa uma interface ao mesmo tempo.
 * Isso e comum e desejavel: a classe abstrata concentra o estado e o codigo
 * comum da FAMILIA ("e um"), enquanto a interface declara uma CAPACIDADE
 * independente da hierarquia ("consegue fazer"). Elas nao competem.</p>
 */
public abstract class Usuario implements Notificavel {

    private final String matricula;
    private final String nome;
    private final String email;

    /**
     * Itens atualmente com o usuario.
     *
     * ⚠️ ARMADILHA CLASSICA: este atributo e {@code private}, mas isso sozinho
     * NAO protege nada. {@code private} protege a VARIAVEL, nao o OBJETO
     * apontado por ela. Se um getter devolvesse este array diretamente,
     * qualquer codigo externo poderia fazer
     * {@code usuario.getItensEmprestados()[0] = null} e corromper o estado
     * interno — sem tocar em nenhum setter.
     *
     * <p>A defesa e a COPIA DEFENSIVA em {@code getItensEmprestados()}.
     * Veja tambem a demonstracao pratica disso em {@code Main}.</p>
     */
    private final ItemAcervo[] itensEmprestados;

    private int quantidadeEmprestada;
    private double multaAcumulada;

    private static int totalUsuariosCriados = 0;

    protected Usuario(String matricula, String nome, String email) {

        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Matricula e obrigatoria.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }

        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.quantidadeEmprestada = 0;
        this.multaAcumulada = 0.0;

        // ⚠️ DETALHE SUTIL: getLimiteItens() e um metodo ABSTRATO sendo
        // chamado dentro do construtor da classe abstrata. Funciona porque a
        // subclasse ja definiu sua resposta em tempo de compilacao — mas em
        // codigo de producao chamar metodo sobrescrivivel no construtor e
        // arriscado, porque os atributos da subclasse ainda nao foram
        // inicializados. Aqui e seguro apenas porque getLimiteItens() devolve
        // uma constante e nao depende de estado.
        this.itensEmprestados = new ItemAcervo[getLimiteItens()];

        totalUsuariosCriados++;
    }

    // ------------------------------------------------------------
    // METODOS ABSTRATOS — o que varia de um tipo de usuario para outro
    // ------------------------------------------------------------

    /** Quantos itens este tipo de usuario pode ter simultaneamente. */
    public abstract int getLimiteItens();

    /** Percentual de desconto na multa (0.0 a 1.0). */
    public abstract double getPercentualDesconto();

    /** Rotulo do tipo, usado nos relatorios. */
    public abstract String getCategoria();

    /**
     * Se o usuario pode retirar material restrito (teses).
     *
     * <p>Esta e a resposta a pergunta "onde mora a regra de acesso a tese?".
     * A permissao pertence ao USUARIO, entao e ele quem responde. A classe
     * {@code Tese} apenas pergunta, sem nunca precisar saber quais tipos de
     * usuario existem.</p>
     */
    public abstract boolean podeRetirarMaterialRestrito();

    // ------------------------------------------------------------
    // METODOS CONCRETOS — escritos uma vez, herdados por todos
    // ------------------------------------------------------------

    /**
     * Aplica o desconto do tipo de usuario sobre a multa bruta.
     *
     * <p>Outro <i>Template Method</i>: a formula e igual para todos e mora
     * aqui; o percentual varia e e delegado ao filho. Se cada subclasse
     * implementasse o calculo inteiro, a formula apareceria quatro vezes.</p>
     */
    public double aplicarDesconto(double multaBruta) {
        if (multaBruta < 0) {
            throw new IllegalArgumentException("Multa bruta nao pode ser negativa.");
        }
        return multaBruta * (1 - getPercentualDesconto());
    }

    /** Se ainda ha vaga dentro do limite deste usuario. */
    public boolean podeEmprestarMais() {
        return quantidadeEmprestada < getLimiteItens();
    }

    /**
     * Registra um item emprestado.
     *
     * <p>Visibilidade de pacote (sem modificador): so classes do pacote
     * {@code modelo} chamam este metodo. Ele e um detalhe da colaboracao
     * entre {@code ItemEmprestavel} e {@code Usuario}, nao parte da API
     * publica do sistema.</p>
     */
    void registrarEmprestimo(ItemAcervo item) {
        if (!podeEmprestarMais()) {
            throw new IllegalStateException(
                    "Limite de emprestimos ja atingido para " + nome);
        }
        itensEmprestados[quantidadeEmprestada] = item;
        quantidadeEmprestada++;
    }

    /** Remove um item da lista do usuario ao devolver. */
    boolean registrarDevolucao(ItemAcervo item) {
        for (int i = 0; i < quantidadeEmprestada; i++) {
            if (itensEmprestados[i].equals(item)) {      // equals, nao ==
                // desloca os seguintes uma posicao para tras
                for (int j = i; j < quantidadeEmprestada - 1; j++) {
                    itensEmprestados[j] = itensEmprestados[j + 1];
                }
                itensEmprestados[quantidadeEmprestada - 1] = null;
                quantidadeEmprestada--;
                return true;
            }
        }
        return false;
    }

    /**
     * Acrescenta multa ao saldo devedor.
     *
     * <p>Nao existe {@code setMultaAcumulada()}. Multa nao e "definida" — ela
     * e ACUMULADA por atraso e QUITADA por pagamento. Os dois metodos abaixo
     * expressam o dominio do problema; um setter nao expressaria nada.</p>
     */
    public void acumularMulta(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor de multa nao pode ser negativo.");
        }
        this.multaAcumulada += valor;
    }

    /** Quita parte ou todo o saldo devedor. */
    public void quitarMulta(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser positivo.");
        }
        if (valor > multaAcumulada) {
            throw new IllegalStateException(String.format(
                    "Pagamento de R$ %.2f excede o debito de R$ %.2f.", valor, multaAcumulada));
        }
        this.multaAcumulada -= valor;
    }

    // ------------------------------------------------------------
    // ACESSORES
    // ------------------------------------------------------------

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    public double getMultaAcumulada() {
        return multaAcumulada;
    }

    /**
     * Devolve os itens emprestados — em uma COPIA.
     *
     * <p>✅ BOA PRATICA: {@code Arrays.copyOf()} cria um array novo. Quem
     * recebe pode fazer o que quiser com ele sem afetar o estado interno do
     * usuario. Sem esta copia, o {@code private} do atributo seria decorativo.</p>
     *
     * <p>Melhor ainda seria NAO expor o array. Frequentemente quem chama quer
     * a QUANTIDADE ou um RESUMO, nao a estrutura interna. Aqui o getter existe
     * justamente para demonstrar a copia defensiva em {@code Main}.</p>
     *
     * <p>Regra pratica: {@code String} e primitivos podem ser devolvidos a
     * vontade — {@code String} e imutavel. Arrays e objetos mutaveis, nao.</p>
     */
    public ItemAcervo[] getItensEmprestados() {
        return Arrays.copyOf(itensEmprestados, quantidadeEmprestada);
    }

    public static int getTotalUsuariosCriados() {
        return totalUsuariosCriados;
    }

    // ------------------------------------------------------------
    // CONTRATO Notificavel
    // ------------------------------------------------------------

    /**
     * Unico metodo que a interface exige. Os metodos {@code notificar()} e
     * {@code notificarUrgente()} vem prontos como {@code default}.
     */
    @Override
    public String getIdentificacaoContato() {
        return email != null ? email : nome;
    }

    // ------------------------------------------------------------
    // Object
    // ------------------------------------------------------------

    /** Dois usuarios sao o mesmo se tem a mesma matricula. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Usuario outro = (Usuario) obj;
        return matricula.equals(outro.matricula);
    }

    @Override
    public int hashCode() {
        return matricula.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d/%d itens | multa R$ %.2f",
                nome, getCategoria(), quantidadeEmprestada, getLimiteItens(), multaAcumulada);
    }
}
