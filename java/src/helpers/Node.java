package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse einer Node der Baumstruktur.
 * 
 * @author Yanik Recke
 * @param <T> - Datentyp
 */
public class Node<T> {
	/** Payload des Nodes */
	public T data;
	/** Parent Node der eigenen Node */
	public Node<T> parent;
	/** Kinder der eigenen Node */
    public List<Node<T>> children = new ArrayList<Node<T>>();
        
    /**
     * Fügt dem eigenen Node ein Node
     * als Kind hinzu.
     * 
     * @param data - payload für das Kind
     * @return - das hinzugefügte Kind
     */
    public Node<T> addChild(T data) {
        Node<T> childNode = new Node<T>();
        
        childNode.parent = this;
        childNode.data = data;
        
        this.children.add(childNode);
        return childNode;
    }  
        
    @Override
    public String toString() {
    	return data.toString();
    }
}
