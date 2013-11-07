public class Strike_Interface{
	
	Strike str;
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String[] inp = null;
		while (sc.hasNext()){
			inp = sc.next().trim().toLowerCase().split(" ");
			if (inp.length == 4 && inp[0].equals("new") && inp[1] == "node" && isNumber(inp[2])){
				int id = Integer.parseInt(inp[2]);
				if (!str.addGuy(id))
					System.out.println("The node is already present in the network");
			}else if (inp.length == 3 && inp[0].equals("new") && inp[1].equals("link") && isNumber(inp[2]) && isNumber(inp[3]))
				if (!str.addConnection(Integer.parseInt(inp[2]) , Integer.parseInt(inp[3])))
					System.out.println("The node(s) is not in the network");
			}else if (inp.length == 1 && inp[0] == "strike"){
				System.out.println("Strike result :");
				str.strike();
				return;
			}else if (inp.length == 1 && inp[0].equals("help"){
				System.out.println("List of commands :");
				System.out.println("Adding a new guy : new node <id>");
				System.out.println("Adding a new friendship link : new link <id_origin> <id_target>");
				System.out.println("Get the sorted list of nodes : strike");
				System.out.println("Help : help");			
			}else 
				System.out.println("Wrong input");
		}
		sc.close();
		return;
	}
	boolean isNumber(String s){
		try{
			Integer.parseInt(s);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	Strike_Interface(){
		str = new Strike();
	}
	

}