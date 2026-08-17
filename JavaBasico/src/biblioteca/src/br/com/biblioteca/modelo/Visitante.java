package br.com.biblioteca.modelo;

/*
 * ============================================================
 * VISITANTE - a SEGUNDA PROVA DO POLIMORFISMO
 * ============================================================
 */

/**
 * Visitante externo. Limite 1 item, sem desconto, sem acesso restrito.
 *
 * <p>Assim como {@code AudioLivro} na hierarquia de itens, esta classe foi
 * adicionada DEPOIS que o sistema ja funcionava — e nenhum laco, nenhum
 * {@code if} e nenhuma classe existente precisou ser alterada.</p>
 *
 * <p>Este e o retorno concreto de todo o trabalho de modelagem: o sistema
 * ficou ABERTO A EXTENSAO (novos tipos entram facilmente) e FECHADO A
 * MODIFICACAO (o codigo existente nao precisa ser tocado). Esse principio
 * tem nome na literatura: <i>Open/Closed Principle</i>.</p>
 */
public class Visitante extends Usuario {

    private final String documentoIdentidade;

    public Visitante(String matricula, String nome, String email,
                     String documentoIdentidade) {
        super(matricula, nome, email);

        if (documentoIdentidade == null || documentoIdentidade.isBlank()) {
            throw new IllegalArgumentException("Documento e obrigatorio para visitantes.");
        }

        this.documentoIdentidade = documentoIdentidade;
    }

    @Override
    public int getLimiteItens() {
        return 1;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.0;
    }

    @Override
    public String getCategoria() {
        return "Visitante";
    }

    @Override
    public boolean podeRetirarMaterialRestrito() {
        return false;
    }
}
