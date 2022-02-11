package mx.gda.resultados.objects;

public class EmailFile {

	private String name;
	private String contentBase64;

	public EmailFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailFile(String name, String contentBase64) {
		super();
		this.name = name;
		this.contentBase64 = contentBase64;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentBase64() {
		return contentBase64;
	}

	public void setContentBase64(String contentBase64) {
		this.contentBase64 = contentBase64;
	}

}
