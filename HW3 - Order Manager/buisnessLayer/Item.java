package businessLayer;

public class Item implements Comparable<Item>{

	private int id;
	@Override
	public int compareTo(Item item) {
		if(this.id == item.id){
			return 0;
		}
		if(this.id < item.id){
			return -1;
		}
		if(this.id > item.id){
			return 1;
		}		
		return -2;
		
	}
	
	public Item(int id){
		this.id = id;
	}
	
	

}
