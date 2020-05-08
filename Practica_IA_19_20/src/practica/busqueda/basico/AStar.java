package practica.busqueda.basico;

import java.util.ArrayList;
import java.util.List;

import practica.busqueda.basico.Node;
import practica.busqueda.basico.OpenList;
import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;

/**
 * Clase creada como base para la parte 2 de la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 * 
 */

public class AStar {

	int printDebug; // 0: nada, 1: información básica, 2: información completa

	private OpenList openList = new OpenList();						// Lista de nodos por explorar
	private ArrayList<Node> closedList = new ArrayList<Node>();		// Lista de nodos explorados
	private Node initialNode;										// Nodo inicial del problema
	private Node goalNode;											// Nodo meta del problema
	private boolean findGoal;										// Se ha encontrado la meta
	private Tarea WHAREHOUSE;
	/**
	 * Insertamos en la lista de nodos abiertos los nodos según las acciones que se pueden realizar en este instante
	 * MODIFICAR
	 * @param currentNode - el nodo actual
	 */
	private void addAdjacentNodes(Node currentNode) {
		// MODIFICAR para insertar las acciones específicas del problema
		//Aqui expandimos el arbol para observar los siguientes nodos disponibles para seleccionar que nodo debedemos de dirigirnos.
		ArrayList<Trabajador> trabajadores  = currentNode.getTrabajadores();
		ArrayList<Herramienta> herramientas = currentNode.getHerramientas();
		ArrayList<Tarea> tareas             = currentNode.getTareas();
		ArrayList<Tarea> newTasks 			= new ArrayList<Tarea>();
		
//		generateNodes(0, tareas, trabajadores, herramientas, currentNode);
		
		for (Tarea tarea : tareas) {
			//Generamos cada uno de los nuevos nodos
			if (tarea.getUnidades() > 0) {
				
				newTasks = createCopyTasks(tareas, tarea);
				
				Node newNode = new Node(currentNode, herramientas, trabajadores, newTasks);
				
				//Situamos donde nos encontramos
				newNode.setWorkingAt(tarea);
				
				//Ajustamos la evaluacion del nodo
				adjustNodeEvaluation(newNode, currentNode);
				
				//Añadimos el nodo a la lista abierta para explorarlo
				openList.insertAtEvaluation(newNode);
				
			}			
		}
		
	}
	
	//Metodo de recursión para generar TODOS los nodos posibles con sus trabajadores (AVANZADO)
	private void generateNodes(int numWorker, ArrayList<Tarea> tasks, ArrayList<Trabajador> workers, ArrayList<Herramienta> tools, Node currentNode) {
		if (numWorker == workers.size()) {
			//generamos el nuevo nodo con newNode y lo añadimos a la lista
			Node newNode = new Node(currentNode, tools, workers, tasks);
			//Vamos a comprobar la informacion del nuevo nodo:
//			for (Tarea task : newNode.getTareas()) {
//				if (task.getUnidades() > 0) {
//					String name = "NONE";
//					if (task.getWorker() != null) {
//						name = task.getWorker().getNombre();
//					}
//					System.out.println(task.getArea()+" "+name+" "+task.getUnidades());
//				}
//			}
			//Finalziamos la recursión.
			return;
		}
		for (Tarea tarea : tasks) {
			if (!tarea.getState() && tarea.getUnidades() > 0) {//Comprobamos que la tarea no tiene ningun trabajador (no está siendo realizada).
				tarea.assignWorker(workers.get(numWorker));
				generateNodes(numWorker+1, new ArrayList<Tarea>(tasks), workers, tools, currentNode);
				tarea.retractWorker();
			}
		}
		//Cuando ya hemos terminado de ajustar todas las tareas
		return;
	}
	
	private ArrayList<Tarea> cloneTask(ArrayList<Tarea> tasks){
		ArrayList<Tarea> toReturn = new ArrayList<Tarea>(tasks.size());
		for (Tarea tarea : tasks) {
			Tarea newTask = tarea.clone();
			toReturn.add(newTask);
		}
		return toReturn;
	}
	
	private ArrayList<Tarea> createCopyTasks(ArrayList<Tarea> tasks, Tarea oldTask){
		ArrayList<Tarea> toReturn = new ArrayList<Tarea>();
		for (Tarea task : tasks) {
			Tarea newTask = new Tarea(task.getTipo(), task.getArea(), task.getUnidades());
			if(newTask.equals(oldTask))
				newTask.resetTask();
			toReturn.add(newTask);
		}
		return toReturn;
	}
	
	private void adjustNodeEvaluation(Node node, Node oldNode) {
		Trabajador test = new Trabajador("Antonio", 5, 5, 5);
		if(test.getTool() == null || !test.getTool().getTrabajo().equalsIgnoreCase(node.getWorkingAt().getTipo())) {
			//TODO ponemos una herramienta a mano QUITARLO
			test.setTool(node.getHerramientas().get(0));
			
			//Tenemos que sumarle el tiempo de ir al almacén
			test.walk_WhareHouse_Task(oldNode.getWorkingAt(), WHAREHOUSE, node.getWorkingAt());
		}else {
			test.walkTime(oldNode.getWorkingAt(), node.getWorkingAt());
		}
		
		double time = test.getWorkingTime();
		time += test.taskTime(node.getWorkingAt());
		
		node.setCoste(time);
		node.computeHeuristic(goalNode);
		node.computeEvaluation();
	}
	
