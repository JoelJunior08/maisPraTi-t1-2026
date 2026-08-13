package aula07_heranca;

public class Main {
    public static void main(String[] args) {
        Funcionario[] equipe = {
            new Vendedor("Charla", 17000, "171", 100),
            new Vendedor("Tão", 15000, "172", 150),
//            new Funcionario("Viga", 10000, "173"),
        };

        double folha = 0;

        for(Funcionario funcionario : equipe) {
            System.out.println(funcionario);
            folha += funcionario.calcularSalario();
        }

        System.out.println("Folha de pagamento: " + folha);
    }
}
