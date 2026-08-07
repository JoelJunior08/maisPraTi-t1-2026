package aula03_controle;

/*
 * ============================================================
 * AULA 03 - ARMADILHAS DE CONTROLE (EXEMPLOS COMENTADOS)
 * ============================================================
 * Esta classe NAO e para executar logica: ela serve como mural
 * de erros classicos que iniciantes cometem em estruturas de
 * controle. Todos os trechos ficam comentados para manter o
 * projeto compilando e permitir estudo guiado.
 * ============================================================
 */
public class ArmadilhasControle {

    private ArmadilhasControle() {
        // ✅ BOA PRATICA: classe utilitaria de exemplos nao precisa instanciacao.
    }

    // ⚠️ ARMADILHA: for com ponto e virgula no final.
    // O ponto e virgula encerra o laco imediatamente, entao o bloco
    // seguinte executa uma unica vez, fora do for.
    // Exemplo problematico:
    // for (int i = 0; i < 3; i++);
    // {
    //     System.out.println("Esse bloco NAO pertence ao for");
    // }

    // ⚠️ ARMADILHA: while sem incremento/atualizacao da condicao.
    // Quando a condicao nunca muda, o programa pode entrar em loop infinito.
    // Exemplo problematico:
    // int contador = 1;
    // while (contador <= 3) {
    //     System.out.println("Loop infinito por falta de contador++");
    // }

    // ⚠️ ARMADILHA: esquecer break no switch classico.
    // Isso causa fall-through: casos seguintes executam em cascata.
    // Exemplo problematico:
    // int opcaoMenu = 2;
    // switch (opcaoMenu) {
    //     case 1:
    //         System.out.println("Cadastrar cliente");
    //     case 2:
    //         System.out.println("Listar clientes");
    //     case 3:
    //         System.out.println("Encerrar");
    // }
    // Resultado inesperado para opcao 2: imprime "Listar clientes"
    // E TAMBEM "Encerrar" por ausencia de break no case 2.
}
