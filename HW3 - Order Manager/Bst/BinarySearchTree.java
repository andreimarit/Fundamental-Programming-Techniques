package bst;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<E extends Comparable<? super E>>{

	public Node<E> root; //holds the root of the BST
	private int size = 0; //holds the size of the BST
	private int i;
	
	/**
	 * adds the element object sent as argument to the BST
	 * @param element
	 */
	public void add(E element) {
		if (root == null && element != null) {
			root = new Node<E>(element);
			size++;
		} else if (element != null) {
			root = insert(root, element);
		}
	}
	
	/**
	 * 
	 * @param node
	 * @param value
	 * @return
	 */
	private Node<E> insert(Node<E> node, E value) {
		Node<E> result = new Node<E>(node);
		int compare = result.value.compareTo(value);

		if (compare == 0) {
			return result;
		}


		if (compare > 0) {
			if (result.left != null) {
				result.left = insert(result.left, value);
			} else {
				result.left = new Node<E>(value);
				size++;
			}
		}


		else if (compare < 0) {
			if (result.right != null) {
				result.right = insert(result.right, value);
			} else {
				result.right = new Node<E>(value);
				size++;
			}
		}

		return result;
	}

	public E get(E key) {
		if (root == null)
			return null;

		Node<E> node = root;
		int compareResult;
		while ((compareResult = node.value.compareTo(key)) != 0) {
			if (compareResult > 0) {
				if (node.left != null)
					node = node.left;
				else
					return null;
			} else {
				if (node.right != null)
					node = node.right;
				else
					return null;
			}
		}
		return node.value;
	}


	public List<E> toList() {
		List<E> result = new ArrayList<E>();
		treeToList(root, result);
		return result;
	}


	private void treeToList(Node<E> node, List<E> goal) {
		if (node != null) {
			treeToList(node.left, goal);
			goal.add(node.value);
			treeToList(node.right, goal);
		}
	}
	
	
}