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
	ArrayList<Tarea> workingIn;
	double workingHours;
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
//		this.workingAt = "";
		this.workingIn = new ArrayList<Tarea>();
		this.workingHours = 0.000;
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
	
	public void setWork() {
		this.working = true;
	}
	public void endWork() {
		this.working = false;
	}
	public boolean getState() {
		return this.working;
	}
//	public void setWorkingArea(String area) {
//		System.out.println("Asignando area");
//		this.workingAt += area +" ";
//	}
//	public String getWorkingArea() {
//		return this.workingAt;
//	}
	
	public void addWorkingArea(Tarea tarea) {
		setWorkingHours(tarea.getTipo(), tarea.getUnidades());
		this.workingIn.add(tarea);
	}
	
	public ArrayList<Tarea> getAllWorkingAreas(){
		return this.workingIn;
	}
	
	public int getWorkingHability(String workType) {
		if(workType.equalsIgnoreCase("Podar"))return getHabPodar();
		if(workType.equalsIgnoreCase("Limpiar"))return getHabLimpiar();
		if(workType.equalsIgnoreCase("Reparar"))return getHabReparar();
		return -1;
	}
	
	//Ajustamos las horas trabajando:
	public void setWorkingHours(String tipo, int unidades) {
		double aux = (double)unidades/(double)getWorkingHability(tipo);
		this.workingHours += Math.round(aux*100.0)/100.0;
	}
	
	public double getHours() {
		return this.workingHours;
	}
	
	//Buscamos una tarea entre los trabajadores;
	public boolean isAreaIn(Tarea tarea) {
		for (Tarea tarea2 : workingIn) {
			if (tarea2.getArea().equalsIgnoreCase(tarea.getArea())) {
				return true;
			}
		}
		return false;
	}
}
