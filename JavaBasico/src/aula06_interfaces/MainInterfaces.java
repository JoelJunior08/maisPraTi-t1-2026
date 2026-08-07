package aula06_interfaces;

/*
 * ============================================================
 * AULA 06 - INTERFACES
 * ============================================================
 * Conceitos: contratos, implementacoes, metodos default,
 * polimorfismo, injecao de dependencia, interface funcional,
 * multiplas interfaces e hierarquias sealed.
 *
 * Ao executar, observe especialmente como o MESMO codigo cliente
 * funciona com implementacoes diferentes por meio de interfaces.
 * ============================================================
 */
/**
 * Classe executavel da aula sobre interfaces.
 */
public class MainInterfaces {

    /**
     * Ponto de entrada da aula de interfaces.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        System.out.println("============================================================"); // ============================================================
        System.out.println("AULA 06 - INTERFACES"); // AULA 06 - INTERFACES
        System.out.println("============================================================"); // ============================================================

        // ------------------------------------------------------------
        // [1] NOTIFICADOR E METODOS DEFAULT
        // ------------------------------------------------------------
        System.out.println("\n[1] Interface Notificador e metodos default"); // [1] Interface Notificador e metodos default

        Notificador email = new NotificadorEmail();
        email.enviar("ana@empresa.com", "Seu cadastro foi aprovado.");
        email.enviarUrgente("ana@empresa.com", "Atualize sua senha agora.");
        System.out.println("Email suporta anexo? " + email.suportaAnexo()); // Email suporta anexo? true

        Notificador sms = new NotificadorSMS();
        String mensagemLonga = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.";
        sms.enviar("+55 11 99999-0000", mensagemLonga);
        System.out.println("SMS suporta anexo? " + sms.suportaAnexo()); // SMS suporta anexo? false

        // ------------------------------------------------------------
        // [2] INJECAO DE DEPENDENCIA EM PEDIDO
        // ------------------------------------------------------------
        // Pedido recebe a dependencia por construtor e depende da interface.
        System.out.println("\n[2] Pedido com injecao de dependencia"); // [2] Pedido com injecao de dependencia
        Pedido pedido = new Pedido(101, new NotificadorPush());
        pedido.fechar("usuario-app-77");

        // ------------------------------------------------------------
        // [3] POLIMORFISMO COM ARRAY DE NOTIFICADOR
        // ------------------------------------------------------------
        System.out.println("\n[3] Polimorfismo com array de Notificador"); // [3] Polimorfismo com array de Notificador
        Notificador[] canais = {new NotificadorEmail(), new NotificadorSMS(), new NotificadorPush()};
        for (Notificador canal : canais) {
            canal.enviar("cliente", "Pedido enviado para entrega.");
        }

        // ------------------------------------------------------------
        // [4] FORMA: AREA E PERIMETRO POLIMORFICOS
        // ------------------------------------------------------------
        System.out.println("\n[4] Formas geometricas via interface Forma"); // [4] Formas geometricas via interface Forma
        Forma[] formas = {new Circulo(2.0), new Retangulo(3.0, 4.0), new Triangulo(3.0, 4.0, 5.0)};
        for (Forma forma : formas) {
            System.out.printf("Area=%.2f | Perimetro=%.2f%n", forma.calcularArea(), forma.calcularPerimetro());
        }

        // ------------------------------------------------------------
        // [5] MULTIPLAS INTERFACES
        // ------------------------------------------------------------
        System.out.println("\n[5] Relatorio implementando Imprimivel e Exportavel"); // [5] Relatorio implementando Imprimivel e Exportavel
        Relatorio relatorio = new Relatorio("Fechamento Mensal");
        System.out.println(relatorio.imprimir()); // Imprimindo relatorio: Fechamento Mensal
        System.out.println(relatorio.exportar("PDF")); // Exportando 'Fechamento Mensal' em PDF

        // ------------------------------------------------------------
        // [6] INTERFACE FUNCIONAL E LAMBDAS
        // ------------------------------------------------------------
        // Este e apenas um aperitivo; lambdas e streams serao aprofundados depois.
        System.out.println("\n[6] Interface funcional Validador com lambdas"); // [6] Interface funcional Validador com lambdas
        Validador validaEmailBasico = valor -> valor != null && valor.contains("@");
        Validador validaSenhaForte = valor -> valor != null && valor.length() >= 8;
        System.out.println("Email 'joao@mail.com' valido? " + validaEmailBasico.validar("joao@mail.com")); // Email 'joao@mail.com' valido? true
        System.out.println("Senha '1234' forte? " + validaSenhaForte.validar("1234")); // Senha '1234' forte? false

        // ------------------------------------------------------------
        // [7] INTERFACE COM CONSTANTES
        // ------------------------------------------------------------
        System.out.println("\n[7] Interface Config com constantes implicitas"); // [7] Interface Config com constantes implicitas
        System.out.println("Ambiente: " + Config.AMBIENTE); // Ambiente: DEV
        System.out.println("Tempo limite: " + Config.TEMPO_LIMITE_MS + " ms"); // Tempo limite: 5000 ms

        // ------------------------------------------------------------
        // [8] SEALED + SWITCH COM PATTERN MATCHING
        // ------------------------------------------------------------
        // Como a hierarquia sealed lista todos os subtipos permitidos, o switch
        // pode ser exaustivo sem default quando todos os casos sao tratados.
        System.out.println("\n[8] FormaPagamento sealed com switch sem default"); // [8] FormaPagamento sealed com switch sem default
        FormaPagamento[] pagamentos = {new Pix(), new CartaoCredito(), new Boleto()};
        for (FormaPagamento pagamento : pagamentos) {
            String descricao = descreverPagamento(pagamento, 250.0);
            System.out.println(descricao);
        }

        System.out.println("\nFim da Aula 06 - Interfaces."); // Fim da Aula 06 - Interfaces.
    }

    /**
     * Descreve taxa por tipo de pagamento usando switch com pattern matching.
     *
     * @param formaPagamento forma de pagamento
     * @param valor valor base da compra
     * @return descricao da taxa
     */
    public static String descreverPagamento(FormaPagamento formaPagamento, double valor) {
        return switch (formaPagamento) {
            case Pix pix -> String.format("Pix: taxa de R$ %.2f", pix.calcularTaxa(valor));
            case CartaoCredito cartaoCredito -> String.format("Cartao de credito: taxa de R$ %.2f", cartaoCredito.calcularTaxa(valor));
            case Boleto boleto -> String.format("Boleto: taxa de R$ %.2f", boleto.calcularTaxa(valor));
        };
    }
}
