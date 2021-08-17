package mx.gda.resultados.objects;

public class Orden {

	private String orden;
	private String nombrePx;
	
	public Orden() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Orden(String orden, String nombrePx) {
		super();
		this.orden = orden;
		this.nombrePx = nombrePx;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getNombrePx() {
		return nombrePx;
	}

	public void setNombrePx(String nombrePx) {
		this.nombrePx = nombrePx;
	}		
	
}
