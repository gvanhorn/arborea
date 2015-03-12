import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;


public class MoveList implements Serializable{
	private Queue<Hex[]> orderedHexList;
	private Queue<String> orderedMoveTypeList;
	
	MoveList(){
		orderedHexList = new LinkedList<Hex[]>();
		orderedMoveTypeList = new LinkedList<String>();
	}
	
	public Queue<Hex[]> getOrderedHexList() {
		return orderedHexList;
	}
	public void setOrderedHexList(Queue<Hex[]> orderedHexList) {
		this.orderedHexList = orderedHexList;
	}
	
	public void addToHexList(Hex from, Hex to){
		Hex[] entry = {from,to};
		orderedHexList.add(entry);
	}
	
	public Queue<String> getOrderedTypeList() {
		return orderedMoveTypeList;
	}
	public void setOrderedMoveTypeList(Queue<String> orderedMoveTypeList) {
		this.orderedMoveTypeList = orderedMoveTypeList;
	}
	
	public void addToTypeList(String type){
		orderedMoveTypeList.add(type);
	}
	

}
