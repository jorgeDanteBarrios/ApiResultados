package mx.gda.resultados.objects;

import java.util.List;

import javax.validation.constraints.NotNull;

public class EnviaResCovid {

	@NotNull(message = "El campo kordensucursal no puede ser nulo")
	private String kordensucursal;
	@NotNull(message = "El campo to no puede ser nulo")
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;
	@NotNull(message = "El campo cmarca no puede ser nulo")
	private Long cmarca;
	@NotNull(message = "El campo files no puede ser nulo")
	private List<EmailFile> files;
	@NotNull(message = "El campo paciente no puede ser nulo")
	private String paciente;

	public EnviaResCovid() {
		super();
		// TODO Auto-generated constructor stub
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

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
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
		return "EnviaResCovid [kordensucursal=" + kordensucursal + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc
				+ ", cmarca=" + cmarca + ", files=" + files + ", paciente=" + paciente + "]";
	}

}
