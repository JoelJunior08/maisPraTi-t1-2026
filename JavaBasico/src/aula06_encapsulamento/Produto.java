package aula06_encapsulamento;

/*
 * ============================================================
 * AULA 06 - ENCAPSULAMENTO (PRODUTO)
 * ============================================================
 * Nesta versao, o estado e protegido por invariantes e por
 * operacoes de negocio explicitas: vender, repor e reajustar.
 *
 * ✅ BOA PRATICA: evitar setters genericos em entidades de
 * dominio; prefira metodos que expressem intencao de negocio.
 * ============================================================
 */
/**
 * Produto com regras de negocio e invariantes protegidas.
 */
public class Produto {

    private final String nome;
    private double preco;
    private int estoque;

    /**
     * Cria produto com nome, preco e estoque iniciais validos.
     *
     * @param nome nome comercial
     * @param preco preco inicial
     * @param estoque estoque inicial
     */
    public Produto(String nome, double preco, int estoque) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto e obrigatorio.");
        }
        if (preco < 0.0) {
            throw new IllegalArgumentException("Preco nao pode ser negativo.");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque nao pode ser negativo.");
        }

        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o preco atual.
     *
     * @return preco atual
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Retorna o estoque atual.
     *
     * @return quantidade em estoque
     */
    public int getEstoque() {
        return estoque;
    }

    /**
     * Vende uma quantidade do produto.
     *
     * @param quantidade quantidade a vender
     */
    public void vender(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade para venda deve ser positiva.");
        }
        if (quantidade > estoque) {
            throw new IllegalStateException("Estoque insuficiente para a venda.");
        }
        this.estoque -= quantidade;
    }

    /**
     * Repoe unidades no estoque.
     *
     * @param quantidade quantidade a repor
     */
    public void repor(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade para reposicao deve ser positiva.");
        }
        this.estoque += quantidade;
    }

    /**
     * Reajusta o preco por percentual.
     *
     * @param percentual percentual de reajuste (ex.: 10 para +10, -5 para -5)
     */
    public void reajustar(double percentual) {
        double fator = 1 + (percentual / 100.0);
        if (fator <= 0) {
            throw new IllegalArgumentException("Reajuste invalido: preco nao pode ficar zero ou negativo.");
        }
        this.preco = this.preco * fator;
    }
}
