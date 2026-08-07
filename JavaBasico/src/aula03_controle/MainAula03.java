package aula03_controle;

/*
 * ============================================================
 * AULA 03 - CONTROLE DE FLUXO E OPERADORES
 * ============================================================
 * Conceitos: operadores, condicionais, switch, lacos e comandos
 * de controle para dirigir o fluxo de execucao de um programa.
 *
 * Ao executar, observe especialmente:
 * 1) diferenca entre divisao inteira e decimal;
 * 2) curto-circuito do && evitando NullPointerException;
 * 3) diferenca pratica entre while e do-while com condicao falsa.
 * ============================================================
 */
public class MainAula03 {

    public static void main(String[] args) {
        System.out.println("============================================================"); // ============================================================
        System.out.println("AULA 03 - CONTROLE DE FLUXO E OPERADORES"); // AULA 03 - CONTROLE DE FLUXO E OPERADORES
        System.out.println("============================================================"); // ============================================================

        // ------------------------------------------------------------
        // [1] OPERADORES
        // ------------------------------------------------------------
        // Operadores combinam, transformam e comparam valores.
        // Eles aparecem em praticamente toda regra de negocio.
        System.out.println("\n[1] Operadores: aritmeticos, incremento, relacionais e logicos"); // [1] Operadores: aritmeticos, incremento, relacionais e logicos

        int totalPedidos = 7;
        int totalItensPorPedido = 2;
        System.out.println("7 + 2 = " + (totalPedidos + totalItensPorPedido)); // 7 + 2 = 9
        System.out.println("7 - 2 = " + (totalPedidos - totalItensPorPedido)); // 7 - 2 = 5
        System.out.println("7 * 2 = " + (totalPedidos * totalItensPorPedido)); // 7 * 2 = 14
        System.out.println("7 / 2 = " + (totalPedidos / totalItensPorPedido)); // 7 / 2 = 3
        // ⚠️ ARMADILHA: int/int produz divisao inteira (parte decimal descartada).
        System.out.println("7.0 / 2 = " + (7.0 / totalItensPorPedido)); // 7.0 / 2 = 3.5
        System.out.println("7 % 2 = " + (totalPedidos % totalItensPorPedido)); // 7 % 2 = 1

        int senhaTentativas = 3;
        int preIncremento = ++senhaTentativas;
        System.out.println("Pre-incremento (++x): " + preIncremento); // Pre-incremento (++x): 4

        int codigoFila = 10;
        int posIncremento = codigoFila++;
        System.out.println("Pos-incremento (x++ retorna antes): " + posIncremento); // Pos-incremento (x++ retorna antes): 10
        System.out.println("Valor apos x++: " + codigoFila); // Valor apos x++: 11

        int notaAluno = 8;
        int notaMinima = 7;
        System.out.println("notaAluno > notaMinima: " + (notaAluno > notaMinima)); // notaAluno > notaMinima: true
        System.out.println("notaAluno == 8: " + (notaAluno == 8)); // notaAluno == 8: true
        System.out.println("notaAluno != 10: " + (notaAluno != 10)); // notaAluno != 10: true

        boolean temSaldo = true;
        boolean contaAtiva = true;
        System.out.println("temSaldo && contaAtiva: " + (temSaldo && contaAtiva)); // temSaldo && contaAtiva: true
        System.out.println("temSaldo || false: " + (temSaldo || false)); // temSaldo || false: true
        System.out.println("!contaAtiva: " + (!contaAtiva)); // !contaAtiva: false

        // Curto-circuito: em A && B, se A for false, B nem e avaliado.
        // ✅ BOA PRATICA: validar null antes de chamar metodo no objeto.
        String cupomDesconto = null;
        boolean cupomValido = cupomDesconto != null && cupomDesconto.startsWith("PROMO");
        System.out.println("Cupom valido com curto-circuito: " + cupomValido); // Cupom valido com curto-circuito: false
        // ⚠️ ARMADILHA: inverter a ordem pode gerar NullPointerException:
        // boolean erro = cupomDesconto.startsWith("PROMO") && cupomDesconto != null;

        // ------------------------------------------------------------
        // [2] IF / ELSE IF / ELSE E OPERADOR TERNARIO
        // ------------------------------------------------------------
        // if decide caminhos com base em condicoes booleanas.
        System.out.println("\n[2] if/else if/else e operador ternario"); // [2] if/else if/else e operador ternario

        double mediaFinal = 6.8;
        if (mediaFinal >= 7.0) {
            System.out.println("Situacao: aprovado."); // Situacao: aprovado.
        } else if (mediaFinal >= 5.0) {
            System.out.println("Situacao: recuperacao."); // Situacao: recuperacao.
        } else {
            System.out.println("Situacao: reprovado."); // Situacao: reprovado.
        }

        String statusPagamento = temSaldo ? "Pagamento permitido" : "Saldo insuficiente";
        System.out.println("Ternario: " + statusPagamento); // Ternario: Pagamento permitido

        // ------------------------------------------------------------
        // [3] SWITCH CLASSICO (com break)
        // ------------------------------------------------------------
        // switch classico e util quando comparamos uma mesma variavel
        // contra varios valores fixos.
        System.out.println("\n[3] switch classico com break"); // [3] switch classico com break

        int diaSemana = 3;
        switch (diaSemana) {
            case 1:
                System.out.println("Domingo"); // Domingo
                break;
            case 2:
                System.out.println("Segunda-feira"); // Segunda-feira
                break;
            case 3:
                System.out.println("Terca-feira"); // Terca-feira
                break;
            default:
                System.out.println("Outro dia"); // Outro dia
                break;
        }

        // ⚠️ ARMADILHA: esquecer break causa fall-through (cascata).
        // Exemplo COMENTADO para nao confundir a execucao principal:
        // int faixa = 1;
        // switch (faixa) {
        //     case 1:
        //         System.out.println("Basico");
        //     case 2:
        //         System.out.println("Intermediario");
        //     case 3:
        //         System.out.println("Avancado");
        // }
        // Bug observado: imprime Basico, Intermediario e Avancado,
        // mesmo quando faixa e 1.

        // ------------------------------------------------------------
        // [4] SWITCH COMO EXPRESSAO (Java 21)
        // ------------------------------------------------------------
        // Nesta forma, switch devolve valor e evita repeticao de variavel.
        System.out.println("\n[4] switch como expressao (->, casos agrupados, yield)"); // [4] switch como expressao (->, casos agrupados, yield)

        int mes = 7;
        String trimestre = switch (mes) {
            case 1, 2, 3 -> "1o trimestre";
            case 4, 5, 6 -> "2o trimestre";
            case 7, 8, 9 -> "3o trimestre";
            case 10, 11, 12 -> "4o trimestre";
            default -> "Mes invalido";
        };
        System.out.println("Trimestre do mes 7: " + trimestre); // Trimestre do mes 7: 3o trimestre

        String planoCliente = "PREMIUM";
        int descontoPlano = switch (planoCliente) {
            case "BASICO" -> 5;
            case "PREMIUM" -> {
                int descontoBase = 10;
                int bonusFidelidade = 5;
                yield descontoBase + bonusFidelidade;
            }
            default -> 0;
        };
        System.out.println("Desconto do plano PREMIUM: " + descontoPlano + "%"); // Desconto do plano PREMIUM: 15%

        // ------------------------------------------------------------
        // [5] SWITCH COM PATTERN MATCHING E WHEN (Java 21)
        // ------------------------------------------------------------
        // Pattern matching permite tratar tipos diferentes de forma segura.
        System.out.println("\n[5] switch com pattern matching e when"); // [5] switch com pattern matching e when

        Object entrada = "   Java 21   ";
        String descricaoEntrada = switch (entrada) {
            case null -> "Entrada nula";
            case String texto when texto.isBlank() -> "Texto em branco";
            case String texto when texto.trim().length() >= 5 -> "Texto longo: " + texto.trim();
            case String texto -> "Texto curto: " + texto.trim();
            case Integer numero when numero > 0 -> "Inteiro positivo: " + numero;
            default -> "Tipo nao mapeado: " + entrada.getClass().getSimpleName();
        };
        System.out.println(descricaoEntrada); // Texto longo: Java 21

        // ------------------------------------------------------------
        // [6] WHILE E DO-WHILE
        // ------------------------------------------------------------
        // while testa ANTES; do-while testa DEPOIS.
        System.out.println("\n[6] while e do-while"); // [6] while e do-while

        int contadorWhile = 1;
        while (contadorWhile <= 3) {
            System.out.println("while contador = " + contadorWhile); // while contador = 1 ... 3
            contadorWhile++;
        }

        boolean condicaoInicialmenteFalsa = false;
        while (condicaoInicialmenteFalsa) {
            System.out.println("while com condicao falsa nao executa"); // (nao imprime)
        }

        int contadorDoWhile = 1;
        do {
            System.out.println("do-while executa ao menos uma vez. contador = " + contadorDoWhile); // do-while executa ao menos uma vez. contador = 1
            contadorDoWhile++;
        } while (false);

        // ------------------------------------------------------------
        // [7] FOR CLASSICO, VARIACOES E FOR-EACH
        // ------------------------------------------------------------
        // for classico concentra inicializacao, condicao e passo em uma linha.
        System.out.println("\n[7] for classico, variacoes e for-each"); // [7] for classico, variacoes e for-each

        for (int parcela = 1; parcela <= 3; parcela++) {
            System.out.println("Parcela " + parcela + " registrada."); // Parcela 1/2/3 registrada.
        }

        int indice = 0;
        for (; indice < 2; indice++) {
            System.out.println("For sem inicializacao interna, indice = " + indice); // For sem inicializacao interna, indice = 0/1
        }

        String[] modulos = {"Introducao", "Tipos", "Controle"};
        for (String modulo : modulos) {
            System.out.println("For-each modulo: " + modulo); // For-each modulo: Introducao/Tipos/Controle
        }

        // ------------------------------------------------------------
        // [8] BREAK, CONTINUE E BREAK COM ROTULO
        // ------------------------------------------------------------
        // break encerra laco; continue pula para a proxima iteracao.
        System.out.println("\n[8] break, continue e break com rotulo"); // [8] break, continue e break com rotulo

        for (int tentativa = 1; tentativa <= 5; tentativa++) {
            if (tentativa == 3) {
                System.out.println("continue na tentativa 3"); // continue na tentativa 3
                continue;
            }
            if (tentativa == 5) {
                System.out.println("break na tentativa 5"); // break na tentativa 5
                break;
            }
            System.out.println("Tentativa processada: " + tentativa); // Tentativa processada: 1,2,4
        }

        buscaProduto:
        for (int corredor = 1; corredor <= 3; corredor++) {
            for (int prateleira = 1; prateleira <= 3; prateleira++) {
                if (corredor == 2 && prateleira == 2) {
                    System.out.println("Produto encontrado no corredor 2, prateleira 2."); // Produto encontrado no corredor 2, prateleira 2.
                    break buscaProduto;
                }
                System.out.println("Verificando corredor " + corredor + ", prateleira " + prateleira); // Verificando corredor X, prateleira Y
            }
        }

        System.out.println("\nConsulte tambem a classe ArmadilhasControle para erros classicos comentados."); // Consulte tambem a classe ArmadilhasControle para erros classicos comentados.
        System.out.println("Fim da Aula 03."); // Fim da Aula 03.
    }
}
