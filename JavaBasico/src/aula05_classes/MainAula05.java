package aula05_classes;

import java.util.Arrays;

/*
 * ============================================================
 * AULA 05 - CLASSES, OBJETOS, CONSTRUTORES E STATIC
 * ============================================================
 * Conceitos: modelagem de classes, encapsulamento basico, this,
 * sobrecarga, membros static, estado de instancia e passagem de
 * parametros em Java.
 *
 * Ao executar, observe especialmente:
 * 1) por que metodos static funcionam sem objeto quando nao ha estado;
 * 2) por que um setSaldo livre seria perigoso para regra de negocio;
 * 3) como Java passa parametros por valor em todos os cenarios.
 * ============================================================
 */
/**
 * Classe executavel da aula 05 com demonstracoes praticas dos conceitos.
 */
public class MainAula05 {

    /**
     * Ponto de entrada da aula 05.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        System.out.println("============================================================"); // ============================================================
        System.out.println("AULA 05 - CLASSES"); // AULA 05 - CLASSES
        System.out.println("============================================================"); // ============================================================

        // ------------------------------------------------------------
        // [1] CLASSE MINIMA: Pessoa
        // ------------------------------------------------------------
        // Classe define um modelo; objeto e uma instancia concreta desse modelo.
        // Aqui, this.nome e this.idade deixam explicito que usamos atributos do objeto atual.
        System.out.println("\n[1] Pessoa: classe minima com estado e comportamento"); // [1] Pessoa: classe minima com estado e comportamento
        Pessoa pessoa = new Pessoa("Marina", 24);
        System.out.println(pessoa.apresentar()); // Oi! Eu sou Marina e tenho 24 anos.

        // ------------------------------------------------------------
        // [2] ContaBancaria: encapsulamento e regras de negocio
        // ------------------------------------------------------------
        // Encapsular saldo com private impede alteracao arbitraria de fora.
        // ✅ BOA PRATICA: em vez de setSaldo, oferecemos operacoes com regra:
        // deposito positivo, saque com saldo suficiente, transferencia valida.
        System.out.println("\n[2] Conta bancaria: depositar, sacar e transferir"); // [2] Conta bancaria: depositar, sacar e transferir
        ContaBancaria contaAna = new ContaBancaria("Ana", "0001-1", 500.0);
        ContaBancaria contaBruno = new ContaBancaria("Bruno", "0001-2", 200.0);

        System.out.println("Saldo inicial Ana: " + contaAna.getSaldo()); // Saldo inicial Ana: 500.0
        System.out.println("Deposito de 150 em Ana: " + contaAna.depositar(150)); // Deposito de 150 em Ana: true
        System.out.println("Saque de 100 em Ana: " + contaAna.sacar(100)); // Saque de 100 em Ana: true
        System.out.println("Transferencia 300 Ana -> Bruno: " + contaAna.transferirPara(contaBruno, 300)); // Transferencia 300 Ana -> Bruno: true
        System.out.println(contaAna); // ContaBancaria{...}
        System.out.println(contaBruno); // ContaBancaria{...}

        // ------------------------------------------------------------
        // [3] Calculadora estatica
        // ------------------------------------------------------------
        // Como nao existe estado para guardar, os metodos podem ser static.
        // Isso permite chamar pela classe, sem criar objeto, igual ao estilo de Math.
        // ⚠️ ARMADILHA: metodo static NAO acessa atributo de instancia diretamente,
        // porque ele existe antes de qualquer objeto ser criado.
        System.out.println("\n[3] Calculadora com metodos static"); // [3] Calculadora com metodos static
        System.out.println("Somar 10 + 5 = " + Calculadora.somar(10, 5)); // Somar 10 + 5 = 15.0
        System.out.println("Subtrair 10 - 5 = " + Calculadora.subtrair(10, 5)); // Subtrair 10 - 5 = 5.0
        System.out.println("Multiplicar 10 * 5 = " + Calculadora.multiplicar(10, 5)); // Multiplicar 10 * 5 = 50.0
        System.out.println("Dividir 10 / 2 = " + Calculadora.dividir(10, 2)); // Dividir 10 / 2 = 5.0

        try {
            Calculadora.dividir(10, 0);
        } catch (ArithmeticException erro) {
            System.out.println("Divisao 10 / 0 gerou erro: " + erro.getMessage()); // Divisao 10 / 0 gerou erro: Divisao por zero nao e permitida.
        }

        // ------------------------------------------------------------
        // [4] CalculadoraComMemoria: quando o estado justifica o objeto
        // ------------------------------------------------------------
        System.out.println("\n[4] Calculadora com memoria (estado por objeto)"); // [4] Calculadora com memoria (estado por objeto)
        CalculadoraComMemoria memoriaA = new CalculadoraComMemoria();
        CalculadoraComMemoria memoriaB = new CalculadoraComMemoria();

        memoriaA.somar(30);
        memoriaA.subtrair(5);
        memoriaB.somar(100);

        System.out.println("Memoria A: " + memoriaA.getMemoria()); // Memoria A: 25.0
        System.out.println("Memoria B: " + memoriaB.getMemoria()); // Memoria B: 100.0
        memoriaA.limpar();
        System.out.println("Memoria A apos limpar: " + memoriaA.getMemoria()); // Memoria A apos limpar: 0.0

        // ------------------------------------------------------------
        // [5] Produto: sobrecarga de construtores com this()
        // ------------------------------------------------------------
        // Sobrecarga = mesmo nome de metodo/construtor com assinaturas diferentes.
        // Sobrescrita (proxima aula) = redefinir comportamento herdado em subclasse.
        // ⚠️ ARMADILHA: ao criar QUALQUER construtor manualmente, o construtor
        // padrao sem argumentos nao e mais gerado automaticamente pelo compilador.
        System.out.println("\n[5] Produto: sobrecarga e atributo final"); // [5] Produto: sobrecarga e atributo final
        Produto produtoCaneta = new Produto("P001", "Caneta");
        Produto produtoCaderno = new Produto("P002", "Caderno", 29.90);

        produtoCaneta.setPreco(3.50);
        System.out.println("Caneta -> codigo=" + produtoCaneta.getCodigo() + ", preco=" + produtoCaneta.getPreco()); // Caneta -> codigo=P001, preco=3.5
        System.out.println("Caderno -> codigo=" + produtoCaderno.getCodigo() + ", preco=" + produtoCaderno.getPreco()); // Caderno -> codigo=P002, preco=29.9

        // Exemplo que nao compila (mantenha comentado):
        // produtoCaneta.setCodigo("P999");
        // Erro: nao existe setCodigo, porque codigo e final e deve ser definido no construtor.

        // ------------------------------------------------------------
        // [6] Contador static: dado compartilhado da classe
        // ------------------------------------------------------------
        System.out.println("\n[6] Contador de instancias com static"); // [6] Contador de instancias com static
        new Contador();
        new Contador();
        new Contador();
        System.out.println("Total de instancias criadas: " + Contador.getTotalInstancias()); // Total de instancias criadas: 3

        // ------------------------------------------------------------
        // [7] Passagem de parametros: Java sempre por valor
        // ------------------------------------------------------------
        System.out.println("\n[7] Passagem de parametros em Java"); // [7] Passagem de parametros em Java
        PassagemDeParametros demonstracao = new PassagemDeParametros();

        int idadeAluno = 20;
        demonstracao.tentarAlterarPrimitivo(idadeAluno);
        // Primitivo: o metodo recebeu uma COPIA do valor, entao o original nao muda.
        System.out.println("Idade apos metodo: " + idadeAluno); // Idade apos metodo: 20

        int[] notas = {7, 8, 9};
        demonstracao.alterarConteudoArray(notas);
        // Array: copia da referencia aponta para o mesmo objeto; alterar conteudo afeta original.
        System.out.println("Notas apos alterar conteudo: " + Arrays.toString(notas)); // Notas apos alterar conteudo: [10, 8, 9]

        demonstracao.reatribuirArrayLocalmente(notas);
        // Reatribuir a referencia DENTRO do metodo so troca a copia local da referencia.
        System.out.println("Notas apos reatribuir no metodo: " + Arrays.toString(notas)); // Notas apos reatribuir no metodo: [10, 8, 9]

        System.out.println("\nFim da Aula 05."); // Fim da Aula 05.
    }
}