	private void giveAtool(Trabajador worker) {
		
	}
	
	/**
	 * Implementación de A estrella
	 */
	public double Algorithm() {
		System.out.println("Llamamos al algoritmo astar");
		double initialTime = Double.parseDouble(""+System.currentTimeMillis()); // Para contar el tiempo de ejecución

		Node currentNode = null;
		System.out.println("El estado de la lista abierta (nodos por explorar) es: "+this.openList.isEmpty());
		while(!this.openList.isEmpty()) { 				// Recorremos la lista de nodos sin explorar
			currentNode = this.openList.pullFirst(); 	// Extraemos el primero (la lista esta ordenada segun la funcion de evaluación)
			if(checkNode(currentNode)) {				// Si el nodo ya se ha visitado CON UN COSTE MENOR (esta en la lista de explorados) lo ignoramos
				currentNode.printNodeData(printDebug);
				closedList.add(currentNode); 			// Añadimos dicho nodo a la lista de explorados
				System.out.println("Se parece a la meta? "+this.getGoalNode().equals(currentNode));
				if(this.getGoalNode().equals(currentNode)) {	// Si es el nodo meta hemos acabado y no hace falta continuar
					System.out.println("Hemos encontrado la meta");
					this.setGoalNode(currentNode);
					this.setFindGoal(true);
					break;
				}
				this.addAdjacentNodes(currentNode); 	// Expandimos el nodo segun las acciones posibles
			}
		}
		
		// Para contar el tiempo de ejecución
		double fin    = Double.parseDouble(""+System.currentTimeMillis());
		double tiempo = (fin - initialTime) / 1000;
		return tiempo;
	}


	/**
	 * Constructor del algoritmo, obtiene el nodo de inicio y el nodo meta
	 * NO MODIFICAR
	 * @param initialNode
	 * @param goalNode
	 */
	public AStar(int printDebug, Node initialNode, Node goalNode) { 
		this.printDebug = printDebug;
		this.setInitialNode(initialNode);
		this.setGoalNode(goalNode);
		this.setFindGoal(false); 					// No se ha llegado al nodo meta
		this.setWhareHouse(initialNode.getWorkingAt());
		// Introducir heurísticas y costes para el nodo inicial. El nodo meta solo tiene heurística
//		System.out.print("Heursitica Nodo inicial: ------- ");
		initialNode.computeHeuristic(goalNode);	// Coste esperado por la heurística para llegar al nodo final desde el inicial
		initialNode.setCoste(0);					// el nodo inicial tiene coste cero
		initialNode.computeEvaluation();			// coste + heurística
//		System.out.print("Heursitica Nodo final: ------- ");
		goalNode.computeHeuristic(goalNode);		// Debe ser 0, ya es el nodo final

		// Genera la lista de nodos explorados y sin explorar
		this.closedList = new ArrayList<Node>();
		this.openList   = new OpenList();
		this.openList.insertAtEvaluation(initialNode); // Añadimos a la lista de nodos sin explorar el nodo inicial
	}


	/**
	 * Comprobación de si el nodo ya se ha explorado
	 * NO MODIFICAR
	 * @param currentNode
	 * @return
	 */
	private boolean checkNode(Node currentNode) {
		boolean expandirNodo = true;
		for (Node node : this.closedList) { // Se observa si el nodo está en la lista de cerrados
			if(currentNode.equals(node)) {	// Comprueba si la información del nodo es igual
				expandirNodo = false;
				break;
			}
		}
		return expandirNodo;				// false en el caso de que el nodo se haya visitado, indicando que no hay que expandirlo
	}


	/**
	 * Método para calcular el camino desde el nodo Inicial hasta el nodo actual
	 * NO MODIFICAR
	 * @param currentNode
	 * @return lista de nodos ordenada, desde el primer nodo al último
	 */
	public List<Node> getPath(Node currentNode) {
		List<Node> path = new ArrayList<Node>();	
		path.add(currentNode);	
		Node parent;
		while ((parent = currentNode.getParent()) != null) {	// Desde el nodo actual, se busca el nodo padre y se insertan 
			path.add(0, parent);								//  dentro de la lista de manera inversa
			currentNode = parent;
		}
		return path;
	}


	/**** Getters y Setters ****/
	/**
	 * MODIFICAR y/o AÑADIR si se considera necesario. No es imprescindible, solo si se considera que puede ayudar a la implementación
	 */
	public Node getInitialNode() {
		return initialNode;
	}
	public void setInitialNode(Node initialNode) {
		this.initialNode = initialNode;
	}
	public boolean isFindGoal() {
		return findGoal;
	}
	public void setFindGoal(boolean findGoal) {
		this.findGoal = findGoal;
	}
	public Node getGoalNode() {
		return goalNode;
	}
	public void setGoalNode(Node goalNode) {
		this.goalNode = goalNode;
	}
	
	public void setWhareHouse(Tarea wharehouse) {
		this.WHAREHOUSE = wharehouse;
	}

}
