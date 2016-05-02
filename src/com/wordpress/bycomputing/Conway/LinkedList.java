package com.wordpress.bycomputing.Conway;

public class LinkedList {
	
	Linkable head;
	
    public interface Linkable {
    	public Linkable getNext();
    	public void setNext(Linkable node);
    }

	public synchronized Linkable getHead() {
		return head;
	}
    
	public synchronized void insertAtHead(Linkable node) {
		node.setNext(head);
		head = node;
	}
	
    public synchronized void insertAtTail(Linkable node) {
    	if (head == null) head = node;
    	else {
    		Linkable p, q;
    		for (p = head; (q = p.getNext()) != null; p = q);
    		p.setNext(node);
    	}
    }
    
    public synchronized Linkable removeFromHead() {
    	Linkable node = head;
    	if (node != null) {
    		head = node.getNext();
    		node.setNext(null);
    	}
    	return node;
    }
    
    public synchronized Linkable removeFromTail() {
    	if (head == null) return null;
    	Linkable p = head, q = null, next = head.getNext();
    	if (next == null) {
    		head = null;
    		return p;
    	}
    	while ((next = p.getNext()) != null) {
    		q = p;
    		p = next;
    	}
    	q.setNext(null);
    	return p;
    }
    
    public synchronized void remove(Linkable node) {
    	if (head == null) return;
    	if (node.equals(head)) {
    		head = head.getNext();
    		return;
    	}
    	Linkable p = head, q = null;
    	while ((q = p.getNext()) != null) {
    		if (node.equals(q)) {
    			p.setNext(q.getNext());
    			return;
    		}
    		p = q;
    	}
    }    
}
