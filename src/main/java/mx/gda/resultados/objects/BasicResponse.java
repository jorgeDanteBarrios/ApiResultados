package mx.gda.resultados.objects;

public class BasicResponse {
	
	private Boolean exito;
	private String pdfBase64;
	private String msgError;

	public BasicResponse() {
	}

	public BasicResponse(Boolean exito, String pdfBase64, String msgError) {
		this.exito = exito;
		this.pdfBase64 = pdfBase64;
		this.msgError = msgError;
	}

	public Boolean getExito() {
		return this.exito;
	}

	public void setExito(Boolean exito) {
		this.exito = exito;
	}

	public String getPdfBase64() {
		return this.pdfBase64;
	}

	public void setPdfBase64(String pdfBase64) {
		this.pdfBase64 = pdfBase64;
	}

	public String getMsgError() {
		return this.msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
}
