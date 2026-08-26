package aula10_streams;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        List<Integer> numeros = List.of(1, 2, 3, 4, 5);
        //pegar pares, dobrar cada um e somar tudo
//        int soma = 0;
//        for(int n : numeros) {
//            if(n % 2 == 0) {
//                soma += n * 2;
//            }
//        }
//
//        System.out.println(soma);

//        int soma = numeros.stream()
//                .filter(n -> n % 2 == 0)
//                .mapToInt(n -> n * 2)
//                .sum();
//        System.out.println(soma);

        //stream (fonte) -> filter(seleção) -> map(transforma) -> collect(encerra)
        //Lambda - Uma funçõa curta, que ela é escrita no seu lugar de uso.

        List<String> palavras = List.of("Java", "Python", "C++", "JavaScript");

        palavras.stream()
                .filter(p -> p.length() > 4)
                .forEach(System.out::println);

        palavras.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        //Usando Streams, a apartir de 'palavras' criem um outro array
        //que armazena o tamanho de cada palavra.

        List<Integer> tamanhos = palavras.stream()
                .map(String::length)
                .toList();

        System.out.println(tamanhos);

        //Dada List<Integer> de 1 a 20, imprima só os múltiplos de 3.

        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        numeros.stream()
                .filter(n -> n % 3 == 0)
                .forEach(System.out::println);
    }
}
