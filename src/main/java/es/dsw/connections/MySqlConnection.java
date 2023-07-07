package es.dsw.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//[2] - CONSTRUCCIÓN DE LA CLASE CONECTORA

/*####################################################################################
 * 
 * NOMBRE: MySqlConnection
 * FECHA: 24/06/2023
 * AUTOR: Daniel Pérez
 * 
 * DESCRIPCIÓN: Clase de la capa CONEXION que centraliza toda la complejidad de conexión, acceso y modificación de
 * datos para una base de datos MySql.
 * ###################################################################################
 */

public class MySqlConnection {
	
	//Aunque en este ejemplo los datos de conexión están en código, siempre es recomendable que esta
	//parametrización figure en un fichero de propiedades.
	
	//Nombre del host
	private String host = "localhost";
	//Puerto
	private String puerto = "3306";
	//Nombre de la base de datos
	private String nameDB = "stockdb";
	//Usuario de conexión
	private String usuario = "root";
	//Contraseña
	private String password = "1234";
	
	//Atributo que indica si las operaciones se realizarán con autocomit. De lo contrario, en la capa de datos (DAO's) se
	//debe controlar las transacciones.
	private boolean autocomit;
	
	//Bandera de error
	private boolean flagError;
	//Mensaje de error
	private String msgError;
	
	//Objeto de conexión
	private Connection connection;
	

	//inicialización de parámetros por defecto
	private void _initialize() {
		this.flagError = false;
		this.msgError = "";
		this.connection = null;
	}
	
	//Inicialización de la bandera y mensaje de error
	private void _initializeError() {
		this.flagError = false;
		this.msgError = "";
	}
	
	//Constructor implícito
	public MySqlConnection()
	{
		this._initialize();
		this.autocomit = true;
	}
	
	public MySqlConnection(boolean _autocomit)
	{
		this._initialize();
		this.autocomit = _autocomit;
	}
	
	//Constructor que permite indicar esquema por defecto
	public MySqlConnection(String _nameDB)
	{
		this._initialize();
		this.nameDB = _nameDB;
		this.autocomit = true;
	}
	
	public MySqlConnection(String _nameDB, boolean _autocomit)
	{
		this._initialize();
		this.nameDB = _nameDB;
		this.autocomit = _autocomit;
	}
	
	//Constructor que permite conectar a cualquier base de datos MySql
	public MySqlConnection(String _host, String _puerto, String _nameDB, String _usuario, String _password)
	{
		this._initialize();
		this.host = _host;
		this.puerto = _puerto;
		this.nameDB = _nameDB;
		this.usuario = _usuario;
		this.password = _password;
		this.autocomit = true;
	}
	
	public MySqlConnection(String _host, String _puerto, String _nameDB, String _usuario, String _password, boolean _autocomit)
	{
		this._initialize();
		this.host = _host;
		this.puerto = _puerto;
		this.nameDB = _nameDB;
		this.usuario = _usuario;
		this.password = _password;
		this.autocomit = _autocomit;
	}
	

