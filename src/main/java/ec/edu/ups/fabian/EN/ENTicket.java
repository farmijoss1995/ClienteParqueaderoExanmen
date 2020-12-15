package ec.edu.ups.fabian.EN;

import java.io.Serializable;

public class ENTicket implements Serializable {

	private int tiId;
	private String tiFechaActual;
	private String tiHoraIngreso;
	private String tiCodigo;
	private ENVehiculo VehiculoveID;
	private int tiempo;
	private String tiHoraSalida;
	private double tiCobro;

	public String getTiHoraSalida() {
		return tiHoraSalida;
	}

	public void setTiHoraSalida(String tiHoraSalida) {
		this.tiHoraSalida = tiHoraSalida;
	}

	public int getTiId() {
		return tiId;
	}

	public void setTiId(int tiId) {
		this.tiId = tiId;
	}

	public String getTiFechaActual() {
		return tiFechaActual;
	}

	public void setTiFechaActual(String tiFechaActual) {
		this.tiFechaActual = tiFechaActual;
	}

	public String getTiHoraIngreso() {
		return tiHoraIngreso;
	}

	public void setTiHoraIngreso(String tiHoraIngreso) {
		this.tiHoraIngreso = tiHoraIngreso;
	}

	public String getTiCodigo() {
		return tiCodigo;
	}

	public void setTiCodigo(String tiCodigo) {
		this.tiCodigo = tiCodigo;
	}

	public ENVehiculo getVehiculoveID() {
		return VehiculoveID;
	}

	public void setVehiculoveID(ENVehiculo VehiculoveID) {
		this.VehiculoveID = VehiculoveID;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public double getTiCobro() {
		return tiCobro;
	}

	public void setTiCobro(double tiCobro) {
		this.tiCobro = tiCobro;
	}

	@Override
	public String toString() {
		return "ENTicket [tiId=" + tiId + ", tiFechaActual=" + tiFechaActual + ", tiHoraIngreso=" + tiHoraIngreso
				+ ", tiCodigo=" + tiCodigo + ", VehiculoveID=" + VehiculoveID + ", tiempo=" + tiempo + ", tiHoraSalida="
				+ tiHoraSalida + ", tiCobro=" + tiCobro + "]";
	}
	
}
