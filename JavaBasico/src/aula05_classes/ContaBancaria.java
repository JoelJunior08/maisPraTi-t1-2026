package aula05_classes;

/*
 * ============================================================
 * AULA 05 - CLASSES E OBJETOS (CONTA BANCARIA)
 * ============================================================
 * Esta classe mostra encapsulamento basico: os dados internos
 * ficam protegidos e o acesso ocorre por metodos controlados.
 *
 * Ao estudar, observe por que existe depositar/sacar/transferir
 * em vez de um setSaldo livre: regras de negocio precisam ficar
 * centralizadas para evitar estados invalidos.
 * ============================================================
 */
/**
 * Representa uma conta bancaria com operacoes essenciais.
 */
public class ContaBancaria {

    private String titular;
    private String numeroConta;
    private double saldo;

    /**
     * Cria uma conta com titular, numero e saldo inicial.
     *
     * @param titular      nome do titular
     * @param numeroConta  identificador da conta
     * @param saldoInicial saldo inicial da conta
     */
    public ContaBancaria(String titular, String numeroConta, double saldoInicial) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = Math.max(0.0, saldoInicial);
    }

    /**
     * Retorna o nome do titular.
     *
     * @return titular da conta
     */
    public String getTitular() {
        return titular;
    }

    /**
     * Retorna o numero da conta.
     *
     * @return numero da conta
     */
    public String getNumeroConta() {
        return numeroConta;
    }

    /**
     * Retorna o saldo atual.
     *
     * @return saldo da conta
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Deposita valor positivo na conta.
     *
     * @param valor valor para deposito
     * @return true quando o deposito e aceito; false em valor invalido
     */
    public boolean depositar(double valor) {
        if (valor <= 0) {
            return false;
        }
        saldo += valor;
        return true;
    }

    /**
     * Realiza saque quando houver saldo suficiente.
     *
     * @param valor valor para saque
     * @return true quando o saque e concluido; false caso contrario
     */
    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            return false;
        }
        saldo -= valor;
        return true;
    }

    /**
     * Transfere valor desta conta para outra conta.
     *
     * @param destino conta de destino
     * @param valor   valor da transferencia
     * @return true quando a transferencia e concluida; false caso contrario
     */
    public boolean transferirPara(ContaBancaria destino, double valor) {
        if (destino == null) {
            return false;
        }
        if (!sacar(valor)) {
            return false;
        }
        destino.depositar(valor);
        return true;
    }

    /**
     * Retorna representacao textual da conta.
     *
     * @return texto com dados principais da conta
     */
    @Override
    public String toString() {
        return "ContaBancaria{titular='" + titular + "', numeroConta='" + numeroConta + "', saldo="
            + String.format("%.2f", saldo) + "}";
    }
}
