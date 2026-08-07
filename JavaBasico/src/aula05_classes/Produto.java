package aula05_classes;

/*
 * ============================================================
 * AULA 05 - CLASSES E OBJETOS (PRODUTO)
 * ============================================================
 * Esta classe demonstra sobrecarga de construtores e uso de
 * atributo final para dados que nao devem mudar apos criacao.
 *
 * ✅ BOA PRATICA: usar this(...) evita duplicar logica entre
 * construtores e reduz risco de inconsistencias.
 * ============================================================
 */
/**
 * Representa um produto com codigo imutavel e dados comerciais.
 */
public class Produto {

    private final String codigo;
    private String nome;
    private double preco;

    /**
     * Cria produto com codigo e nome, usando preco padrao zero.
     *
     * @param codigo codigo do produto
     * @param nome   nome do produto
     */
    public Produto(String codigo, String nome) {
        this(codigo, nome, 0.0);
    }

    /**
     * Cria produto com codigo, nome e preco.
     *
     * @param codigo codigo do produto
     * @param nome   nome do produto
     * @param preco  preco do produto
     */
    public Produto(String codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Retorna o codigo do produto.
     *
     * @return codigo do produto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return nome do produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o preco do produto.
     *
     * @return preco do produto
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Atualiza nome do produto.
     *
     * @param nome novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atualiza preco quando o valor for valido.
     *
     * @param preco novo preco
     */
    public void setPreco(double preco) {
        if (preco >= 0.0) {
            this.preco = preco;
        }
    }
}
