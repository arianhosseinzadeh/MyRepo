class Strike{

	HashMap<Integer, Node> Graph;

	Public Strike(){
	        Graph = new HashMap<Integer, Node>();
	}
	
	public boolean addGuy(int id){
		if (Graph.containsKey(id))
			return false;
		Node guy = new Node(id);
		Graph.put(id, guy);
		return true;
	}

	public void addConnection(int origin , int target){
		if (!Graph.containsKey(origin) || !(Graph.containsKey(target))
			return false;
		Node g1 = Graph.get(origin);
		Node g2 = Graph.get(target);
		if (g1.neighbor == null)
			g1.neighbor = new HashSet<Node>();
		g1.neighbor.add(g2);
		return true;
	}
  /* =========================================
  by use of TreeSet data structure we print a sorted list of articulation points ranked by number of components that removal   of each results , the sizes of these components, the variance of the sizes and the effect of the removal on the sum of the   of the shortest paths   
  */
	void score_nodes(){
		if (Graph.size == 0)
			return;
		Node[] G = Graph.keySet().toArray(new Node[Graph.keySet().size()])
		HashSet<Node> art_points = Articulation_point(G);
		TreeMap<Node, Double> variance = new TreeMap<Node, Double>(new Comparator<Map.Entry<Node , Double>>(){
			public int compare(Map.Enry<Node, Double> e1 , Map.Enry<Node, Double> e2){
				double v1 = e1.getValue();
				double v2 = e2.getValue();
				if (v1 == v2 || Math.abs(v1 - v2) < 0.0001)
					return 0;
			
				return (Math.signum(v2 - v1));
			}
		});
		TreeMap<Node, Integer> components = new TreeMap<Node, Integer>(new Comparator<Map.Entry<Node, Integer>(){
			public int compare(Map.Entry<Node, Integer> e1 , Map.Entry<Node, Integer> e2){
				return e2.getValue() - e1.getValue();
			}
		});
		TreeMap<Node, Integer> shortest_path_map = new TreeMap<Node, Integer>(new Comparator<Map.Entry<Node, Integer>() {
			int compare(Map.Entry<Node, Integer> e1 , Map.Entry<Node, Integer> e2){
				return e2.getValue() - e1.getValue();
			}
		});
		int shortest_path = shortest_path_sum(G , null);
		for (Node n : art_points){
			shortest_path_map.put(n , shortest_path_sum(G , n) - shortest_path);
			ArrayList<Integer> cmp = components(G , n);
			components.put(n , cmp.size());
			variance.put(n , variance(cmp));
		}
		TreeSet<
		Node> ranked_nodes = new TreeSet<Node>(new Comparator<Node>(){
			public int compare(Node n1 , Node n2){
				if (shortest_path_map.get(n1) != shortest_path_map.get(n2))
					return shortest_path_map.get(n2) - shortest_path_map.get(n1);
				else{
					if (Math.abs(variance.get(n1) - variance.get(n2)) > 0.001 || variance.get(n1) != variance.get(n2))
						return Math.signum(variance.get(n2) - variance.get(n1));
					else
						return Math.signum(components.get(n2) - components(n1));
				}
			}
		});
		for (Node n : art_points)
			ranked_nodes.add(n);
		for (Node n : ranked_nodes)
			System.out.println(n.id + " :: shortest path vitality (" + vital_node.get(n) + ") ; number of components  resulted ("+ components.get(n) +") ; variance of components sizes ("+variance.get(n) +")");
	
		return;
	}
	  
  /*By use of Floyd-Warshall algorithm we find the sum of the all-pairs shortest paths
  then we calculate that how the removal of each artiulation point will affect this sum
  If junction argument in the input is not null we don't consider paths which go through this node
  /*
  
	int shortest_path_sum(Node[] G, Node junction){ 
	 	/*we want to find if we remove junction what would be the sum of the shortest path , exception : if junction is null 			we find the sum of the shortest path for the whole graph
	 	*/
		int[][] shortest_path = new int[G.length][G.length];
		for (int i = 0 ; i < shortest_path.length; i++){
			for (int j = 0 ; j < shortest_path.length; j++){
				shortest_path[i][j] = Integer.MAX_VALUE;
			}
		}
		for (int i = 0 ; i < shortest_path.length ; i++)
			shortest_path[i][i] = 0;
		for (int i = 0 ; i < shortest_path.length ; i++){
			if (junction != null && G[i] == junction)
				continue;
			
			for (int j = 0 ; j < shortest_path.length ; j++){
				if (junction != null && G[j] == junction)
					continue;
				for (int k = 0 ; k < shortest_path.length ; k++){
					if (junction != null && G[k] == junction)
						continue;
					shortest_path[i][j] = Math.min(shortest_path[i][k] + shortest_path[k][j] , shortest_path[i][j]);
				}
			}
		}
		int sum = 0;
		for (int i = 0 ; i < shortest_path.length ; i++){
			if (junction != null && G[i] == junction)
				continue;
			for (int j = 0 ; j < shortest_path.length ; j++){
				if (junction != null && G[j] == junction)
					continue;
				sum += shortest_path[i][j];
			}
		}
		
		return d;
	}
	  
	  
	  //this method finds the variance of a list of numbers
	double variance(ArrayList<Integer> list){
		double mean = 0;
		for (int n : list){
			mean += n;
		}
		mean /= list.size();
		double var = 0;
		for (int n : list){
			var += Math.pow(n - mean , 2);
		}
		var /= list.size();
		var = Math.sqrt(var);
		return var;
	}

  /* ============================================ 
    We find size
    of the resultant components by applying DFS and removing the articulation point from the network
    (we simulate the removal by not visiting of this speical node)
    This methods returns the sizes of the resultant components as an array
  */
	ArrayList<Integer> components(Node[] G , Node junction){
		HashSet<Node> visited = new HashSet<Node>();
		ArrayList<Integer> comp = new ArrayList<Integer>();
		for (Node n : G){
			if (n != junction && !visited.contains(n)){
				int comp_size = components_sub(n , junction, 0, visited);
				comp.add(comp_size);
			}
		}
		return comp;
	}
	
	int components_sub(Node current , Node junction, int d, HashSet<Node> visited){
		visited.add(current);
		for (Node n : current.neighbor){
			if (n != junction && !visited.contains(n)){
			
				d = Math.max(d, components_sub(n , junction , d + 1 , visited));
			}
		}
		return d;
	}

/*
  We find the articulation points by applying DFS on the graph :
  First the depth of each node in the DFS tree is calculated, then the "low" value is calculated which is lowest value
  between these three values: depth of the node itself, minimum low value of neighbors and depth of the ancestors (which are   connected to the node by back edges)
*/
	
	HashSet<Node> Articulation_point(Node[] G){
		Node root = G[0];
		HashMap<Node, Integer> depth = new HashMap<Node, Integer>();
		HashMap<Node, Integer> low = new HashMap<Node, Integer>();
		HashSet<Node> art_points = new HashSet<Node>();
		if (root.neighbor.size() > 1)
			art_points.add(root);
		Ariculation_point_sub(root, depth, low, 0);
		outter : for (Node n : depth){ // traverse over all nodes of the graph
			if (n != root){
				for (Node neighb : n.neighbor){
			
					if (low.get(neighb) < depth.get(n))
						continue outter;
				}
				art_points.add(n);
			}
		}
		return art_points;
	}
	
	int Ariculation_point_sub(Node current , HashMap<Node, Integer> depth , HashMap<Node, Integer> low , int d){
		depth.put(current , d);
		int l = d;
		for (Node neighb : current.neighbor){
			if (!depth.contaisnKey(neighb))
				l = Math.min(l, DFS_visit(neighb , depth , low , d+1));
			else if (depth.get(neighb) < depth.get(current)) // if it's an ancestor i.e. if it's a back edge
				l = Math.min(l, depth.get(neighb));
		}
		low.put(current , l);
		return l;
	}


}

