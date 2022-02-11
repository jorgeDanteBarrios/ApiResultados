package mx.gda.resultados.objects;

import java.util.List;

public class EnviaResCovid {

	private String kordensucursal;
	private List<String> to;
	private Long cmarca;
	private List<EmailFile> files;
	private String paciente;

	public EnviaResCovid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnviaResCovid(String kordensucursal, List<String> to, Long cmarca, List<EmailFile> files, String paciente) {
		super();
		this.kordensucursal = kordensucursal;
		this.to = to;
		this.cmarca = cmarca;
		this.files = files;
		this.paciente = paciente;
	}

	public String getKordensucursal() {
		return kordensucursal;
	}

	public void setKordensucursal(String kordensucursal) {
		this.kordensucursal = kordensucursal;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public Long getCmarca() {
		return cmarca;
	}

	public void setCmarca(Long cmarca) {
		this.cmarca = cmarca;
	}

	public List<EmailFile> getFiles() {
		return files;
	}

	public void setFiles(List<EmailFile> files) {
		this.files = files;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	@Override
	public String toString() {
		return "EnviaResCovid [kordensucursal=" + kordensucursal + ", to=" + to + ", cmarca=" + cmarca + ", files="
				+ files + ", paciente=" + paciente + "]";
	}

}
