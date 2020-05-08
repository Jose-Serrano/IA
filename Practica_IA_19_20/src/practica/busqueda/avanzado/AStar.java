package practica.busqueda.avanzado;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;
import practica.objetos.taskDistance;


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
		
		generateNodes(0, tareas, trabajadores, herramientas, currentNode);
		
	}
	
	//Metodo de recursividad para generar TODOS los nodos posibles con sus trabajadores
	private void generateNodes(int numWorker, ArrayList<Tarea> tasks, ArrayList<Trabajador> workers, ArrayList<Herramienta> tools, Node currentNode) {
		
		if (numWorker == workers.size()) {
			//generamos el nuevo nodo con newNode y lo añadimos a la lista
			Node newNode = new Node(currentNode, tools, workers, tasks);//Vamos a comprobar la informacion del nuevo nodo:
//			int i = 0;
//			for (Tarea task : newNode.getTareas()) {
//				
//				String name = "NONE";
//				if (task.getWorker() != null) {
//					name = task.getWorker().getNombre();
//				}
//				System.out.println(i+" "+task.getArea()+" "+name+" "+task.getUnidades());
//				i++;
//			}
			adjustNodeEvaluation(newNode, currentNode);
			openList.insertAtEvaluation(newNode);
			
			//Finalziamos la recursión.
			return;
		}
		for (Tarea tarea : tasks) {
			if (!tarea.getState() && tarea.getUnidades() > 0) {//Comprobamos que la tarea no tiene ningun trabajador (no está siendo realizada).
				//Tenemos que crear copias de cada uno de los elementos ya que son mutables.
				generateNodes(
						numWorker+1, 
						createCopyTasks(tasks, tarea, workers.get(numWorker)),
						createCopyWorkers(workers, tarea, workers.get(numWorker)),
						createCopyTools(tools),
						currentNode
						);
			}
		}
		//Cuando ya hemos terminado de ajustar todas las tareas
		return;
	}
	
	private ArrayList<Tarea> createCopyTasks(ArrayList<Tarea> tasks, Tarea oldTask, Trabajador worker){
		ArrayList<Tarea> toReturn = new ArrayList<Tarea>();
		for (Tarea task : tasks) {
			Tarea newTask = new Tarea(task.getTipo(), task.getArea(), task.getUnidades());
			if (newTask.equals(oldTask)) {
				newTask.assignWorker(worker);
			}else {
				newTask.assignWorker(task.getWorker());
			}
			for (taskDistance dis : oldTask.getAllDistances()) {
				newTask.addDistance(dis);
			}
			toReturn.add(newTask);
		}
		return toReturn;
	}
	
	private ArrayList<Trabajador> createCopyWorkers(ArrayList<Trabajador> workers, Tarea task, Trabajador worker){
		ArrayList<Trabajador> toReturn = new ArrayList<Trabajador>();
		for (Trabajador trabajador : workers) {
			Trabajador newworker = new Trabajador(trabajador.getNombre(), trabajador.getHabPodar(), trabajador.getHabLimpiar(), trabajador.getHabReparar());
			if (worker.getNombre().equalsIgnoreCase(trabajador.getNombre())) {
				newworker.addTask(task);
			} else {
				newworker.addTask(trabajador.getTask());
			}
			toReturn.add(newworker);
		}
		return toReturn;
	}
	
	private ArrayList<Herramienta> createCopyTools(ArrayList<Herramienta> tools){
		ArrayList<Herramienta> toReturn = new ArrayList<Herramienta>();
		for (Herramienta herramienta : tools) {
			Herramienta newTool = new Herramienta(herramienta.getNombre(), herramienta.getTrabajo(), herramienta.getPeso(), herramienta.getMejora(), herramienta.getCantidad());
			toReturn.add(newTool);
		}
		return toReturn;
	}
	
	//Tenemos que cambiar la evaluacion de los nodos
	private void adjustNodeEvaluation(Node node, Node oldNode) {
		double time = 0;
		//Calulamos el coste de un trabajador de desplazarse a una tarea;
		time += costMoving(node, oldNode);
		
//		//Calculamos el coste que tiene realizar una tarea;
		time += costWorking(node);
		
		node.setCoste(time+oldNode.getCost());
		node.computeHeuristic(goalNode);
		node.computeEvaluation();
	}
	
	private double costWorking(Node node) {
		double toReturn=0;
		//hay que hacer el reset task
		for (Trabajador worker : node.getTrabajadores()) {
			toReturn+=worker.taskTime(worker.getTask());
			resetATask(worker.getTask(), node.getTareas());
		}
		return toReturn;
	}
	
	private void resetATask(Tarea task, ArrayList<Tarea> taks) {
		for (Tarea tarea : taks) {
			if (tarea.equals(task)) {
				tarea.resetTask();
				return;
			}
		}
	}
	
	private double costMoving(Node newnode, Node oldNode) {
 		double toReturn = 0;
		for (int i = 0; i < newnode.getTrabajadores().size(); i++) {
			String oldTarea = "NONE";
			
			if (oldNode.getTrabajadores().get(i).getTask() != null) {
				oldTarea = oldNode.getTrabajadores().get(i).getTask().getArea();
				//Comprobamos que si el tipo de tarea asignada es similar a la tarea anterior para ir directamente o pasar por el almacen para cambiar de herramienta
				if (oldNode.getTrabajadores().get(i).getTool().getTrabajo().equalsIgnoreCase(newnode.getTrabajadores().get(i).getTask().getTipo())) {
					//Cambiamos de herramienta
					newnode.getTrabajadores().get(i).setTool(asignTool(newnode.getTrabajadores().get(i).getTask().getTipo(), newnode.getHerramientas(), oldNode.getTrabajadores().get(i).getTool().getNombre()));
					//Vamos al almacen y del almacen a la nueva tarea
					toReturn += newnode.getTrabajadores().get(i).walk_timev2(oldNode.getTrabajadores().get(i).getTask(), WHAREHOUSE);
					toReturn += newnode.getTrabajadores().get(i).walk_timev2(WHAREHOUSE, newnode.getTrabajadores().get(i).getTask());
				}else {
//					newnode.getTrabajadores().get(i).setTool(asignTool(newnode.getTrabajadores().get(i).getTask().getTipo(), newnode.getHerramientas(), oldNode.getTrabajadores().get(i).getTool().getNombre()));
					newnode.getTrabajadores().get(i).setTool(oldNode.getTrabajadores().get(i).getTool());
					toReturn += newnode.getTrabajadores().get(i).walk_timev2(oldNode.getTrabajadores().get(i).getTask(), newnode.getTrabajadores().get(i).getTask());
				}
			}else {
				//Si partimos de una tarea inicial que sea null partimos del almacen por lo que debemos de asingarle una tarea.
				newnode.getTrabajadores().get(i).setTool(asignTool(newnode.getTrabajadores().get(i).getTask().getTipo(), newnode.getHerramientas(), null));
				toReturn += newnode.getTrabajadores().get(i).walk_timev2(WHAREHOUSE, newnode.getTrabajadores().get(i).getTask());
			}
//			System.out.println("El trabajador "+newnode.getTrabajadores().get(i).getNombre()+" viene de "+oldTarea+" y va a "+newnode.getTrabajadores().get(i).getTask().getArea());
		}
		return toReturn;
	}
	//TODO TENEMOS QUE ASIGNAR UNA HERRAMIENTA A CADA UNO DE LOS TRABAJADORES CUANDO SEA NECESARIO
	private Herramienta asignTool (String tipo, ArrayList<Herramienta> tools, String oldTool) {
		int improve = 0;
		Herramienta toReturn = null;
		for (Herramienta tool : tools) {
			if (!tool.getState() && tool.getTrabajo().equalsIgnoreCase(tipo) && tool.getMejora() >= improve) {
				if (toReturn!= null) {
					toReturn.toolGiveBack();
				}
				toReturn = tool;
				tool.toolBeingUsed();
			}
			if(tool.getNombre().equalsIgnoreCase(oldTool)) {
				tool.toolGiveBack();
			}
		}
		return toReturn;
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
			//NODO ACTUAL:
			System.out.println("\n ----------------------------------------- \n"+currentNode.getEvaluation());
			int i = 0;
			for (Tarea tarea : currentNode.getTareas()) {
				if (tarea.getUnidades()>0) {
//					System.out.println(i+"Tarea "+tarea.getArea()+" "+tarea.getTipo()+" "+tarea.getUnidades());
					i++;
				}
			}
			System.out.println(i);
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
