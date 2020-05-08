package practica.busqueda.basico;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import practica.busqueda.basico.AStar;
import practica.busqueda.basico.Node;
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

	/**
	 * Ejecuta la solución del problema avanzado con un algoritmo de búsqueda
	 * MODIFICAR
	 * @param args[0]: Ruta del fichero a ejecutar
	 * @param args[1]: Número de debug a utilizar
	 */
	public static void main(java.lang.String[] args) throws IOException {

		/**
		 * No se permite modificar el código desde aquí. Salvo el valor de printDebug o problemPath
		 */ 

		System.out.println("--------------------------------------------------------");
		System.out.println("********** PRACTICA IA 19-20 UC3M COLMENAREJO **********");
		System.out.println("************** SOLUCION BUSQUEDA - BASICO **************");
		System.out.println("--------------------------------------------------------");

		// Se define el nivel de debug a utilizar: Por argumentos el segundo parámetro.
		int printDebug; // Nivel de debug. Permite elegir la cantidad de mensajes a imprimir
		if (args.length > 1) printDebug =  Integer.parseInt(args[1]);
		else printDebug = 0; // Definir aquí el valor

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


		// Herramientas
		ArrayList<Herramienta> herramientas = readedHerramientas;
		// Trabajadores
		ArrayList<Trabajador>  trabajadores = readedTrabajadores;
		// Tareas
		ArrayList<Tarea> tareas = readedTareas;

		Tarea warehouse = new Tarea(null, "A", 0);
		tareas.add(warehouse);
		
		for (Tarea tarea : tareas) {
			for (Tarea tarea2 : tareas) {
				if (tarea.getArea().equalsIgnoreCase("U")){
					if ("J1BJ2".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}
				}
				if(tarea.getArea().equalsIgnoreCase("J1")) {
					if ("UBJ2C1".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}
				}
				if(tarea.getArea().equalsIgnoreCase("J2")) {
					if ("AUJ1C1C2".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}					
				}
				if(tarea.getArea().equalsIgnoreCase("B")) {
					if ("J1U".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}					
				}
				if(tarea.getArea().equalsIgnoreCase("A")) {
					if ("J2C2J3".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}					
				}
				if(tarea.getArea().equalsIgnoreCase("J3")) {
					if ("RAC2".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}					
				}
				if(tarea.getArea().equalsIgnoreCase("C1")) {
					if ("C2J2J1".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}					
				}
				if(tarea.getArea().equalsIgnoreCase("C2")) {
					if ("AJ2C1J3".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}					
				}
				if(tarea.getArea().equalsIgnoreCase("R")) {
					if ("J3".indexOf(tarea2.getArea()) != -1) {
						tarea.addAdyacentTask(tarea2);
					}					
				}
			}
		}
		
		for (Tarea tarea : tareas) {
			System.out.println(tarea.getArea()+" "+tarea.getTipo()+" "+tarea.getUnidades());
		}
		for (Trabajador worker : trabajadores) {
			System.out.println(worker.getNombre());
		}

		//-------- Se crean los inicializan los objetos para ejecutar la solución --------//
		Node initialNode = new Node(null, herramientas, trabajadores, tareas);
		Node goalNode = createEndNode(initialNode);
		initialNode.setWorkingAt(warehouse);
		goalNode.setWorkingAt(warehouse);
		AStar aStar = new AStar(printDebug, initialNode, goalNode); // Se inicializa el A-Estrella

		//----------------------------- Ejecución del algoritmo ---------------------------//
		double executionTime = aStar.Algorithm();
		System.out.println("--------------------------------------------------------");
		System.out.println("******************** FIN EJECUCION *********************");
		System.out.println("--------------------------------------------------------");

		//---------------------------- Extracción de información ---------------------------//
		printFinalPath(aStar);
		System.out.println("------------------------ METRICAS -----------------------");
		printMetrics(aStar, executionTime);
	}

	/**
	 * Se generan las métricas implementadas y se imprimen sus resultados
	 * @param aStar
	 */
	public static void printFinalPath(AStar aStar) {
		if(aStar.isFindGoal()) System.out.println("****************** CAMINO ENCONTRADO *******************");
		else System.out.println("***************** CAMINO NO ENCONTRADO *****************");
		System.out.println("************** IMPRESION DEL CAMINO REALIZADO **************");

		// Genera el camino para llegar a la meta desde el nodo inicial
		List<Node> path = aStar.getPath(aStar.getGoalNode());
		for (Node node:path) {
			node.printNodeData(2);	// printDebug = 2
		}
		System.out.println();
	}

	/**
	 * Se generan las métricas implementadas y se imprimen sus resultados
	 * MODIFICAR
	 * @param aStar
	 * @param executionTime
	 */
	public static void printMetrics(AStar aStar, double executionTime) {
		System.out.println("************** IMPRESION DE METRICAS **************");
		System.out.println("La ejecución ha tardado: "+executionTime +" segundos");
	}
	
	private static Node createEndNode(Node initNode) {
		ArrayList<Trabajador> workers = initNode.getTrabajadores();
		ArrayList<Herramienta> tools = initNode.getHerramientas();
		ArrayList<Tarea> tasks = new ArrayList<Tarea>();
		for (Tarea task : initNode.getTareas()) {
			Tarea newTask = new Tarea(task.getTipo(), task.getArea(), task.getUnidades());
			newTask.resetTask();
			tasks.add(newTask);
		}
		return new Node(initNode, tools, workers, tasks);
	}

}
