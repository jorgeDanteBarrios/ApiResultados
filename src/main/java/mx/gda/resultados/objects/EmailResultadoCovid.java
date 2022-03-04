package mx.gda.resultados.objects;

public class EmailResultadoCovid {

	private String krescovid;
	private Integer marca;
	private Long korden;
	private Long kordensucursal;
	private String paciente;
	private String labcore;
	private Integer tipo;
	private String emailPx;
	private String emailMedico;
	private String emailEmpresa;

	public EmailResultadoCovid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailResultadoCovid(String krescovid, Integer marca, Long korden, Long kordensucursal, String paciente,
			String labcore, Integer tipo, String emailPx, String emailMedico, String emailEmpresa) {
		super();
		this.krescovid = krescovid;
		this.marca = marca;
		this.korden = korden;
		this.kordensucursal = kordensucursal;
		this.paciente = paciente;
		this.labcore = labcore;
		this.tipo = tipo;
		this.emailPx = emailPx;
		this.emailMedico = emailMedico;
		this.emailEmpresa = emailEmpresa;
	}

	public String getKrescovid() {
		return krescovid;
	}

	public void setKrescovid(String krescovid) {
		this.krescovid = krescovid;
	}

	public Integer getMarca() {
		return marca;
	}

	public void setMarca(Integer marca) {
		this.marca = marca;
	}

	public Long getKorden() {
		return korden;
	}

	public void setKorden(Long korden) {
		this.korden = korden;
	}

	public Long getKordensucursal() {
		return kordensucursal;
	}

	public void setKordensucursal(Long kordensucursal) {
		this.kordensucursal = kordensucursal;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getLabcore() {
		return labcore;
	}

	public void setLabcore(String labcore) {
		this.labcore = labcore;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getEmailPx() {
		return emailPx;
	}

	public void setEmailPx(String emailPx) {
		this.emailPx = emailPx;
	}

	public String getEmailMedico() {
		return emailMedico;
	}

	public void setEmailMedico(String emailMedico) {
		this.emailMedico = emailMedico;
	}

	public String getEmailEmpresa() {
		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}

	@Override
	public String toString() {
		return "EmailResultadoCovid [krescovid=" + krescovid + ", marca=" + marca + ", korden=" + korden
				+ ", kordensucursal=" + kordensucursal + ", paciente=" + paciente + ", labcore=" + labcore + ", tipo="
				+ tipo + ", emailPx=" + emailPx + ", emailMedico=" + emailMedico + ", emailEmpresa=" + emailEmpresa
				+ "]";
	}

}
