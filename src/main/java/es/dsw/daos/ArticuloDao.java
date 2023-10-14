package es.dsw.daos;

import java.sql.ResultSet;
import java.util.ArrayList;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.Articulo;
import es.dsw.models.TipoArticulo;

//Definición de la clase DAO relacionada con la clase del modelo Articulo. Este DAO resuelve la complejidad del acceso a datos
//devolviendo objetos de tipo Articulo o realizando operaciones de modificación sobre la base de datos.
public class ArticuloDao {

	private boolean flagError;
	private String msgError;
	private MySqlConnection objConection;
	private boolean transaccionExterna;
	
	public ArticuloDao() {
		this.flagError = false;
		this.msgError = "";
		this.objConection = null;
		this.transaccionExterna = false;
	}
	
	//Sobrecarga preparada para recibir el objeto desde otro DAO. Se indica si se ha comenzado 
	//una transacción desde otro DAO, ya que si es así, no se debe cerrar la conexión.
	public ArticuloDao(MySqlConnection objConx, boolean transac) {
		this.flagError = false;
		this.msgError = "";
		this.objConection = objConx;
		this.transaccionExterna = transac;
	}
	
	//Método que obtiene todos los artículos. Notar que devuelve un array list de objetos de tipo artículo.
	public ArrayList<Articulo> getAll(){
		
		objConection = new MySqlConnection();
		ArrayList<Articulo> objTablaTipoArticulo = new ArrayList<Articulo>();
		try {
			  objConection.open();
					
			  if (!objConection.isError()){
				   ResultSet Result = objConection.executeSelect("SELECT * FROM articulo");
				   while (Result.next()) {
					   Articulo objArticulo = new Articulo();
					   objArticulo.setIdarticulo(Result.getInt("idarticulo_art"));
					   objArticulo.setCodbarras(Result.getInt("codbarras_art"));
					   objArticulo.setNombre(Result.getString("nombre_art"));
					   objArticulo.setDescripcion(Result.getString("descripcion_art"));
					   objArticulo.setIdtipoarticulo(Result.getInt("idtipoarticulo_art"));
					   objArticulo.setFinsercion(Result.getDate("finsercion_art"));
					   objArticulo.setFultmodf(Result.getDate("fultmodf_art"));
					   TipoArticuloDao objTipoArticuloDao = new TipoArticuloDao();
					   TipoArticulo objTipoArticulo = objTipoArticuloDao.getOnce(Result.getInt("idtipoarticulo_art"));
					   objArticulo.setTipoArticulo(objTipoArticulo);
					   objTablaTipoArticulo.add(objArticulo); 
				   }
				
				} else {
						this.flagError = true;
						this.msgError = "Error en getAll. El objeto clsConectionMySql informa error al abrir conexión. +Info: " + objConection.msgError();
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
	
	//Método que dado un objeto de tipo Articulo inserta en base de datos un registro en la tabla artículo con sus datos. Además actualiza
	//las claves primarias en los atributos del objeto pasado por parámetro.
	public int setArticulo(Articulo objArticulo){
		
		objConection = new MySqlConnection();
		int IdArticulo = -1;
		try {
				objConection.open();
				if (!objConection.isError()){
					String SQL = "INSERT INTO stockdb.articulo "
							         + "(codbarras_art,"
							         + " nombre_art,"
							         + " descripcion_art,"
							         + " idtipoarticulo_art,"
							         + " finsercion_art,"
							         + " fultmodf_art) "
							   + "VALUES "
							   + "("+objArticulo.getCodbarras()+","
							   + " '"+objArticulo.getNombre()+"',"
							   + " '"+objArticulo.getDescripcion()+"',"
							   + " "+objArticulo.getIdtipoarticulo()+","
							   + " CURRENT_TIMESTAMP,"
							   + " CURRENT_TIMESTAMP)";
						
					ResultSet Result = objConection.executeInsert(SQL);
					if ((Result != null) && (Result.next())) {
						IdArticulo=Result.getInt(1);
					}
				} else {
					    this.flagError = true;
					    this.msgError = "Error en setArticulo. El objeto clsConectionMySql informa error al abrir conexión. +Info: " + objConection.msgError();
				   }
	    	} catch (Exception ex) {
				this.flagError = true;
				this.msgError = "Error en setArticulo. +Info: " + ex.getMessage();
	    	} finally {
	    		if (!this.transaccionExterna)
	    			objConection.close();
	    	}
		
		objArticulo.setIdarticulo(IdArticulo);
		return IdArticulo;
	}
	
	//Método que elimina todos los registros de la tabla articulo.
	//Se ejecuta el borrado en modo Transacción.
	public int deleteAll() {
		int NumRowsDelete = 0;
		//Aunque solo se ejecutará una operación en bbdd, se ha desactivado el autocomit (MySqlConnection(false)) para ejecutar las operaciones
		//en modo transacción. 
		//RECUERDA: Si en este código se ejecutara más de una operación, estariamos hablando de
		//una transacción. Al desactivar el autocomit, debemos controlar esta situación.
		objConection = new MySqlConnection(false);
		try {
			  objConection.open();
			  if (!objConection.isError()){
				  String SQL = "DELETE FROM stockdb.articulo";
				  NumRowsDelete = objConection.executeUpdateOrDelete(SQL);
				  //Confirmamos la transacción en base de datos. Ya no hay vuelta atrás. Los commit se pueden ejecutar varias veces seguidas.
				  objConection.commit();
			  } else {
				    this.flagError = true;
				    this.msgError = "Error en deleteAll. El objeto clsConectionMySql informa error al abrir conexión. +Info: " + objConection.msgError();
			   }
		    } catch (Exception ex) {
			       this.flagError = true;
			       this.msgError = "Error en deleteAll. +Info: " + ex.getMessage();
    	    } finally {
    	    	   if (this.flagError) {
    	    		   //Si se detecta una bandera de error, entonces forzamos rollback. Al igual que el commit, no pasa
    	    		   //nada si se ejecuta varias veces. Eso sí, la ejecución de un rollback anula TODA la transacción.
    	    		   objConection.rollback();
    	    	   }
    	    	   if (!this.transaccionExterna)
    	    		   objConection.close();
    	    }
	
	    return NumRowsDelete;
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
