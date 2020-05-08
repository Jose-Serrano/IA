package practica.objetos;

/**
 * Clase creada como objeto base para la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class Herramienta {

	// Variables del objeto Herramienta
	String nombre;
	String trabajo;
	double peso;
	int mejora;
	int cantidad;
	// AÑADIR LAS VARIABLES NECESARIAS
	boolean assigned;
	/**
	 * Constructor para el objeto
	 * NO MODIFICAR LA LLAMADA DEL CONSTRUCTOR
	 */
	public Herramienta(String nombre, String trabajo, double peso, int mejora, int cantidad) {
		this.nombre = nombre;
		this.trabajo = trabajo;
		this.peso = peso;
		this.mejora = mejora;
		this.cantidad = cantidad;
		
		// Añadir el estado inicial (estático) de las variables que se añadan
		// Si se necesita añadir valores variables, como un ID, utilizar setters
		if (cantidad == 0) {
			this.assigned = true;
		}else {
			this.assigned = false;
		}
	}

	/**
	 * Métodos getters y setters
	 */
	// Añadir (si procede) métodos auxiliares, como getters o setters

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTrabajo() {
		return trabajo;
	}
	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public int getMejora() {
		return mejora;
	}
	public void setMejora(int mejora) {
		this.mejora = mejora;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public void toolBeingUsed() {
		cantidad--;
		if (cantidad == 0) {
			this.assigned = true;
		}
	}
	public void toolGiveBack() {
		cantidad++;
		if (cantidad > 0) {
			this.assigned = false;
		}
	}
	public boolean getState() {
		return this.assigned;
	}
	
	public boolean equals(Herramienta tool) {
		return (compareName(tool.getNombre()) && compareWork(tool.getTrabajo()) && compareState(tool.getState()))? true:false;
	}
	
	private boolean compareName(String toolName) {
		return (this.nombre.equalsIgnoreCase(toolName)) ? true : false;
	}
	
	private boolean compareWork(String toolWork) {
		return (this.trabajo.equalsIgnoreCase(toolWork)) ? true : false;
	}
	
	private boolean compareState(boolean toolState) {
		return (this.assigned == toolState) ? true : false;
	}

}
