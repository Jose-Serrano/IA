package practica.inferencia.avanzado;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jeops.engine.KnowledgeBase;
import practica.json.LectorJSON;
import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;

/**
 * Clase creada como base para la parte 1 de la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class MainClass {

	public static void main(java.lang.String[] args) throws IOException {
	
		/**
		 * No se permite modificar el código desde aquí. Salvo el valor de printDebug o problemPath
		 */
		
		System.out.println("--------------------------------------------------------");
		System.out.println("********** PRACTICA IA 19-20 UC3M COLMENAREJO **********");
		System.out.println("************ SOLUCION INFERENCIA - AVANZADO ************");
		System.out.println("--------------------------------------------------------");
        
		// Se define el nivel de debug a utilizar: Por argumentos el segundo parámetro
		int printDebug; // Nivel de debug. Permite elegir la cantidad de mensajes a imprimir
		if (args.length > 1) printDebug =  Integer.parseInt(args[1]);
		else printDebug = 1; // Definir aquí el valor
				
		//----------------------------- Se carga el problema -----------------------------//
		String problemPath = "problema.json"; // Problema en la misma ruta del paquete
		InputStream isJSON;
		// Si hay argumentos, se busca un fichero por parámetro. NO MODIFICAR
		if (args.length > 0 && !args[0].equals("")) isJSON = new FileInputStream(args[0]);
		else isJSON = LectorJSON.class.getResourceAsStream(problemPath); // Se busca en el path de LectorJSON dicho fichero
		LectorJSON lectorJSON = new LectorJSON();
		lectorJSON.readJSON(isJSON);
		ArrayList<Herramienta> readedHerramientas = lectorJSON.getHerramientas();
		ArrayList<Trabajador>  readedTrabajadores = lectorJSON.getTrabajadores();
		ArrayList<Tarea>       readedTareas       = lectorJSON.getTareas();

		/**
		 * No se permite modificar el código hasta aquí. Salvo el valor de printDebug o problemPath
		 */
		
		//----------------------------- Se preparan los objetos a utilizar en esta solución básica -----------------------------//
		// Se pueden añadir variables extra iterando sobre cada array y añadiendo un set en cada objeto		
		// Herramientas
		ArrayList<Herramienta> herramientas = readedHerramientas;
		// Trabajadores
		ArrayList<Trabajador>  trabajadores = readedTrabajadores;
		// Tareas
		ArrayList<Tarea> tareas = readedTareas;
		
		/**
		 * No se permite modificar el código desde aquí
		 */
		
		//----------------------------- Se crean los inicializan los objetos para ejecutar la solución -----------------------------//
		// Generación del motor de inferencia. Se le introduce la dirección a las reglas y se indica un orden para las reglas (ordenadas por prioridad en el fichero. No se puede modificar)
		InputStream isRules = MainClass.class.getResourceAsStream("reglas.rules"); // Se busca en el path de MainClass dicho fichero
		KnowledgeBase kb = new KnowledgeBase(isRules, new jeops.engine.PriorityRuleSorter(), printDebug);
		
		/**
		 * No se permite modificar el código hasta aquí
		 */
		
		//---------------------- Introducir los objetos en la base de hechos para el problema básico ---------------------- //
		for (int i = 0; i < herramientas.size(); i++) kb.join(herramientas.get(i));
		for (int i = 0; i < trabajadores.size(); i++) kb.join(trabajadores.get(i));
		for (int i = 0; i < tareas.size(); i++) kb.join(tareas.get(i));

		// Impresión del estado final del problema		
		System.out.println("--------------------------------------------------------");
		System.out.println("****************** COMIENZO EJECUCION ******************");
		System.out.println("--------------------------------------------------------");
		printState(herramientas, trabajadores, tareas);

		// Ejecución del motor de inferencia con el problema
		double executionTime = kb.run();

		// Impresión del estado final del problema		
		System.out.println("--------------------------------------------------------");
		System.out.println("******************** FIN EJECUCION *********************");
		System.out.println("--------------------------------------------------------");
		printState(herramientas, trabajadores, tareas);
		
		// Impresión de las métricas definidas
		System.out.println("------------------------ METRICAS -----------------------");
		printMetrics(executionTime, herramientas, trabajadores, tareas);
	}
	
	/**
	 * Se imprime el estado del problema en el instante actual
	 * MODIFICAR
	 * @param herramientas
	 * @param trabajadores
	 * @param tareas
	 */
	public static void printState(ArrayList<Herramienta> herramientas, ArrayList<Trabajador> trabajadores, ArrayList<Tarea> tareas) {
		System.out.println("************** IMPRESION DEL ESTADO **************");
	}

	/**
	 * Se generan las métricas implementadas y se imprimen sus resultados
	 * MODIFICAR
	 * @param herramientas
	 * @param trabajadores
	 * @param tareas
	 */
	public static void printMetrics(double executionTime, ArrayList<Herramienta> herramientas, ArrayList<Trabajador> trabajadores, ArrayList<Tarea> tareas) {
		System.out.println("************** IMPRESION DE METRICAS **************");
		System.out.println("La ejecución ha tardado: "+executionTime +" segundos");
	}

}
