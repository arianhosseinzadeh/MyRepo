//We have each guy defined as the following class

class Node{
	
	int id;
	HashSet<Node> neighbor;
	public Node(int id){
		this.id = id;
		this.neighbor = new HashSet<Node>();
	}
	public int hash(){
		return id;
	}
	
}