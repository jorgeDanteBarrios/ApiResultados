package mx.gda.resultados.objects;

public class EmailResultadoCovid {

	private Long kordensucursal;
	private String ordenLabcore;
	private Integer cmarca;
	private String email;
	private String krescovid;
	private String paciente;

	public EmailResultadoCovid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailResultadoCovid(Long kordensucursal, String ordenLabcore, Integer cmarca, String email, String krescovid,
			String paciente) {
		super();
		this.kordensucursal = kordensucursal;
		this.ordenLabcore = ordenLabcore;
		this.cmarca = cmarca;
		this.email = email;
		this.krescovid = krescovid;
		this.paciente = paciente;
	}

	public Long getKordensucursal() {
		return kordensucursal;
	}

	public void setKordensucursal(Long kordensucursal) {
		this.kordensucursal = kordensucursal;
	}

	public String getOrdenLabcore() {
		return ordenLabcore;
	}

	public void setOrdenLabcore(String ordenLabcore) {
		this.ordenLabcore = ordenLabcore;
	}

	public Integer getCmarca() {
		return cmarca;
	}

	public void setCmarca(Integer cmarca) {
		this.cmarca = cmarca;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKrescovid() {
		return krescovid;
	}

	public void setKrescovid(String krescovid) {
		this.krescovid = krescovid;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

}
