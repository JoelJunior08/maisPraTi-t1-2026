package aula04_arrays;

/*
 * ============================================================
 * AULA 04 - ESTATISTICAS DE NOTAS
 * ============================================================
 * Esta classe concentra operacoes numericas comuns em arrays de
 * notas: soma, media, maior e menor valor.
 *
 * Ao estudar, observe por que maior/menor iniciam com notas[0]:
 * isso evita resultados incorretos quando todos os valores forem
 * negativos, quando a escala mudar, ou quando 0 nao fizer sentido.
 * ============================================================
 */
public class EstatisticasNotas {

    public int calcularSoma(int[] notas) {
        int soma = 0;
        for (int nota : notas) {
            soma += nota;
        }
        return soma;
    }

    public double calcularMedia(int[] notas) {
        if (notas.length == 0) {
            return 0.0;
        }
        return (double) calcularSoma(notas) / notas.length;
    }

    public int encontrarMaior(int[] notas) {
        // ✅ BOA PRATICA: iniciar com notas[0] usa um valor REAL do proprio array.
        // Se iniciarmos com 0, um array com valores todos menores que 0 ficaria incorreto.
        int maior = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] > maior) {
                maior = notas[i];
            }
        }
        return maior;
    }

    public int encontrarMenor(int[] notas) {
        // ✅ BOA PRATICA: pelo mesmo motivo, menor tambem inicia com notas[0].
        int menor = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] < menor) {
                menor = notas[i];
            }
        }
        return menor;
    }
}
