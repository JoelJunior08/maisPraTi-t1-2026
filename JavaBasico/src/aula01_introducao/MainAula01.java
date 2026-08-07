package aula01_introducao;

/*
 * ============================================================
 * AULA 01 - INTRODUCAO AO JAVA
 * ============================================================
 * Conceitos: estrutura minima de um programa Java, compilacao,
 * execucao na JVM, saida no console e colaboracao entre classes.
 *
 * Ao executar, observe especialmente:
 * 1) como cada tipo de saida (print, println, printf) se comporta;
 * 2) como a classe MainAula01 chama a classe Saudacao;
 * 3) como pequenos erros de sintaxe/convencao quebram a compilacao.
 * ============================================================
 */
public class MainAula01 {

    // O metodo main e a "porta de entrada" da aplicacao.
    // A JVM procura exatamente essa assinatura para iniciar a execucao.
    public static void main(String[] args) {
        System.out.println("============================================================"); // ============================================================
        System.out.println("AULA 01 - PRIMEIRO CONTATO COM JAVA"); // AULA 01 - PRIMEIRO CONTATO COM JAVA
        System.out.println("============================================================"); // ============================================================

        // ------------------------------------------------------------
        // DISSECANDO A ASSINATURA: public static void main(String[] args)
        // ------------------------------------------------------------
        // public: indica que o metodo pode ser acessado de fora da classe.
        // Sem esse modificador, a JVM nao consegue invocar o ponto de entrada.
        // static: permite chamar o metodo sem criar objeto da classe.
        // A JVM inicia por aqui antes de existir qualquer instancia.
        // void: informa que o metodo nao devolve valor ao final da execucao.
        // main: nome reservado do ponto de entrada reconhecido pela JVM.
        // String[] args: vetor de argumentos de linha de comando.
        // Exemplo de uso: java MainAula01 Maria 2026
        System.out.println("\n[1] Assinatura do metodo main explicada nos comentarios."); // [1] Assinatura do metodo main explicada nos comentarios.

        // ------------------------------------------------------------
        // CICLO DE EXECUCAO: .java -> javac -> .class -> JVM
        // ------------------------------------------------------------
        // O arquivo .java contem codigo-fonte legivel por humanos.
        // O compilador javac traduz esse codigo para bytecode (.class),
        // um formato intermediario que nao depende do sistema operacional.
        // A JVM carrega o .class e executa as instrucoes no ambiente local.
        // ✅ BOA PRATICA: entender esse fluxo ajuda a diagnosticar erros:
        // erro no .java (sintaxe) acontece na compilacao; erro em runtime
        // acontece durante execucao na JVM.
        System.out.println("\n[2] Ciclo: .java -> javac -> .class -> JVM"); // [2] Ciclo: .java -> javac -> .class -> JVM

        // ------------------------------------------------------------
        // SAIDA NO CONSOLE: print vs println vs printf
        // ------------------------------------------------------------
        System.out.println("\n[3] Demonstracao de print, println e printf:"); // [3] Demonstracao de print, println e printf:

        // print: imprime sem quebrar a linha.
        System.out.print("print: Olá, "); // print: Ola,
        System.out.print("mundo!"); // mundo!
        System.out.println(" <- mesma linha"); //  <- mesma linha

        // println: imprime e ja quebra a linha ao final.
        System.out.println("println: cada chamada termina com quebra de linha."); // println: cada chamada termina com quebra de linha.
        System.out.println("println: segunda linha separada automaticamente."); // println: segunda linha separada automaticamente.

        // printf: permite formatar texto com placeholders (%s, %d, %.2f...).
        String nomeCurso = "Java Basico";
        int totalAulas = 6;
        System.out.printf("printf: Curso %s com %d aulas principais.%n", nomeCurso, totalAulas); // printf: Curso Java Basico com 6 aulas principais.

        // ------------------------------------------------------------
        // MAIS DE UM ARQUIVO: classe chamando outra classe
        // ------------------------------------------------------------
        // Em projetos reais, separamos responsabilidades em varias classes.
        // Aqui, MainAula01 delega a mensagem de boas-vindas para Saudacao.
        // Isso melhora organizacao e manutencao do codigo.
        Saudacao saudacao = new Saudacao();
        String mensagemBoasVindas = saudacao.criarMensagemBoasVindas("Estudante");
        System.out.println("\n[4] Chamada entre classes:"); // [4] Chamada entre classes:
        System.out.println(mensagemBoasVindas); // Bem-vindo(a), Estudante! Vamos comecar sua jornada em Java.

        // ------------------------------------------------------------
        // ERROS CLASSICOS DE INICIANTE (exemplos comentados)
        // ------------------------------------------------------------
        // ⚠️ ARMADILHA: o nome da classe publica DEVE ser igual ao nome do arquivo.
        // Exemplo incorreto (arquivo MainAula01.java):
        // public class ProgramaInicial {
        //     public static void main(String[] args) {}
        // }
        // Erro esperado do compilador:
        // "class ProgramaInicial is public, should be declared in a file named ProgramaInicial.java"

        // ⚠️ ARMADILHA: o metodo precisa ser exatamente main, minusculo.
        // Exemplo incorreto:
        // public static void Main(String[] args) {
        //     System.out.println("Nao executa como ponto de entrada");
        // }
        // Erro esperado ao tentar executar:
        // "Error: Main method not found in class ..."

        // ⚠️ ARMADILHA: esquecer ponto e virgula encerra a compilacao.
        // Exemplo incorreto:
        // System.out.println("Faltou ponto e virgula")
        // Erro esperado do compilador:
        // "';' expected"

        System.out.println("\nFim da Aula 01."); // Fim da Aula 01.
    }
}
