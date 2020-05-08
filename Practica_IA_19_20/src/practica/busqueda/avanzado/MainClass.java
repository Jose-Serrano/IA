package practica.busqueda.avanzado;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import practica.busqueda.avanzado.AStar;
import practica.busqueda.avanzado.Node;
import practica.json.LectorJSON;
import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;
import practica.objetos.taskDistance;

/**
 * Clase creada como base para la parte 2 de la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
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
		System.out.println("************* SOLUCION BUSQUEDA - AVANZADO *************");
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


		// Herramientas
		ArrayList<Herramienta> herramientas = readedHerramientas;
		// Trabajadores
		ArrayList<Trabajador>  trabajadores = readedTrabajadores;
		// Tareas
		ArrayList<Tarea> tareas = readedTareas;
		
		for (Herramienta tool : herramientas) {
			System.out.println("Herramienta: "+tool.getNombre()+" unidades "+tool.getCantidad()+" mejora de "+tool.getMejora());
		}
		
		Tarea warehouse = new Tarea(null, "A", 0);
		tareas.add(warehouse);
		
		//Situamos a mano la distancia de cada una de las tareas
		for (Tarea tarea : tareas) {
			if (tarea.getArea().equalsIgnoreCase("U")) {
				
				tarea.addDistance(new taskDistance("B", 1));
				tarea.addDistance(new taskDistance("J1", 1));
				tarea.addDistance(new taskDistance("J2", 1));
				tarea.addDistance(new taskDistance("C1", 2));
				tarea.addDistance(new taskDistance("C2", 2));
				tarea.addDistance(new taskDistance("A", 2));
				tarea.addDistance(new taskDistance("J3", 3));
				tarea.addDistance(new taskDistance("R", 4));
				
			}else if(tarea.getArea().equalsIgnoreCase("J1")) {
				
				tarea.addDistance(new taskDistance("B", 1));
				tarea.addDistance(new taskDistance("U", 1));
				tarea.addDistance(new taskDistance("J2", 1));
				tarea.addDistance(new taskDistance("C1", 1));
				tarea.addDistance(new taskDistance("C2", 2));
				tarea.addDistance(new taskDistance("A", 2));
				tarea.addDistance(new taskDistance("J3", 3));
				tarea.addDistance(new taskDistance("R", 4));
				
			}else if(tarea.getArea().equalsIgnoreCase("J2")) {
				
				tarea.addDistance(new taskDistance("B", 2));
				tarea.addDistance(new taskDistance("J1", 1));
				tarea.addDistance(new taskDistance("U", 1));
				tarea.addDistance(new taskDistance("C1", 1));
				tarea.addDistance(new taskDistance("C2", 1));
				tarea.addDistance(new taskDistance("A", 1));
				tarea.addDistance(new taskDistance("J3", 2));
				tarea.addDistance(new taskDistance("R", 3));
				
			}else if(tarea.getArea().equalsIgnoreCase("B")) {

				tarea.addDistance(new taskDistance("U", 1));
				tarea.addDistance(new taskDistance("J1", 1));
				tarea.addDistance(new taskDistance("J2", 2));
				tarea.addDistance(new taskDistance("C1", 2));
				tarea.addDistance(new taskDistance("C2", 3));
				tarea.addDistance(new taskDistance("A", 3));
				tarea.addDistance(new taskDistance("J3", 4));
				tarea.addDistance(new taskDistance("R", 5));
				
			}else if (tarea.getArea().equalsIgnoreCase("A")) {

				tarea.addDistance(new taskDistance("U", 2));
				tarea.addDistance(new taskDistance("J1", 2));
				tarea.addDistance(new taskDistance("J2", 1));
				tarea.addDistance(new taskDistance("C1", 2));
				tarea.addDistance(new taskDistance("C2", 1));
				tarea.addDistance(new taskDistance("B", 3));
				tarea.addDistance(new taskDistance("J3", 4));
				tarea.addDistance(new taskDistance("R", 2));
								
			}else if (tarea.getArea().equalsIgnoreCase("J3")) {

				tarea.addDistance(new taskDistance("U", 3));
				tarea.addDistance(new taskDistance("J1", 3));
				tarea.addDistance(new taskDistance("J2", 2));
				tarea.addDistance(new taskDistance("C1", 2));
				tarea.addDistance(new taskDistance("C2", 1));
				tarea.addDistance(new taskDistance("A", 1));
				tarea.addDistance(new taskDistance("B", 4));
				tarea.addDistance(new taskDistance("R", 1));
				
			}else if (tarea.getArea().equalsIgnoreCase("C1")) {

				tarea.addDistance(new taskDistance("U", 2));
				tarea.addDistance(new taskDistance("J1", 1));
				tarea.addDistance(new taskDistance("J2", 1));
				tarea.addDistance(new taskDistance("B", 2));
				tarea.addDistance(new taskDistance("C2", 1));
				tarea.addDistance(new taskDistance("A", 2));
				tarea.addDistance(new taskDistance("J3", 2));
				tarea.addDistance(new taskDistance("R", 3));
				
			}else if (tarea.getArea().equalsIgnoreCase("C2")) {

				tarea.addDistance(new taskDistance("U", 2));
				tarea.addDistance(new taskDistance("J1", 2));
				tarea.addDistance(new taskDistance("J2", 1));
				tarea.addDistance(new taskDistance("C1", 1));
				tarea.addDistance(new taskDistance("B", 3));
				tarea.addDistance(new taskDistance("A", 1));
				tarea.addDistance(new taskDistance("J3", 1));
				tarea.addDistance(new taskDistance("R", 2));
				
			}else if (tarea.getArea().equalsIgnoreCase("R")) {

				tarea.addDistance(new taskDistance("U", 4));
				tarea.addDistance(new taskDistance("J1", 4));
				tarea.addDistance(new taskDistance("J2", 3));
				tarea.addDistance(new taskDistance("C1", 3));
				tarea.addDistance(new taskDistance("C2", 2));
				tarea.addDistance(new taskDistance("A", 2));
				tarea.addDistance(new taskDistance("J3", 1));
				tarea.addDistance(new taskDistance("B", 5));
				
			}
		}
		
		//Prueba del funcionamiento correcto de la funion wal_timev2
//		Trabajador tonyStark = new Trabajador("Tony", 3, 1, 500);
//		tonyStark.setTool(herramientas.get(0));
//		System.out.println("Para ir de la tarea "+tareas.get(2).getArea()+" a la tarea "+tareas.get(0).getArea()+" se ha necesitado un tiemppo de:"+tonyStark.walk_timev2(tareas.get(2), tareas.get(0)));
		
		
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
