package ec.edu.ups.fabian.Gestion;

import ec.edu.ups.fabian.EN.ENTicket;
import ec.edu.ups.fabian.EN.ENVehiculo;

public interface GestionPaqueaderaRemoto {
	
	public boolean guardarTicket(ENTicket t);
	public Boolean guardarVehiculo(ENVehiculo v);
	public boolean actualizarTicket(ENTicket t);
	public ENTicket buscarTicK(ENTicket t);
	public ENVehiculo obtenerUnVehiculo(ENVehiculo enveh);
	public double valorPagar(int hora);
	public int obtenerCodigo();
	public int calcularTiempo(String a, String b);
	public Object[][] listarTicket();
	public Object[][] listarVehiculo(String placa);
	public boolean placaRepetida(String placa);

}
