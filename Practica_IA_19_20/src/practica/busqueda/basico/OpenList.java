package practica.busqueda.basico;

/**
 * Clase creada como ejemplo de la parte 2 de la práctica 2019-2020 de IA, UC3M Colmenarejo
 * NO MODIFICAR
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class OpenList {

	protected Node rootNode = null; // Nodo Raiz de la lista

	// Constructor de la lista
	public OpenList() {
		this.rootNode = new Node();	// Genera el nodo raiz vacio. Despues se modifica
	}

	/*** Métodos específicos para el funcionamiento de A estrella */

	/**
	 * NO MODIFICAR
	 * Introduce un nodo en función de su valor de evaluación (de menor a mayor). Imprescindible para mantener el orden de la lista
	 * @param newElem
	 */
	public void insertAtEvaluation(Node newElem) {
		Node newNodo = new Node(newElem);
		Node posNode = null;
		Node nodeIt = rootNode.getNextNode();
		if(nodeIt == null) {
			rootNode.setNextNode(newNodo);
		}
		else if(nodeIt.getEvaluation()>newNodo.getEvaluation()) {
			newNodo.setNextNode(nodeIt);
			rootNode.setNextNode(newNodo);
		}
		else {
			while (nodeIt != null) {
				
				posNode = nodeIt;
				nodeIt  = nodeIt.getNextNode();
				if(nodeIt == null) {
					posNode.setNextNode(newNodo);
					break;
				}
				else if(nodeIt.getEvaluation() > newNodo.getEvaluation()) {
					newNodo.setNextNode(nodeIt);
					posNode.setNextNode(newNodo);
					break;
				}
			}

		}
	}

	/**
	 * NO MODIFICAR
	 * Extraer el primer nodo de la lista
	 * @return
	 */
	public Node pullFirst() {
		Node nodo = rootNode.getNextNode();		// Se modifica el segundo nodo para que sea el siguiente del nodo raiz (primer nodo de la lista)
		rootNode.setNextNode(nodo.getNextNode());
		return nodo;
	}


	/*** Métodos de utilidad dentro de una lista **/

	/**
	 * NO MODIFICAR
	 * Introduce al comienzo un nuevo nodo
	 * @param newElem
	 */
	public void addFirst(Node newElem) {
		Node newNodo = new Node(newElem);
		Node oldNode = this.rootNode.getNextNode();
		rootNode.setNextNode(newNodo);
		newNodo.setNextNode(oldNode);
	}

	/**
	 * NO MODIFICAR
	 * Introduce al final un nuevo nodo
	 * @param newElem
	 */
	public void addLast(Node newElem) {
		Node newNodo= new Node(newElem);

		Node lastNode = null;
		Node nodeIt = rootNode.getNextNode();
		while (nodeIt != null) {
			lastNode = nodeIt;
			nodeIt = nodeIt.getNextNode();
		}
		if (lastNode == null) {
			rootNode.setNextNode(newNodo);
		} else {
			lastNode.setNextNode(newNodo);
		}
	}	

	/**
	 * NO MODIFICAR
	 * Comprobar si la lista esta vacia
	 * @return
	 */
	public boolean isEmpty() {				
		return (rootNode.getNextNode() == null);
	}


	/**
	 * NO MODIFICAR
	 * Obtener el tamaño de la lista
	 * @return
	 */
	public int getSize() {
		int size = 0;
		Node nodeIt = rootNode.getNextNode();
		while (nodeIt != null) {
			size++;
			nodeIt = nodeIt.getNextNode();
		}
		return size;
	}

}