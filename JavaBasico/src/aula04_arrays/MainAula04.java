package aula04_arrays;

import java.util.Arrays;

/*
 * ============================================================
 * AULA 04 - ARRAYS
 * ============================================================
 * Conceitos: arrays unidimensionais e bidimensionais, percursos,
 * operacoes com java.util.Arrays, comparacoes e armadilhas comuns.
 *
 * Ao executar, observe especialmente:
 * 1) diferenca entre referencia e conteudo em arrays;
 * 2) por que for-each nao altera os valores originais;
 * 3) como a ordem dos lacos muda o sentido do percurso em matriz.
 * ============================================================
 */
public class MainAula04 {

    public static void main(String[] args) {
        System.out.println("============================================================"); // ============================================================
        System.out.println("AULA 04 - ARRAYS"); // AULA 04 - ARRAYS
        System.out.println("============================================================"); // ============================================================

        // ------------------------------------------------------------
        // [1] ARRAYS UNIDIMENSIONAIS: declaracao, criacao e inicializacao
        // ------------------------------------------------------------
        // Arrays guardam varios valores do MESMO tipo em posicoes contiguas.
        // Sao ideais quando o tamanho e conhecido antecipadamente.
        System.out.println("\n[1] Unidimensionais: declaracao, criacao e inicializacao"); // [1] Unidimensionais: declaracao, criacao e inicializacao

        int[] notasTurma; // Apenas declaracao da referencia.
        notasTurma = new int[4]; // Criacao com tamanho fixo (4 posicoes).

        int[] idadesAlunos = {18, 21, 19, 22}; // Inicializacao direta.

        // Valores padrao ao criar arrays com new:
        // byte/short/int/long -> 0 | float/double -> 0.0
        // char -> '\u0000' | boolean -> false | referencia -> null
        System.out.println("Valor padrao de int[0]: " + notasTurma[0]); // Valor padrao de int[0]: 0

        // Indices vao de 0 ate length - 1.
        System.out.println("Primeira idade (indice 0): " + idadesAlunos[0]); // Primeira idade (indice 0): 18
        System.out.println("Ultima idade (indice length-1): " + idadesAlunos[idadesAlunos.length - 1]); // Ultima idade (indice length-1): 22

        // ⚠️ ARMADILHA: acessar indice fora do intervalo gera excecao em runtime.
        // System.out.println(idadesAlunos[4]);
        // java.lang.ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4

        // ✅ BOA PRATICA: array.length e ATRIBUTO; String.length() e METODO.
        // array.length nao usa parenteses; string.length() usa parenteses.
        String nomeCurso = "Java Basico";
        System.out.println("Tamanho do array idadesAlunos: " + idadesAlunos.length); // Tamanho do array idadesAlunos: 4
        System.out.println("Tamanho da String nomeCurso: " + nomeCurso.length()); // Tamanho da String nomeCurso: 11

        // ------------------------------------------------------------
        // [2] PERCURSO: for classico e for-each
        // ------------------------------------------------------------
        // for classico da acesso ao indice, util para alteracoes in-place.
        System.out.println("\n[2] Percurso com for classico e for-each"); // [2] Percurso com for classico e for-each

        int[] notasProva = {7, 8, 6};
        for (int indice = 0; indice < notasProva.length; indice++) {
            notasProva[indice] += 1;
        }
        System.out.println("Apos for classico (+1): " + Arrays.toString(notasProva)); // Apos for classico (+1): [8, 9, 7]

        // for-each facilita leitura, mas trabalha com COPIA da posicao em tipos primitivos.
        for (int nota : notasProva) {
            nota = 10;
            System.out.println("Valor da variavel do for-each: " + nota); // Valor da variavel do for-each: 10
        }
        System.out.println("Array apos tentativa de alterar no for-each: " + Arrays.toString(notasProva)); // Array apos tentativa de alterar no for-each: [8, 9, 7]

        // ------------------------------------------------------------
        // [3] CLASSE ESTATISTICASNOTAS: soma, media, maior e menor
        // ------------------------------------------------------------
        // Encapsular calculos em classe separada evita repeticao e melhora manutencao.
        System.out.println("\n[3] Estatisticas de notas"); // [3] Estatisticas de notas

        int[] notasSemestre = {8, 5, 10, 7, 6};
        EstatisticasNotas estatisticas = new EstatisticasNotas();
        System.out.println("Notas analisadas: " + Arrays.toString(notasSemestre)); // Notas analisadas: [8, 5, 10, 7, 6]
        System.out.println("Soma: " + estatisticas.calcularSoma(notasSemestre)); // Soma: 36
        System.out.printf("Media: %.2f%n", estatisticas.calcularMedia(notasSemestre)); // Media: 7,20 (ou 7.20 dependendo do locale)
        System.out.println("Maior nota: " + estatisticas.encontrarMaior(notasSemestre)); // Maior nota: 10
        System.out.println("Menor nota: " + estatisticas.encontrarMenor(notasSemestre)); // Menor nota: 5

        // ------------------------------------------------------------
        // [4] java.util.Arrays: toString, sort, binarySearch, copyOf...
        // ------------------------------------------------------------
        // A classe Arrays oferece operacoes comuns e seguras para vetores.
        System.out.println("\n[4] Utilitarios da classe Arrays"); // [4] Utilitarios da classe Arrays

        int[] codigosProdutos = {40, 10, 30, 20};
        System.out.println("toString: " + Arrays.toString(codigosProdutos)); // toString: [40, 10, 30, 20]

        Arrays.sort(codigosProdutos);
        System.out.println("sort: " + Arrays.toString(codigosProdutos)); // sort: [10, 20, 30, 40]

        int posicaoCodigo30 = Arrays.binarySearch(codigosProdutos, 30);
        System.out.println("binarySearch(30): indice " + posicaoCodigo30); // binarySearch(30): indice 2
        // ⚠️ ARMADILHA: binarySearch exige array ORDENADO; sem sort, o resultado pode ser incorreto.

        int[] primeirosTres = Arrays.copyOf(codigosProdutos, 3);
        System.out.println("copyOf(3): " + Arrays.toString(primeirosTres)); // copyOf(3): [10, 20, 30]

        int[] estoquePadrao = new int[5];
        Arrays.fill(estoquePadrao, 100);
        System.out.println("fill com 100: " + Arrays.toString(estoquePadrao)); // fill com 100: [100, 100, 100, 100, 100]

        int[] estoquePadraoCopia = {100, 100, 100, 100, 100};
        System.out.println("Arrays.equals conteudo: " + Arrays.equals(estoquePadrao, estoquePadraoCopia)); // Arrays.equals conteudo: true

        // ------------------------------------------------------------
        // [5] REFERENCIA VS CONTEUDO: println(array), ==, equals e alias
        // ------------------------------------------------------------
        // Arrays sao objetos: variaveis guardam referencia, nao os dados em si.
        System.out.println("\n[5] Referencia x conteudo em arrays"); // [5] Referencia x conteudo em arrays

        int[] arrayA = {1, 2, 3};
        int[] arrayB = {1, 2, 3};

        System.out.println("println(arrayA): " + arrayA); // println(arrayA): [I@<hash>
        System.out.println("arrayA == arrayB: " + (arrayA == arrayB)); // arrayA == arrayB: false
        System.out.println("arrayA.equals(arrayB): " + arrayA.equals(arrayB)); // arrayA.equals(arrayB): false
        System.out.println("Arrays.equals(arrayA, arrayB): " + Arrays.equals(arrayA, arrayB)); // Arrays.equals(arrayA, arrayB): true

        int[] apelido = arrayA;
        apelido[0] = 99;
        System.out.println("arrayA apos alterar apelido: " + Arrays.toString(arrayA)); // arrayA apos alterar apelido: [99, 2, 3]
        System.out.println("apelido e a mesma referencia de arrayA: " + (apelido == arrayA)); // apelido e a mesma referencia de arrayA: true

        // ------------------------------------------------------------
        // [6] ARRAYS BIDIMENSIONAIS (matriz)
        // ------------------------------------------------------------
        // Matriz em Java e "array de arrays". Cada linha e um array independente.
        System.out.println("\n[6] Bidimensionais: matriz, deepToString e matriz irregular"); // [6] Bidimensionais: matriz, deepToString e matriz irregular

        int[][] matrizVendas = {
            {10, 12, 9},
            {8, 15, 11}
        };

        for (int linha = 0; linha < matrizVendas.length; linha++) {
            for (int coluna = 0; coluna < matrizVendas[linha].length; coluna++) {
                System.out.println("matrizVendas[" + linha + "][" + coluna + "] = " + matrizVendas[linha][coluna]);
            }
        }

        System.out.println("deepToString: " + Arrays.deepToString(matrizVendas)); // deepToString: [[10, 12, 9], [8, 15, 11]]

        int[][] matrizVendasCopia = {
            {10, 12, 9},
            {8, 15, 11}
        };
        System.out.println("deepEquals entre matrizes: " + Arrays.deepEquals(matrizVendas, matrizVendasCopia)); // deepEquals entre matrizes: true

        int[][] matrizIrregular = {
            {1, 2, 3},
            {4},
            {5, 6}
        };
        System.out.println("Matriz irregular: " + Arrays.deepToString(matrizIrregular)); // Matriz irregular: [[1, 2, 3], [4], [5, 6]]

        // ------------------------------------------------------------
        // [7] CLASSE BOLETIM: medias por aluno e por materia
        // ------------------------------------------------------------
        // Delegamos para outra classe para reforcar reuso e separacao de responsabilidade.
        System.out.println("\n[7] Boletim com medias por linha e por coluna"); // [7] Boletim com medias por linha e por coluna

        double[][] notasBoletim = {
            {8.0, 7.5, 9.0},
            {6.0, 8.5, 7.0},
            {9.5, 9.0, 8.5}
        };
        Boletim boletim = new Boletim();
        boletim.imprimirBoletim(notasBoletim);

        System.out.println("\nFim da Aula 04."); // Fim da Aula 04.
    }
}
