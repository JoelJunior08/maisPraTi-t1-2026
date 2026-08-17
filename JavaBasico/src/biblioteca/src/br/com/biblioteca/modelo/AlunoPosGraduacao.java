package br.com.biblioteca.modelo;

/*
 * ============================================================
 * ALUNOPOSGRADUACAO - subclasse concreta de Usuario
 * ============================================================
 */

/**
 * Aluno de mestrado ou doutorado. Limite 5 itens, 20% de desconto,
 * com acesso a material restrito.
 *
 * <p>Demonstra {@code super.toString()}: em vez de reescrever a formatacao
 * inteira, esta classe REAPROVEITA a do pai e acrescenta sua informacao.
 * Sobrescrever nem sempre significa substituir.</p>
 */
public class AlunoPosGraduacao extends Usuario {

    private final String programa;
    private final String orientador;

    public AlunoPosGraduacao(String matricula, String nome, String email,
                             String programa, String orientador) {
        super(matricula, nome, email);
        this.programa = programa;
        this.orientador = orientador;
    }

    @Override
    public int getLimiteItens() {
        return 5;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.20;     // 20%
    }

    @Override
    public String getCategoria() {
        return "Pos-graduacao";
    }

    @Override
    public boolean podeRetirarMaterialRestrito() {
        return true;
    }

    /**
     * ✅ BOA PRATICA: estende a saida do pai em vez de duplica-la.
     * Se a formatacao de Usuario mudar, esta classe acompanha sozinha.
     */
    @Override
    public String toString() {
        return super.toString() + " | " + programa;
    }
}
