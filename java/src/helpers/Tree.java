package helpers;

import java.util.ArrayList;


/**
 * Klasse zur Implementierung einer Baumstruktur.
 * 
 * @author Yanik Recke
 * @param <T> - Datentyp
 */
public class Tree<T> {
	/** Wurzel des Baums */
    public Node<T> root;

    /**
     * Konstruktor, der die Wurzel mit 
     * ihrer payload versorgt.
     * 
     * @param rootData - payload
     */
    public Tree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
    }  
    
    @Override
    public String toString() {
    	return root.data.toString();
    }
    
}
