package exericico_biblioteca.modelo;

import exericico_biblioteca.contrato.Emprestavel;

public abstract class ItemEmprestavel extends ItemAcervo implements Emprestavel {
    private Usuario usuarioAtual;

    protected ItemEmprestavel(String codigo, String titulo, int ano) {
        super(codigo, titulo, ano);
        this.usuarioAtual = null;
    }

    @Override
    public boolean emprestar(Usuario usuario) {
        if(usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo");
        }

        if(!isDisponivel()) {
            System.out.println("Item indisponível.");
        }
        marcarComoEmprestado();
        return true;
    }
}
