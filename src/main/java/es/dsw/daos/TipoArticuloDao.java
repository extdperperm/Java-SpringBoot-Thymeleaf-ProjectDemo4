package es.dsw.daos;

import java.sql.ResultSet;
import java.util.ArrayList;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.TipoArticulo;

//Definición de la clase DAO relacionada con la clase del modelo TipoArticulo. Este DAO resuelve la complejidad del acceso a datos
//devolviendo objetos de tipo TipoArticulo.
public class TipoArticuloDao {

	private boolean flagError;
	private String msgError;
	private MySqlConnection objConection;
	private boolean transaccionExterna;

	public TipoArticuloDao() {
		this.flagError = false;
		this.msgError = "";
		this.objConection = null;
		this.transaccionExterna = false;
	}
	
	//Sobrecarga preparada para recibir el objeto desde otro DAO. Se indica si se ha comenzado 
	//una transacción desde otro DAO, ya que si es así, no se debe cerrar la conexión.
	public TipoArticuloDao(MySqlConnection objConx, boolean transac) {
		this.flagError = false;
		this.msgError = "";
		this.objConection = objConx;
		this.transaccionExterna = transac;
	}
	
	//Devuelve un array list de objetos de tipo TipoArticulo. En este caso devuelve todos los registros.
	public ArrayList<TipoArticulo> getAll(){
		
		objConection = new MySqlConnection();
		ArrayList<TipoArticulo> objTablaTipoArticulo = new ArrayList<TipoArticulo>();
		try {
			  objConection.open();
					
			  if (!objConection.isError()){
				   ResultSet Result = objConection.executeSelect("SELECT * FROM tipoarticulo");
				   while (Result.next()) {
					   TipoArticulo objTipoArticulo = new TipoArticulo();
					   objTipoArticulo.setIdTipoArticulo(Result.getInt("idtipoarticulo_tar"));
					   objTipoArticulo.setDescripcion(Result.getString("descripcion_tar"));
					   objTipoArticulo.setFUltmodf(Result.getString("fultmodf_tar"));
					   objTipoArticulo.setFInsercion(Result.getString("finsercion_tar"));
					   objTablaTipoArticulo.add(objTipoArticulo);
				   }
				
				} else {
						this.flagError = true;
						this.msgError = "Error en getAll. El objeto MySqlConnection informa error al abrir conexión. +Info: " + objConection.msgError();
					   }
		    } catch (Exception ex) {
					this.flagError = true;
					this.msgError = "Error en getAll. +Info: " + ex.getMessage();
			} finally {
				if (!this.transaccionExterna)
					objConection.close();
			}
		
		
		
		return objTablaTipoArticulo;
	}
	
	//Dado un Id correspondiente a la clave primaria de la tabla TipoArticulo devuelve un objeto con los datos de dicho registro.
	public TipoArticulo getOnce(int IdTipoArticulo){
		
		objConection = new MySqlConnection();
		TipoArticulo objTipoArticulo = null;
		
		try {
			  objConection.open();
					
			  if (!objConection.isError()){
				   ResultSet Result = objConection.executeSelect("SELECT * FROM tipoarticulo WHERE idtipoarticulo_tar = "+IdTipoArticulo);
				   while (Result.next()) {
					   objTipoArticulo = new TipoArticulo();
					   objTipoArticulo.setIdTipoArticulo(Result.getInt("idtipoarticulo_tar"));
					   objTipoArticulo.setDescripcion(Result.getString("descripcion_tar"));
					   objTipoArticulo.setFUltmodf(Result.getString("fultmodf_tar"));
					   objTipoArticulo.setFInsercion(Result.getString("finsercion_tar"));
				   }
				
				} else {
						this.flagError = true;
						this.msgError = "Error en getOnce. El objeto MySqlConnection informa error al abrir conexión. +Info: " + objConection.msgError();
					   }
		    } catch (Exception ex) {
					this.flagError = true;
					this.msgError = "Error en getOnce. +Info: " + ex.getMessage();
			} finally {
				if (!this.transaccionExterna)
					objConection.close();
			}
		
		
		
		return objTipoArticulo;
	}

	//Devuelve la bandera de error si se desea comprobar desde la capa de negocio si ha ocurrido algún problema.
	public boolean isFlagError() {
		return flagError;
	}

	//Método que devuelve la descripción del error.
	public String getMsgError() {
		return msgError;
	}
}
