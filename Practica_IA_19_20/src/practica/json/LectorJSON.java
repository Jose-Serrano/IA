package practica.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;

/**
 * Clase creada como base para la parte 1 de la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 * CÓDIGO NO MODIFICABLE
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class LectorJSON {

	// DEFINICIÓN DE LAS LISTAS DE OBJETOS A LEER DEL JSON
	ArrayList<Herramienta> herramientas; 
	ArrayList<Trabajador>  trabajadores;
	ArrayList<Tarea>       tareas;

	// Cuando se llama al constructor, se crean unas listas vacías
	public LectorJSON() {
		this.herramientas = new ArrayList<Herramienta>();
		this.trabajadores = new ArrayList<Trabajador>();
		this.tareas       = new ArrayList<Tarea>();
	}

	// Getters y setters
	public ArrayList<Herramienta> getHerramientas() {
		return herramientas;
	}
	public void setHerramientas(ArrayList<Herramienta> herramientas) {
		this.herramientas = herramientas;
	}
	public ArrayList<Trabajador> getTrabajadores() {
		return trabajadores;
	}
	public void setTrabajadores(ArrayList<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}
	public ArrayList<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(ArrayList<Tarea> tareas) {
		this.tareas = tareas;
	}


	/**
	 * NO MODIFICAR
	 * Se lee el fichero recibido por parámetros y se añaden los objetos a las listas de objetos definidas
	 * @param inputStream
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void readJSON(InputStream inputStream) {
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream));

			JSONObject tareasJSON       = (JSONObject) jsonObject.get("tareas");
			JSONObject trabajadoresJSON = (JSONObject) jsonObject.get("trabajadores");
			JSONObject herramientasJSON = (JSONObject) jsonObject.get("herramientas");

			// Tareas
			Iterator iterator = tareasJSON.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JSONObject> pair = (Entry<String, JSONObject>) iterator.next();
				JSONObject tareaObject = pair.getValue();
				Long unidadesAux = (Long) tareaObject.get("unidades");
				tareas.add(new Tarea(
						(String)  tareaObject.get("tipo"), 
						(String)  tareaObject.get("area"),
						unidadesAux.intValue()
						) );
			}

			// Trabajadores
			iterator = trabajadoresJSON.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JSONObject> pair = (Entry<String, JSONObject>) iterator.next();
				JSONObject tareaObject = pair.getValue();
				Long podarAux = (Long) tareaObject.get("podar");
				Long limpiarAux = (Long) tareaObject.get("limpiar");
				Long repararAux = (Long) tareaObject.get("reparar");
				trabajadores.add(new Trabajador(
						(String) tareaObject.get("nombre"), 
						podarAux.intValue(), 
						limpiarAux.intValue(), 
						repararAux.intValue()
						) );
			}

			// Herramientas
			iterator = herramientasJSON.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JSONObject> pair = (Entry<String, JSONObject>) iterator.next();
				JSONObject herramientaObject = pair.getValue();
				Long mejoraAux = (Long) herramientaObject.get("mejora");
				Long cantidadAux = (Long) herramientaObject.get("cantidad");
				Long pesoAux = (Long) herramientaObject.get("peso");
				herramientas.add(new Herramienta(
						(String)  herramientaObject.get("nombre"), 
						(String)  herramientaObject.get("trabajo"),
						pesoAux.intValue(),
						mejoraAux.intValue(), 
						cantidadAux.intValue()
						) );
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}