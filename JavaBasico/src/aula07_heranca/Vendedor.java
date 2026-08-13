package aula07_heranca;

public class Vendedor extends Funcionario {
    private double totalVendas;
    private static final double COMISSAO = 0.15;

    public Vendedor(String nome, double salario, String cpf, double totalVendas) {
        super(nome, salario, cpf);
        this.totalVendas = totalVendas;
    }

    @Override
    public double calcularSalario() {
        return super.calcularSalario() + this.totalVendas * COMISSAO;
    }
}
