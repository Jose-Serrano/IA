package practica.objetos;

import java.util.ArrayList;

/**
 * Clase creada como objeto base para la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class Tarea {

	// Variables del objeto Tarea
	String tipo;
	String area;
	int unidades;
	// AÑADIR LAS VARIABLES NECESARIAS
	boolean working;
	Trabajador worker;
	Herramienta toolHab;
	ArrayList<Tarea> adyacentTasks;
	
	ArrayList<taskDistance> distances;
	/**
	 * Constructor para el objeto
	 * NO MODIFICAR LA LLAMADA DEL CONSTRUCTOR
	 */
	public Tarea(String tipo, String area, int unidades) {
		this.tipo = tipo;
		this.area = area;
		this.unidades = unidades;
		// Añadir el estado inicial (estático) de las variables que se añadan
		// Si se necesita añadir valores variables, como un ID, utilizar setters
		this.working = false;
		this.worker = null;
		this.toolHab = null;
		this.adyacentTasks = new ArrayList<Tarea>();
		this.distances = new ArrayList<taskDistance>();
	}
	
	
	// Métodos getters y setters
	/**
	 * Añadir (si procede) métodos auxiliares, como getters o setters
	 */
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	public void assignWorker(Trabajador worker) {
		if (worker != null) {
			this.working = true;
		}
		this.worker = worker;
	}
	public void retractWorker() {
		this.working = false;
		this.worker = null;
	}
	public boolean getState() {
		return this.working;
	}
	public void notInit() {
		this.worker = null;
		this.working = false;
	}
	public Trabajador getWorker() {
		return worker;
	}
	public void setTool(Herramienta tool) {
		this.toolHab = tool;
	}
	public Herramienta getTool() {
		return this.toolHab;
	}
	
	public void resetTask() {
		this.unidades = 0;
	}
	
	//Para situar las tareas que son adyacentes.
	
	public void addAdyacentTask(Tarea task) {
		this.adyacentTasks.add(task);
	}
	
	//Obtener las tareas que son adyacentes
	public ArrayList<Tarea> getAdyacentTask(){
		return this.adyacentTasks;
	}
	
	public boolean equals(Tarea task) {
		
		if (this.getArea().equalsIgnoreCase("A") && this.getArea().equalsIgnoreCase("A")) {
			return true;
		}
		
		return (
				(compareTipo(task.getTipo()) && 
				compareArea(task.getArea()) && 
				//compareWorker(task.getWorker()) && 
				compareUnidades(task.getUnidades()))
				) ? true : false;
	}
	
	private boolean compareTipo(String toolType) {
		return (this.tipo.equalsIgnoreCase(toolType)) ? true : false;
	}
	
	private boolean compareArea(String toolArea) {
		return (this.area.equalsIgnoreCase(toolArea)) ? true : false;		
	}
	
	private boolean compareWorker(Trabajador toolWorker) {
		return (this.worker != null && this.worker.getNombre().equalsIgnoreCase(toolWorker.getNombre())) ? true : false;		
	}
	
	private boolean compareUnidades(int unidades) {
		return (this.unidades == unidades) ? true : false;		
	}
	
	public Tarea clone() {
		Tarea toReturn = new Tarea(tipo, area, unidades);
		if (this.worker!=null) {
			toReturn.assignWorker(this.worker);
		}
		return toReturn;
	}

	
	public void addDistance(taskDistance x) {
		this.distances.add(x);
	}
	
	public int getDisntaceBetweenAreas(String area) {
		int toReturn = -1;
		for (taskDistance taskDistance : distances) {
			if (taskDistance.getAreaName().equalsIgnoreCase(area)) {
				return taskDistance.getDistance();
			}
		}
		return 999999999;
	}
	
	public ArrayList<taskDistance> getAllDistances(){
		return this.distances;
	}
}
