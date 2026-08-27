package es.dsw.models;

import java.util.Date;

//Definición de la clase correspondiente al modelo Artículo
public class Articulo {
	private int idarticulo;
	private int codbarras;
	private String nombre;
	private String descripcion;
	private int idtipoarticulo;
	private TipoArticulo TipoArticulo;
	private Date finsercion;
	private Date fultmodf;
	
	public Articulo() {}
	
	public Articulo(int _idarticulo, int _codbarras, String _nombre, String _descripcion, 
			         int _idtipoarticulo, TipoArticulo _TipoArticulo, Date _finsercion, Date fultmodf_art) {
		this.idarticulo = _idarticulo;
		this.codbarras = _codbarras;
		this.nombre = _nombre;
		this.descripcion = _descripcion;
		this.idtipoarticulo = _idtipoarticulo;
		this.TipoArticulo = _TipoArticulo;
		this.finsercion = _finsercion;
		this.fultmodf = fultmodf_art;
	}

	public TipoArticulo getTipoArticulo() {
		return this.TipoArticulo;
	}

	public void setTipoArticulo(TipoArticulo _tipoArticulo) {
		this.TipoArticulo = _tipoArticulo;
	}

	public int getIdarticulo() {
		return idarticulo;
	}

	public void setIdarticulo(int idarticulo) {
		this.idarticulo = idarticulo;
	}

	public int getCodbarras() {
		return codbarras;
	}

	public void setCodbarras(int codbarras) {
		this.codbarras = codbarras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdtipoarticulo() {
		return idtipoarticulo;
	}

	public void setIdtipoarticulo(int idtipoarticulo) {
		this.idtipoarticulo = idtipoarticulo;
	}

	public String getFinsercion() {
		return finsercion.toString();
	} 

	public void setFinsercion(Date finsercion) {
		this.finsercion = finsercion;
	}

	public String getFultmodf() {
		return fultmodf.toString();
	}

	public void setFultmodf(Date fultmodf) {
		this.fultmodf = fultmodf;
	}


}
