package mx.gda.resultados.objects;

public class MuestraFuji {

	private Long cexamen;
	private String estudio;
	private Long cexamenproceso;
	private Long kmuestrasucursal;

	public MuestraFuji() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MuestraFuji(Long cexamen, String estudio, Long cexamenproceso, Long kmuestrasucursal) {
		super();
		this.cexamen = cexamen;
		this.estudio = estudio;
		this.cexamenproceso = cexamenproceso;
		this.kmuestrasucursal = kmuestrasucursal;
	}

	public Long getCexamen() {
		return cexamen;
	}

	public void setCexamen(Long cexamen) {
		this.cexamen = cexamen;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public Long getCexamenproceso() {
		return cexamenproceso;
	}

	public void setCexamenproceso(Long cexamenproceso) {
		this.cexamenproceso = cexamenproceso;
	}

	public Long getKmuestrasucursal() {
		return kmuestrasucursal;
	}

	public void setKmuestrasucursal(Long kmuestrasucursal) {
		this.kmuestrasucursal = kmuestrasucursal;
	}

}
