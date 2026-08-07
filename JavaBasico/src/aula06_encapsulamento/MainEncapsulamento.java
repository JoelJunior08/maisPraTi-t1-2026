package aula06_encapsulamento;

import java.util.Arrays;

/*
 * ============================================================
 * AULA 06 - ENCAPSULAMENTO
 * ============================================================
 * Conceitos: invariantes de dominio, controle de acesso, vazamento
 * de referencias mutaveis, copias defensivas e imutabilidade.
 *
 * Ao executar, observe especialmente:
 * 1) por que getter+setter para tudo NAO protege regra de negocio;
 * 2) como arrays podem vazar estado interno sem copia defensiva;
 * 3) como objetos imutaveis simplificam consistencia.
 * ============================================================
 */
/**
 * Classe executavel com demonstracoes praticas de encapsulamento.
 */
public class MainEncapsulamento {

    /**
     * Ponto de entrada da aula de encapsulamento.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        System.out.println("============================================================"); // ============================================================
        System.out.println("AULA 06 - ENCAPSULAMENTO"); // AULA 06 - ENCAPSULAMENTO
        System.out.println("============================================================"); // ============================================================

        // ------------------------------------------------------------
        // [1] ALUNO RUIM x ALUNO BOM
        // ------------------------------------------------------------
        // Encapsulamento nao e sobre criar acesso irrestrito por getters/setters.
        // O objetivo e proteger INVARIANTES: estado so muda por operacoes validas.
        System.out.println("\n[1] Comparacao: AlunoRuim x AlunoBom"); // [1] Comparacao: AlunoRuim x AlunoBom

        AlunoRuim alunoRuim = new AlunoRuim();
        alunoRuim.nome = "Rafa";
        alunoRuim.idade = -5; // ⚠️ ARMADILHA: idade invalida aceita por falta de encapsulamento.
        alunoRuim.notaFinal = 15.0; // ⚠️ ARMADILHA: nota fora da faixa aceita.
        System.out.println("AlunoRuim -> idade=" + alunoRuim.idade + ", nota=" + alunoRuim.notaFinal); // AlunoRuim -> idade=-5, nota=15.0

        AlunoBom alunoBom = new AlunoBom("Lia", 19, 8.5);
        alunoBom.fazerAniversario();
        alunoBom.atualizarNotaFinal(9.0);
        System.out.println("AlunoBom -> nome=" + alunoBom.getNome() + ", idade=" + alunoBom.getIdade() + ", nota=" + alunoBom.getNotaFinal()); // AlunoBom -> nome=Lia, idade=20, nota=9.0

        // ------------------------------------------------------------
        // [2] PRODUTO COM REGRAS DE NEGOCIO
        // ------------------------------------------------------------
        // Em vez de setPreco/setEstoque livres, usamos metodos semanticos que
        // validam contexto da operacao e disparam excecoes claras quando preciso.
        System.out.println("\n[2] Produto: vender, repor e reajustar com invariantes"); // [2] Produto: vender, repor e reajustar com invariantes

        Produto produto = new Produto("Caderno", 25.0, 10);
        produto.vender(3);
        produto.repor(5);
        produto.reajustar(10.0);
        System.out.printf("Produto %s -> preco=%.2f, estoque=%d%n", produto.getNome(), produto.getPreco(), produto.getEstoque()); // Produto Caderno -> preco=27.50, estoque=12

        try {
            produto.vender(50);
        } catch (IllegalStateException erro) {
            System.out.println("Venda invalida: " + erro.getMessage()); // Venda invalida: Estoque insuficiente para a venda.
        }

        // ------------------------------------------------------------
        // [3] VAZAMENTO DE ARRAY: versao insegura e versao segura
        // ------------------------------------------------------------
        System.out.println("\n[3] Vazamento de estado com arrays"); // [3] Vazamento de estado com arrays

        double[] notasEntrada = {7.0, 8.0, 9.0};
        Turma turmaInsegura = Turma.criarInsegura("Turma A", notasEntrada);
        notasEntrada[0] = 0.0;
        System.out.println("Turma insegura apos alterar array externo: " + Arrays.toString(turmaInsegura.getNotas())); // Turma insegura apos alterar array externo: [0.0, 8.0, 9.0]

        double[] notasEntradaSegura = {7.0, 8.0, 9.0};
        Turma turmaSegura = Turma.criarSegura("Turma B", notasEntradaSegura);
        notasEntradaSegura[0] = 0.0;
        System.out.println("Turma segura apos alterar array externo: " + Arrays.toString(turmaSegura.getNotas())); // Turma segura apos alterar array externo: [7.0, 8.0, 9.0]

        double[] notasVazadas = turmaInsegura.getNotas();
        notasVazadas[1] = 1.0;
        System.out.println("Turma insegura apos alterar retorno de getNotas: " + Arrays.toString(turmaInsegura.getNotas())); // Turma insegura apos alterar retorno de getNotas: [0.0, 1.0, 9.0]

        double[] notasProtegidas = turmaSegura.getNotas();
        notasProtegidas[1] = 1.0;
        System.out.println("Turma segura apos alterar retorno de getNotas: " + Arrays.toString(turmaSegura.getNotas())); // Turma segura apos alterar retorno de getNotas: [7.0, 8.0, 9.0]

        // ✅ BOA PRATICA: melhor do que expor o array seria expor apenas comportamentos
        // de negocio, como getMedia(), sem revelar estrutura interna mutavel.
        System.out.printf("Media da turma segura: %.2f%n", turmaSegura.getMedia()); // Media da turma segura: 8.00

        // ------------------------------------------------------------
        // [4] IMUTABILIDADE COM TEMPERATURA
        // ------------------------------------------------------------
        // Objetos imutaveis evitam estados intermediarios inconsistentes e tornam
        // o raciocinio mais simples: cada "alteracao" gera nova instancia.
        System.out.println("\n[4] Classe imutavel: Temperatura"); // [4] Classe imutavel: Temperatura

        Temperatura agora = new Temperatura(22.5);
        Temperatura depois = agora.comAcrescimo(3.0);
        System.out.println("Temperatura original: " + agora.getCelsius()); // Temperatura original: 22.5
        System.out.println("Temperatura apos acrescimo (nova instancia): " + depois.getCelsius()); // Temperatura apos acrescimo (nova instancia): 25.5

        System.out.println("\nFim da Aula 06 - Encapsulamento."); // Fim da Aula 06 - Encapsulamento.
    }
}
