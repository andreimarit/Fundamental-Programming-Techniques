package bst;
public class Node<E extends Comparable<? super E>>{
	
	public Node<E> left;
	public Node<E> right;
	public final E value;
	
	public Node(E value){
		this.value = value;
	}
	
	public Node(Node<E> node){
		this.left = node.left;
		this.right = node.right;
		this.value = node.value;
	}
}