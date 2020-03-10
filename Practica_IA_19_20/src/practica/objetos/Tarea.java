package practica.objetos;

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
		this.working = true;
		this.worker = worker;
	}
	public void retractWorker() {
		this.working = false;
	}
	public boolean getState() {
		return this.working;
	}
	public Trabajador getWorker() {
		return worker;
	}
	public void endTarea() {
		this.unidades = 0;
	}
	



}
