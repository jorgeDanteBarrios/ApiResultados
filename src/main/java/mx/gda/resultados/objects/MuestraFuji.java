package mx.gda.resultados.objects;

public class MuestraFuji {

	private Long cexamen;
	private String estudio;
	private Long cexamenproceso;
	private Long kmuestrasucursal;
	private Long csucursaltranslado;

	public MuestraFuji() {
		super();
	}


	public MuestraFuji(Long cexamen, String estudio, Long cexamenproceso, Long kmuestrasucursal, Long csucursaltranslado) {
		this.cexamen = cexamen;
		this.estudio = estudio;
		this.cexamenproceso = cexamenproceso;
		this.kmuestrasucursal = kmuestrasucursal;
		this.csucursaltranslado = csucursaltranslado;
	}

	public Long getCexamen() {
		return this.cexamen;
	}

	public void setCexamen(Long cexamen) {
		this.cexamen = cexamen;
	}

	public String getEstudio() {
		return this.estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public Long getCexamenproceso() {
		return this.cexamenproceso;
	}

	public void setCexamenproceso(Long cexamenproceso) {
		this.cexamenproceso = cexamenproceso;
	}

	public Long getKmuestrasucursal() {
		return this.kmuestrasucursal;
	}

	public void setKmuestrasucursal(Long kmuestrasucursal) {
		this.kmuestrasucursal = kmuestrasucursal;
	}

	public Long getCsucursaltranslado() {
		return this.csucursaltranslado;
	}

	public void setCsucursaltranslado(Long csucursaltranslado) {
		this.csucursaltranslado = csucursaltranslado;
	}

	public MuestraFuji cexamen(Long cexamen) {
		setCexamen(cexamen);
		return this;
	}

	public MuestraFuji estudio(String estudio) {
		setEstudio(estudio);
		return this;
	}

	public MuestraFuji cexamenproceso(Long cexamenproceso) {
		setCexamenproceso(cexamenproceso);
		return this;
	}

	public MuestraFuji kmuestrasucursal(Long kmuestrasucursal) {
		setKmuestrasucursal(kmuestrasucursal);
		return this;
	}

	public MuestraFuji csucursaltranslado(Long csucursaltranslado) {
		setCsucursaltranslado(csucursaltranslado);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" cexamen='" + getCexamen() + "'" +
			", estudio='" + getEstudio() + "'" +
			", cexamenproceso='" + getCexamenproceso() + "'" +
			", kmuestrasucursal='" + getKmuestrasucursal() + "'" +
			", csucursaltranslado='" + getCsucursaltranslado() + "'" +
			"}";
	}
	
}
