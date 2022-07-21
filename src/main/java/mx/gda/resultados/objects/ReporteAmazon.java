package mx.gda.resultados.objects;

public class ReporteAmazon {

	private String nombre_paciente;
	private String edad_paciente;
	private String tipo;
	private String fecha_toma;
	private String nombre_medico;
	private String cedula_medico;
	private String firmaBase64_medico;
	private String diagnostico;
	private String resultado;

	public ReporteAmazon() {
		super();
	}

	public ReporteAmazon(String nombre_paciente, String edad_paciente, String tipo, String fecha_toma,
			String nombre_medico, String cedula_medico, String firmaBase64_medico, String diagnostico,
			String resultado) {
		super();
		this.nombre_paciente = nombre_paciente;
		this.edad_paciente = edad_paciente;
		this.tipo = tipo;
		this.fecha_toma = fecha_toma;
		this.nombre_medico = nombre_medico;
		this.cedula_medico = cedula_medico;
		this.firmaBase64_medico = firmaBase64_medico;
		this.diagnostico = diagnostico;
		this.resultado = resultado;
	}

	public String getNombre_paciente() {
		return nombre_paciente;
	}

	public void setNombre_paciente(String nombre_paciente) {
		this.nombre_paciente = nombre_paciente;
	}

	public String getEdad_paciente() {
		return edad_paciente;
	}

	public void setEdad_paciente(String edad_paciente) {
		this.edad_paciente = edad_paciente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFecha_toma() {
		return fecha_toma;
	}

	public void setFecha_toma(String fecha_toma) {
		this.fecha_toma = fecha_toma;
	}

	public String getNombre_medico() {
		return nombre_medico;
	}

	public void setNombre_medico(String nombre_medico) {
		this.nombre_medico = nombre_medico;
	}

	public String getCedula_medico() {
		return cedula_medico;
	}

	public void setCedula_medico(String cedula_medico) {
		this.cedula_medico = cedula_medico;
	}

	public String getFirmaBase64_medico() {
		return firmaBase64_medico;
	}

	public void setFirmaBase64_medico(String firmaBase64_medico) {
		this.firmaBase64_medico = firmaBase64_medico;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