	//Método que abre la conexión y en caso de error, activa la bandera de error.
	public void open()
	{
		try
		{   //Se reinician las banderas de error.
			this._initializeError();
			//Si el objeto connection no es vacío o está cerrado se procede a abrirlo.
			if ((this.connection == null) || ((this.connection != null) && (this.connection.isClosed())))
			{
			  //Se carga el jdbc
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  //Se indican los datos de conexión y se intenta obtener el objeto abierto (esta instrucción es crítica)
			  this.connection = DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.puerto+"/"+this.nameDB, this.usuario, this.password);
			  //Se indica si se realizará autocomit (cada operación será independiente) o si se estará en modo de transacción (delega en la capa de datos el commit).
			  this.connection.setAutoCommit(this.autocomit);
			}
		}
		catch (ClassNotFoundException ex)
		{
			this.flagError = true;
			this.msgError = "Error al registrar el dricer. +Info: " + ex.getMessage();
		}
		catch (Exception ex)
		{
			this.flagError = true;
			this.msgError = "Error en Open. +Info: " + ex.getMessage();
		}
	}
	
	//Método que cierra la conexión si estaba abierta
	public void close()
	{
		try
		{   this._initializeError();
			if ((this.connection != null) && (!this.connection.isClosed()))
			   this.connection.close();
		}
		catch (SQLException ex)
		{
			this.flagError = true;
			this.msgError = "Error en close. +Info: " + ex.getMessage();
		}
		
	}
	
	//Método que retorna el resultado de cualquier consulta sql en un ResultSet
	public ResultSet executeSelect(String _sql)
	{
		try {   
			this._initializeError();
			if (this.connection != null) {
				if (!this.connection.isClosed()) {
					java.sql.Statement objStament = this.connection.createStatement();	
					ResultSet rs = objStament.executeQuery (_sql);
					return rs;
				}
				else {
					this.flagError = true;
					this.msgError = "Error en ExecuteSelect. +Info: Conexión cerrada.";
				}
			}
			else {
				this.flagError = true;
				this.msgError = "Error en ExecuteSelect. +Info: Conexión no inicializada.";
			}
		}
		catch (SQLException ex) {
			this.flagError = true;
			this.msgError = "Error en ExecuteSelect. +Info: " + ex.getMessage();
		}

		
		   try {
				 if ((this.flagError) && (!this.connection.getAutoCommit())) {
						this.connection.rollback();
				}
			} catch (SQLException ex) {
				this.flagError = true;
				this.msgError = "Error en intento de rollback en ExecuteSelect. +Info: " + ex.getMessage();
			}
			
		
		return null; 
	}
	
	//Método para ejecutar un insert. Devuelve la/s claves primarias generadas en un record set.
	public ResultSet executeInsert(String _sql) {
		
		try {   
			 this._initializeError();
			 if (this.connection != null) {
				if (!this.connection.isClosed()) {
					PreparedStatement objStament = this.connection.prepareStatement(_sql,Statement.RETURN_GENERATED_KEYS);	
					objStament.execute();
					ResultSet rs = objStament.getGeneratedKeys();
					return rs;
				}
				else {
					this.flagError = true;
					this.msgError = "Error en ExecuteQuery. +Info: Conexión cerrada.";
				}
			 }
			 else {
				this.flagError = true;
				this.msgError = "Error en ExecuteQuery. +Info: Conexión no inicializada.";
			}
		   }
		   catch (SQLException ex) {
			    this.flagError = true;
			    this.msgError = "Error en ExecuteQuery. +Info: " + ex.getMessage();
		   }
		
		   try {
				 if ((this.flagError) && (!this.connection.getAutoCommit())) {
						this.connection.rollback();
				}
			} catch (SQLException ex) {
				this.flagError = true;
				this.msgError = "Error en intento de rollback en ExecuteQuery. +Info: " + ex.getMessage();
			}
		
		return null; 
	}
	
	//Método para ejecutar un update o delete. Devuelve el número de registros afectados.
	public int executeUpdateOrDelete(String _sql) {
		int NumRows = 0;
		try {   
			 this._initializeError();
			 if (this.connection != null) {
				if (!this.connection.isClosed()) {
					PreparedStatement objStament = this.connection.prepareStatement(_sql);	
					NumRows = objStament.executeUpdate();
				}
				else {
					this.flagError = true;
					this.msgError = "Error en executeUpdateOrDelete. +Info: Conexión cerrada.";
				}
			 }
			 else {
				this.flagError = true;
				this.msgError = "Error en executeUpdateOrDelete. +Info: Conexión no inicializada.";
			}
		   }
		   catch (SQLException ex) {
			    this.flagError = true;
			    this.msgError = "Error en executeUpdateOrDelete. +Info: " + ex.getMessage();
		   }
		
		   try {
				 if ((this.flagError) && (!this.connection.getAutoCommit())) {
						this.connection.rollback();
				}
			} catch (SQLException ex) {
				this.flagError = true;
				this.msgError = "Error en intento de rollback en executeUpdateOrDelete. +Info: " + ex.getMessage();
			}
		
		return NumRows;
	}
	
	//Método que fuerza un commit. Solo se realizará si el autocomit está deshabilitado. (autocomit = false).
	public void commit()
	{
		try
		{   this._initializeError();
			if (!this.connection.getAutoCommit()) {
			   this.connection.commit();
			}
		}
		catch (SQLException ex)
		{
			this.flagError = true;
			this.msgError = "Error en commit. +Info: " + ex.getMessage();
		}
	}
	
	//Método que fuerza un rollback. Solo se realizará si el autocomit está deshabilitado. (autocomit = false).
	public void rollback()
	{
		try
		{   this._initializeError();
			if (!this.connection.getAutoCommit()) {
			   this.connection.rollback();
			}
		}
		catch (SQLException ex)
		{
			this.flagError = true;
			this.msgError = "Error en rollback. +Info: " + ex.getMessage();
		}
	}
	
	//Devuelve el valor de la bandera de error
	public boolean isError() {
		return this.flagError;
	}
	
	//Devuelve la descripción del error
	public String msgError() {
		return this.msgError;
	}

}
