package aula01_introducao;

/*
 * ============================================================
 * AULA 01 - CLASSE DE APOIO (SAUDACAO)
 * ============================================================
 * Esta classe existe para mostrar que um projeto Java e composto
 * por multiplos arquivos cooperando entre si.
 *
 * MainAula01 cuida do fluxo da aula; Saudacao cuida da mensagem
 * de boas-vindas. Essa separacao e um primeiro passo de organizacao.
 * ============================================================
 */
public class Saudacao {

    // Metodo simples para retornar uma mensagem personalizada.
    // Em aulas futuras, esse tipo de separacao evolui para responsabilidades
    // mais claras entre classes de dominio, servico e interface.
    public String criarMensagemBoasVindas(String nomeAluno) {
        return "Bem-vindo(a), " + nomeAluno + "! Vamos comecar sua jornada em Java.";
    }
}
