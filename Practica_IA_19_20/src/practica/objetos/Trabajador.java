package practica.objetos;

import java.util.ArrayList;

/**
 * Clase creada como objeto base para la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class Trabajador {

	// Variables del objeto Trabajador
	String nombre;
	int habPodar;
	int habLimpiar;
	int habReparar;
	// AÑADIR LAS VARIABLES NECESARIAS
	boolean working;
//	String workingAt;
	Tarea task;
	
	double workingHours;
	String bestHab;
	
	Herramienta tool;
	ArrayList<Tarea> completedTasks;
	/**
	 * Constructor para el objeto
	 * NO MODIFICAR LA LLAMADA DEL CONSTRUCTOR
	 */
	public Trabajador(String nombre, int habPodar, int habLimpiar, int habReparar) {
		this.nombre      = nombre;
		this.habPodar    = habPodar;
		this.habLimpiar  = habLimpiar;
		this.habReparar  = habReparar;
		
		// Añadir el estado inicial (estático) de las variables que se añadan
		// Si se necesita añadir valores variables, como un ID, utilizar setters
		
		this.working = false;
		this.task = null;
		this.workingHours = 0.000;

		
		//Para las tareas que se han completado:
		completedTasks = new ArrayList<Tarea>();
		
		//Pruebas para el avanzado de diferente forma
		if (this.habPodar > this.habLimpiar && this.habPodar > this.habReparar) {
			this.bestHab = "podar";
		}else if(this.habLimpiar > this.habPodar && this.habLimpiar > this.habReparar) {
			this.bestHab = "limpiar";
		}else if(this.habReparar > this.habLimpiar && this.habReparar > this.habPodar) {
			this.bestHab = "reparar";
		}
	}
	
	/**
	 * Añadir (si procede) métodos auxiliares, como getters o setters
	 */
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getHabPodar() {
		return habPodar;
	}
	public void setHabPodar(int habPodar) {
		this.habPodar = habPodar;
	}
	public int getHabLimpiar() {
		return habLimpiar;
	}
	public void setHabLimpiar(int habLimpiar) {
		this.habLimpiar = habLimpiar;
	}
	public int getHabReparar() {
		return habReparar;
	}
	public void setHabReparar(int habReparar) {
		this.habReparar = habReparar;
	}
	
	public double getHours() {
		return this.workingHours;
	}
	
	public void startWork() {
		this.working = true;
	}
	
	public void endWork() {
		System.out.println("EL TRABAJADOR "+this.nombre+" HA COMPLETADO LA TAREA "+this.task.getArea());
		this.working = false;
		this.completedTasks.add(this.task);
		this.workingHours += taskTime(this.task);
	}
	
	public double taskTime(Tarea newTask) {
		double toReturn = (double)newTask.getUnidades() / ((double)getWorkingAbility(newTask.getTipo())+this.tool.getMejora());
		toReturn = toReturn * 60;
//		System.out.println("EL TIEMPO DE REALIZAR LA TAREA "+newTask.getArea()+" DE "+newTask.getTipo()+" EL TRABAJADOR "+this.nombre+" ES DE ------"+toReturn);
		return toReturn;
	}
	
	public int getWorkingAbility(String workType) {
		if(workType.equalsIgnoreCase("Podar"))return getHabPodar();
		if(workType.equalsIgnoreCase("Limpiar"))return getHabLimpiar();
		if(workType.equalsIgnoreCase("Reparar"))return getHabReparar();
		return -1;
	}
	public boolean isWorking() {
		return this.working;
	}
	
	public String getBestAbility() {
		return this.bestHab;
	}
	
	public void addTask(Tarea task) {
		this.task = task;
	}
	public Tarea getTask() {
		return this.task;
	}
	public void removeTask() {
		this.task = null;
		this.working = false;
	}
	
	
	public ArrayList<Tarea> getCompletedTasks(){
		return this.completedTasks;
	}
		
	//Cogemos una herramienta
	public void setTool(Herramienta tool) {
		this.tool = tool;
	}
	
	public Herramienta getTool() {
		return this.tool;
	}
	
	//Tenemos que pasar por el almacen para cambiar de herramienta
	public void walk_WhareHouse_Task(Tarea oldTask, Tarea wharehouse, Tarea newTask) {
		//Primero sumamos el tiempo de llegar al almacen
		walkTime(oldTask, wharehouse);
		//Despues le sumammos el tiempo de ir de el almacen a la tarea
		walkTime(wharehouse, newTask);
	}
	
	//Encontramos el mejor camino para llegar a una tarea.
	//Funcionan a la perfeccion
	int totalCost = 0;
	public void walkTime(Tarea oldTask, Tarea newTask) {
		totalCost = 0;
//		System.out.println("queremos ir de: " +oldTask.getArea()+ " a "+newTask.getArea());
		int numAreas = 0;
		ArrayList<String> visitados = new ArrayList<String>();
		visitados.add(oldTask.getArea());
		for (Tarea tarea : oldTask.getAdyacentTask()) {
			totalCost = -1;
//			System.out.println("Estamos en el bucle principal, pasamos de "+oldTask.getArea()+" a "+tarea.getArea());
			if (tarea.getArea().equalsIgnoreCase(newTask.getArea())) {
				this.workingHours += 5;
				return;
			}
			
			if(numAreas <= 0) {
				numAreas = bestWay(tarea, newTask, 1, new ArrayList<String>(visitados));
//				System.out.println("Coste de "+numAreas);
			}else {
				int newCost = bestWay(tarea, newTask, 1, new ArrayList<String>(visitados));
				if(newCost >= 0 && newCost < numAreas)
					numAreas = newCost;		
//				System.out.println("Coste de "+numAreas);	
			}
		}
//		System.out.println("Para ir de "+oldTask.getArea()+" a "+newTask.getArea()+" hay una distancia de "+numAreas);
		this.workingHours += numAreas*(5+this.tool.getPeso());
	}
	
	public int bestWay(Tarea origen, Tarea destino, int coste, ArrayList<String> visitados) {
		
//		System.out.println("Estamos en: "+origen.getArea());
		visitados.add(origen.getArea());
		
		if (origen.getArea().equalsIgnoreCase(destino.getArea())) {
			return coste;
		}
		
		for (Tarea tarea : origen.getAdyacentTask()) {
			
			if(!visitados.contains(tarea.getArea())) {
				int a = bestWay(tarea, destino, coste+1, new ArrayList<String>(visitados));
				if (totalCost <= 0 || totalCost > a) {
					totalCost = a;
				}
			}
			
		}
		return totalCost;
		
	}
	
	public int walk_timev2(Tarea oldTask ,Tarea newTask){
		int timeWalked = 0;
		int distance = oldTask.getDisntaceBetweenAreas(newTask.getArea());
		timeWalked += distance*(5+this.tool.getPeso());
		return timeWalked;
	}
	
	public double getWorkingTime() {
		return this.workingHours;
	}
	
	
	
	
	
}
