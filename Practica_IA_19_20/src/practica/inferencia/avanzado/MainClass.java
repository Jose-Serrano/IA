package practica.inferencia.avanzado;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
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
		System.out.println("************* SOLUCION INFERENCIA - BASICO *************");
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
		
		
		  //--------------------------------------------------------------------//
		 //----------------------PRUEBA PARA COMPROBAR EL CAMINO---------------//
		//--------------------------------------------------------------------//
		//Primero situamos que areas son adyacentes.
		
		
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
		
		//Para comprobar el estado incial de los elementos
		Tarea warehouse = new Tarea("almacen", "A", 0);
		tareas.add(warehouse);
		
		
		
		//Ponemos las adyacentes a cada una de las tareas

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
			
			System.out.print(tarea.getArea()+" "+tarea.getTipo()+" "+tarea.getUnidades()+" "+tarea.getState()+" tareas adyacentes:");
			for (Tarea tarea2 : tarea.getAdyacentTask()) {
				System.out.print(" "+tarea2.getArea());
			}
			System.out.println();
		}
		//System.out.println(trabajadores.get(0).walkTime(tareas.get(3), tareas.get(1)));
		//System.out.println(trabajadores.get(0).taskTime(tareas.get(1)));
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
		
		//Ajustamos las areas de los trabajadores definitivas
		for (Trabajador worker : trabajadores) {
			System.out.print("El trabajador "+worker.getNombre()+" esta en: ");
			for (Tarea tarea : worker.getCompletedTasks()) {
				System.out.print(" "+tarea.getArea()+" haciendo "+tarea.getTipo());
			}
			System.out.println(" con un total de horas de: "+worker.getHours());
		}
		//Comprobacion final
		for (Tarea tarea : readedTareas) {
			if (tarea.getState()) {
				System.out.println(tarea.getArea()+" "+tarea.getTipo()+" "+tarea.getUnidades()+" "+tarea.getState());
			}
		}
		
		for (Herramienta herramienta : herramientas) {
			System.out.println(herramienta.getTrabajo());
		}
		
		/* Prueba de que se elimina una tarea
		Trabajador tr = trabajadores.get(2);
		Tarea ta = null;
		for (Tarea tarea2 : tareas) {
			if (tarea2.getTipo().equalsIgnoreCase("podar")) {
				ta = tarea2;
			}
		}
		System.out.println("vamos a eliminar la tarea "+ta.getArea()+" que es "+ta.getTipo()+" del trabajador "+tr.getNombre());
		tr.deleteTarea(ta);
		System.out.println(tr.getHours());
		FUNCIONA */
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
