package mx.gda.resultados.objects;

import java.util.List;

public class TordenSucursal {
	
	private Long kordensucursal;
	private String claveLabcore;
	private Integer cmarca;
	private Long csucursal;
	private Long cestadoRegistro;
	private Boolean bPatcore;
	private Boolean bFuji;
	private List<MuestraFuji> muestras_fuji;
	
	public TordenSucursal() {
		super();
		this.bPatcore=false;
		this.bFuji=false;
	}

	public TordenSucursal(Long kordensucursal, String claveLabcore, Integer cmarca, Long csucursal,	Long cestadoRegistro) {
		super();
		this.kordensucursal = kordensucursal;
		this.claveLabcore = claveLabcore;
		this.cmarca = cmarca;
		this.csucursal = csucursal;
		this.cestadoRegistro = cestadoRegistro;
		this.bPatcore=false;
		this.bFuji=false;
	}

	public Long getKordensucursal() {
		return kordensucursal;
	}

	public void setKordensucursal(Long kordensucursal) {
		this.kordensucursal = kordensucursal;
	}

	public String getClaveLabcore() {
		return claveLabcore;
	}

	public void setClaveLabcore(String claveLabcore) {
		this.claveLabcore = claveLabcore;
	}

	public Integer getCmarca() {
		return cmarca;
	}

	public void setCmarca(Integer cmarca) {
		this.cmarca = cmarca;
	}

	public Long getCestadoRegistro() {
		return cestadoRegistro;
	}

	public void setCestadoRegistro(Long cestadoRegistro) {
		this.cestadoRegistro = cestadoRegistro;
	}

	public Boolean getbPatcore() {
		return bPatcore;
	}

	public void setbPatcore(Boolean bPatcore) {
		this.bPatcore = bPatcore;
	}

	public Boolean getbFuji() {
		return bFuji;
	}

	public void setbFuji(Boolean bFuji) {
		this.bFuji = bFuji;
	}

	public Long getCsucursal() {
		return csucursal;
	}

	public void setCsucursal(Long csucursal) {
		this.csucursal = csucursal;
	}

	public List<MuestraFuji> getMuestras_fuji() {
		return this.muestras_fuji;
	}

	public void setMuestras_fuji(List<MuestraFuji> muestras_fuji) {
		this.muestras_fuji = muestras_fuji;
	}
	
	
}
