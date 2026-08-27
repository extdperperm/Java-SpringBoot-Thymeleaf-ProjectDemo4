package es.dsw.models;

//Definición de la clase correspondiente al modelo TipoArtículo
public class TipoArticulo {
	
	private int IdTipoArticulo;
	private String Descripcion;
	private String FInsercion;
	private String FUltmodf;
	
	public TipoArticulo() {}
	
	public TipoArticulo(String descripcion, String fInsercion, String fUltmodf) {
		this.Descripcion = descripcion;
		this.FInsercion = fInsercion;
		this.FUltmodf = fUltmodf;
	}

	public int getIdTipoArticulo() {
		return this.IdTipoArticulo;
	}
	
	public void setIdTipoArticulo(int id) {
		this.IdTipoArticulo = id;
	}

	public String getDescripcion() {
		return this.Descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion;
	}

	public String getFInsercion() {
		return this.FInsercion;
	}

	public void setFInsercion(String fInsercion) {
		this.FInsercion = fInsercion;
	}

	public String getFUltmodf() {
		return this.FUltmodf;
	}

	public void setFUltmodf(String fUltmodf) {
		this.FUltmodf = fUltmodf;
	}
}
	
	