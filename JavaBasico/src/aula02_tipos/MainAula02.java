package aula02_tipos;

/*
 * ============================================================
 * AULA 02 - TIPOS DE DADOS
 * ============================================================
 * Conceitos: primitivos, literais, ponto flutuante, String,
 * text blocks, wrappers, casting, var, final e constantes.
 *
 * Ao executar, observe especialmente:
 * 1) por que 0.1 + 0.2 nao resulta exatamente em 0.3;
 * 2) diferenca entre comparar String com == e equals();
 * 3) risco de perda de dados em conversoes narrowing.
 * ============================================================
 */
public class MainAula02 {

    public static void main(String[] args) {
        System.out.println("============================================================"); // ============================================================
        System.out.println("AULA 02 - TIPOS DE DADOS"); // AULA 02 - TIPOS DE DADOS
        System.out.println("============================================================"); // ============================================================

        // ------------------------------------------------------------
        // [1] PRIMITIVOS: os 8 tipos basicos do Java
        // ------------------------------------------------------------
        // Tipos primitivos guardam valores diretamente em memoria.
        // Eles sao mais eficientes que wrappers e ideais para calculos.
        System.out.println("\n[1] Primitivos e faixas de valores"); // [1] Primitivos e faixas de valores

        // byte: 8 bits, faixa de -128 a 127.
        byte idadeCliente = 27;
        System.out.println("byte idadeCliente = " + idadeCliente); // byte idadeCliente = 27

        // short: 16 bits, faixa de -32.768 a 32.767.
        short estoqueLoja = 12_500;
        System.out.println("short estoqueLoja = " + estoqueLoja); // short estoqueLoja = 12500

        // int: 32 bits, faixa de -2.147.483.648 a 2.147.483.647.
        int populacaoCidade = 2_315_000;
        System.out.println("int populacaoCidade = " + populacaoCidade); // int populacaoCidade = 2315000

        // long: 64 bits, faixa muito maior que int.
        // ✅ BOA PRATICA: usar sufixo L em valores grandes para evitar overflow na inferencia literal.
        long distanciaGalaxiaKm = 9_460_730_472_580_800L;
        System.out.println("long distanciaGalaxiaKm = " + distanciaGalaxiaKm); // long distanciaGalaxiaKm = 9460730472580800

        // float: 32 bits, aproximado.
        // ⚠️ ARMADILHA: literal decimal e double por padrao; para float, use sufixo f.
        float taxaConversao = 4.75f;
        System.out.println("float taxaConversao = " + taxaConversao); // float taxaConversao = 4.75

        // double: 64 bits, aproximado, mais preciso que float.
        double precoProduto = 199.99;
        System.out.println("double precoProduto = " + precoProduto); // double precoProduto = 199.99

        // char: 16 bits sem sinal, representa um caractere Unicode.
        char categoriaProduto = 'A';
        System.out.println("char categoriaProduto = " + categoriaProduto); // char categoriaProduto = A

        // boolean: apenas true ou false.
        boolean pagamentoAprovado = true;
        System.out.println("boolean pagamentoAprovado = " + pagamentoAprovado); // boolean pagamentoAprovado = true

        // ------------------------------------------------------------
        // [2] LITERAIS LEGIVEIS
        // ------------------------------------------------------------
        // Literais bem escritos facilitam manutencao e reduzem erro humano.
        System.out.println("\n[2] Literais legiveis"); // [2] Literais legiveis

        int valorComUnderscore = 1_000_000;
        int hexadecimal = 0xFF;
        int binario = 0b1010_0110;
        double notacaoCientifica = 6.022e23;

        System.out.println("Underscore: " + valorComUnderscore); // Underscore: 1000000
        System.out.println("Hexadecimal 0xFF: " + hexadecimal); // Hexadecimal 0xFF: 255
        System.out.println("Binario 0b1010_0110: " + binario); // Binario 0b1010_0110: 166
        System.out.println("Cientifico 6.022e23: " + notacaoCientifica); // Cientifico 6.022e23: 6.022E23

        // ------------------------------------------------------------
        // [3] ARMADILHA DO PONTO FLUTUANTE
        // ------------------------------------------------------------
        // ⚠️ ARMADILHA: numeros decimais sao representados em binario (IEEE 754),
        // e algumas fracoes nao cabem exatamente nessa representacao.
        // Resultado: pequenas diferencas aparecem em contas aparentemente simples.
        System.out.println("\n[3] Armadilha do ponto flutuante"); // [3] Armadilha do ponto flutuante

        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2)); // 0.1 + 0.2 = 0.30000000000000004
        System.out.println("1.0 / 3.0 = " + (1.0 / 3.0)); // 1.0 / 3.0 = 0.3333333333333333

        // ✅ BOA PRATICA: para dinheiro, prefira BigDecimal (aula futura) ou centavos em long.

        // ------------------------------------------------------------
        // [4] STRING: imutabilidade, metodos e comparacao
        // ------------------------------------------------------------
        // String e tipo por referencia e imutavel: qualquer "alteracao"
        // cria um novo objeto em vez de modificar o original.
        System.out.println("\n[4] String: imutabilidade e comparacao"); // [4] String: imutabilidade e comparacao

        String nomeOriginal = "java basico";
        String nomeMaiusculo = nomeOriginal.toUpperCase();
        System.out.println("Original: " + nomeOriginal); // Original: java basico
        System.out.println("toUpperCase(): " + nomeMaiusculo); // toUpperCase(): JAVA BASICO
        System.out.println("Original apos toUpperCase(): " + nomeOriginal); // Original apos toUpperCase(): java basico

        String texto = "  Curso Java 21  ";
        System.out.println("trim(): '" + texto.trim() + "'"); // trim(): 'Curso Java 21'
        System.out.println("length() de 'Java': " + "Java".length()); // length() de 'Java': 4
        System.out.println("contains('Java'): " + texto.contains("Java")); // contains('Java'): true

        // Pool de literais: strings literais iguais podem apontar para o mesmo objeto.
        String linguagem1 = "Java";
        String linguagem2 = "Java";
        // new String cria explicitamente outro objeto na heap.
        String linguagem3 = new String("Java");

        System.out.println("linguagem1 == linguagem2: " + (linguagem1 == linguagem2)); // linguagem1 == linguagem2: true
        System.out.println("linguagem1 == linguagem3: " + (linguagem1 == linguagem3)); // linguagem1 == linguagem3: false
        System.out.println("linguagem1.equals(linguagem3): " + linguagem1.equals(linguagem3)); // linguagem1.equals(linguagem3): true

        // ⚠️ ARMADILHA: use == para comparar referencia (mesmo objeto),
        // e equals() para comparar conteudo textual.

        // ------------------------------------------------------------
        // [5] TEXT BLOCKS (Java 21)
        // ------------------------------------------------------------
        // Text block permite texto multi-linha de forma legivel,
        // sem excesso de "\n" e concatenacoes.
        System.out.println("\n[5] Text blocks"); // [5] Text blocks

        String recibo = """
                RECIBO DE PAGAMENTO
                Cliente: Maria Silva
                Valor: R$ 199,90
                Status: PAGO
                """;
        System.out.println(recibo);

        // ------------------------------------------------------------
        // [6] WRAPPERS, AUTOBOXING E UNBOXING
        // ------------------------------------------------------------
        // Wrappers sao versoes em classe dos primitivos (Integer, Double...)
        // e sao necessarios em colecoes, generics e APIs orientadas a objeto.
        System.out.println("[6] Wrappers e boxing/unboxing"); // [6] Wrappers e boxing/unboxing

        Integer totalPedidos = 10; // autoboxing: int -> Integer
        int pedidosProcessados = totalPedidos; // unboxing: Integer -> int
        System.out.println("Integer totalPedidos = " + totalPedidos); // Integer totalPedidos = 10
        System.out.println("int pedidosProcessados = " + pedidosProcessados); // int pedidosProcessados = 10

        // ⚠️ ARMADILHA: unboxing de null gera NullPointerException em runtime.
        // Exemplo perigoso (comentado para nao quebrar a execucao):
        // Integer quantidadeNula = null;
        // int quantidadeConvertida = quantidadeNula;
        // Erro em runtime: "java.lang.NullPointerException"

        // ------------------------------------------------------------
        // [7] CASTING: widening, narrowing e estouro
        // ------------------------------------------------------------
        // Widening: conversao para tipo maior (geralmente segura, implicita).
        // Narrowing: conversao para tipo menor (pode perder dado, exige cast).
        System.out.println("\n[7] Casting e conversoes"); // [7] Casting e conversoes

        int vendasMes = 120;
        long vendasMesLong = vendasMes; // widening implicito
        System.out.println("Widening int -> long: " + vendasMesLong); // Widening int -> long: 120

        int numeroGrande = 130;
        byte numeroEmByte = (byte) numeroGrande; // narrowing com estouro
        System.out.println("Narrowing 130 para byte: " + numeroEmByte); // Narrowing 130 para byte: -126

        char letra = 'A';
        int codigoUnicode = letra;
        char simbolo = (char) 9731;
        System.out.println("char 'A' para int: " + codigoUnicode); // char 'A' para int: 65
        System.out.println("int 9731 para char: " + simbolo); // int 9731 para char: ☃

        // ------------------------------------------------------------
        // [8] var: inferencia de tipo local
        // ------------------------------------------------------------
        // var reduz repeticao quando o tipo e obvio no lado direito.
        // ✅ BOA PRATICA: use com moderacao para preservar legibilidade.
        System.out.println("\n[8] var (inferencia local)"); // [8] var (inferencia local)

        var nomeAluno = "Joao";
        var mediaFinal = 8.75;
        var ativo = true;
        System.out.println("nomeAluno (var): " + nomeAluno); // nomeAluno (var): Joao
        System.out.println("mediaFinal (var): " + mediaFinal); // mediaFinal (var): 8.75
        System.out.println("ativo (var): " + ativo); // ativo (var): true

        // ⚠️ ARMADILHA: var funciona apenas para variaveis locais com inicializacao.
        // Exemplos invalidos (comentados):
        // var semValor;
        // Erro de compilacao: "cannot infer type for local variable"
        // var valorNulo = null;
        // Erro de compilacao: "cannot infer type for local variable"
        // public static var campo = 10;
        // Erro de compilacao: 'var' is not allowed here

        // ------------------------------------------------------------
        // [9] FINAL E CONSTANTES
        // ------------------------------------------------------------
        // final impede reatribuicao da variavel apos inicializacao.
        // Para constantes, use final com nome em MAIUSCULAS_COM_UNDERSCORE.
        System.out.println("\n[9] final e constantes"); // [9] final e constantes

        final int LIMITE_TRANSFERENCIA_DIARIA = 5_000;
        final String MOEDA_PADRAO = "BRL";

        System.out.println("LIMITE_TRANSFERENCIA_DIARIA = " + LIMITE_TRANSFERENCIA_DIARIA); // LIMITE_TRANSFERENCIA_DIARIA = 5000
        System.out.println("MOEDA_PADRAO = " + MOEDA_PADRAO); // MOEDA_PADRAO = BRL

        // ⚠️ ARMADILHA: tentar reatribuir final nao compila.
        // LIMITE_TRANSFERENCIA_DIARIA = 6_000;
        // Erro de compilacao: "cannot assign a value to final variable LIMITE_TRANSFERENCIA_DIARIA"

        System.out.println("\nFim da Aula 02."); // Fim da Aula 02.
    }
}
