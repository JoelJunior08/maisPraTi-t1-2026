package exericico_biblioteca.contrato;

public interface Reservavel {
    void reservar(Usuario usuario);
    boolean temReserva();
    String getReservante();
}
