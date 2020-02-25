package model;
import static org.junit.Assert.*;

import org.junit.Test;

import bst.BinarySearchTree;
import businessLayer.Product;


public class TestTree {

	@Test
	public void test(){
		BinarySearchTree<String> tree = new BinarySearchTree<String>();
		tree.add("Andrei");
		tree.add("Jessica");
		tree.add("Lias");
		assertEquals(new String[]{ "Lias", "Andrei", "Jessica" }, tree.toList().toArray(new String[3]));
		assertEquals(null, tree.get("Otto"));
		assertEquals("Semida", tree.get("Semida"));
		
		BinarySearchTree<Product> tree1 = new BinarySearchTree<Product>();
		Product laptop = new Product("laptop", 2500, true);
		Product camera = new Product("camera", 2000, true);
		Product desk = new Product("computer desk", 500, false);
		Product lamp = new Product("lamp", 200, true);
		
		tree1.add(laptop);
		tree1.add(camera);
		tree1.add(desk);
		//assertEquals(new Product[]{laptop, camera, desk}, tree.toList().toArray(new Product[3]));
		assertEquals(null, tree1.get(lamp));
		assertEquals(laptop, tree1.get(laptop));
	}
}
