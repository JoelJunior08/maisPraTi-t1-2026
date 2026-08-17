package exericico_biblioteca.modelo;

public class ObraReferencia extends ItemAcervo {
    private final String tipoObra;
    private final String localizacaoEstante;

    public ObraReferencia(String titulo, String autor, String editora, String anoPublicacao, String tipoObra, String localizacaoEstante) {
        super(titulo, autor, editora, anoPublicacao);
        this.tipoObra = tipoObra;
        this.localizacaoEstante = localizacaoEstante;
    }

    public void consultarNoLocal(Usuario usuario) {
        System.out.println("Obra referência consultada pelo usuário: " + usuario.getNome());
        System.out.println("Localização da obra referência: " + localizacaoEstante);
    }

    @Override
    public String getCategoria() {
        return "Referência";
    }

    @Override
    public String getDescricaoDetalhada() {
        return "Tipo de obra: " + tipoObra + "\nLocalização na estante: " + localizacaoEstante;
    }

    public String getLocalizacaoEstante() {
        return this.localizacaoEstante;
    }
}
