package es.dsw.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.dsw.daos.ArticuloDao;
import es.dsw.daos.TipoArticuloDao;
import es.dsw.models.Articulo;
import es.dsw.models.TipoArticulo;



//Lo importante de estos ejemplos es entienda como es aconsejable que las controladoras nunca resuelvan de forma directa las operaciones
//con la base de datos. Para ello, se ha introducido una capa de datos entre la capa de negocio y la de conexión a modo de objetos DAO.
@Controller
public class MainController {

	//Este mapeo se corresponde con el mapeo por defecto de la aplicación. En este caso se obtienen todos los tipos
	//de artículos para alimentar en la vista index.html un selector html que se utilizará para el ejemplo 3.
	@GetMapping(value={"/","/index"})
	public String index(Model objModel) {
		//Las controladoras nunca deben negociar directamente con la base de datos. En estos ejemplos se invoca al DAO de cada modelo
		//para obtener objetos con los datos pre-cargados de la base de datos.
		TipoArticuloDao objTipoArticuloDAO = new TipoArticuloDao();
		//Se solicita una lista de todos los objetos de tipo TipoArticulo
		ArrayList<TipoArticulo> objListaTipoArticulos = objTipoArticuloDAO.getAll();
		//Se carga en el modelo de la vista como atributo dicha lista.
		objModel.addAttribute("TiposDePeliculas", objListaTipoArticulos);
		
		//Si ha ocurrido un error, se informa a la vista.
		if (objTipoArticuloDAO.isFlagError()) {
			objModel.addAttribute("FlagError", true);
			objModel.addAttribute("MsgError", objTipoArticuloDAO.getMsgError());
		} else {
			objModel.addAttribute("FlagError", false);
			objModel.addAttribute("MsgError", "");
		}
		//Se delega en la vista
		return "index";
	} 
	
	//En este ejemplo se hace algo muy similar al ejemplo anterior, la diferencia es que la lista de objetos se utiliza
	//para delegar en la vista ejemplo1 que representará en una tabla los datos.
	@GetMapping(value= {"/ejemplo1"})
	public String ejemplo1(Model objModel) {
		
		TipoArticuloDao objTipoArticuloDAO = new TipoArticuloDao();
		ArrayList<TipoArticulo> objListaTipoArticulos = objTipoArticuloDAO.getAll();
		objModel.addAttribute("TiposDePeliculas", objListaTipoArticulos);
		return "ejemplo1";
	}
	
	//En este ejemplo se realiza lo mismo que en el ejemplo anterior pero sobre la tabla articulo.
	//Comprueba como el DAO proporciona un objeto Articulo que a su vez contiene un objeto TipoArticulo.
	@GetMapping(value= {"/ejemplo2"})
	public String ejemplo2(Model objModel) {
		
		ArticuloDao objArticuloDAO = new ArticuloDao();
		ArrayList<Articulo> objListaArticulos = objArticuloDAO.getAll();
		objModel.addAttribute("Articulos", objListaArticulos);
		return "ejemplo2";
	}
	
	//Ejemplo de recogida de datos e inserción en base de datos.
	@PostMapping(value= {"/ejemplo3"})
	public String ejemplo3(@RequestParam("codbarras") int codbarras, @RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, @RequestParam("tipo") int tipo, Model objModel) {
		
		//Se recogen los datos y se inicializa el objeto artículo
		Articulo objArticulo = new Articulo();
		objArticulo.setCodbarras(codbarras);
		objArticulo.setNombre(nombre);
		objArticulo.setDescripcion(descripcion);
		objArticulo.setIdtipoarticulo(tipo);
		
		//Se invoca al DAO de Articulo para insertar la información en base de datos
		ArticuloDao objArticuloDao = new ArticuloDao();
		objArticuloDao.setArticulo(objArticulo);
		
		//Se todo fue bién, marcamos en el modelo un atributo que indicará que la operación ha sido satisfactoria
		if (objArticulo.getIdarticulo() < 0) {
			objModel.addAttribute("ResultadoOperacion", false);
		} else {
			objModel.addAttribute("ResultadoOperacion", true);
		}

		return "ejemplo3";
	}
	
	//Este mapeo al ser invocado desata una eliminación de todos los registros de la tabla artículo.
	@GetMapping(value= {"/ejemplo4"})
	public String ejemplo4(Model objModel) {
		
		ArticuloDao objArticuloDao = new ArticuloDao();
		int NumRegistrosEliminados = objArticuloDao.deleteAll();
		
		if (NumRegistrosEliminados < 1) {
			objModel.addAttribute("ResultadoOperacion", false);
		} else {
			objModel.addAttribute("ResultadoOperacion", true);
		}
		
		objModel.addAttribute("NumRegistros", NumRegistrosEliminados);
		
		return "ejemplo4";
	}
}
