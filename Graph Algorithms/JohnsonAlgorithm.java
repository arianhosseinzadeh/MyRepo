class Node{
	HashMap<Node, Integer> neighbor;
	Node(){
		neighbor = new HashMap<Node, Integer>();
	}
}

class JohnsonAlgorithm{ 
	
	static int[][] JohnsonAlgorithm(Node[] G){	
		
		int[][] dist = new int[G.length][G.length]; // this is going to be the result
		
		for (int i = 0 ; i < dist.length ; i++)
			for (int j = 0 ; j < dist.length ; j++)
				dist[i][j] = Integer.MAX_VALUE; // initialize the distances as infinite
				
		Node vertex = new Node(); // create one external node and connect it to all nodes of the graph with edges of weight 0
		for (Node n : G)
			vertex.neighbor.put(n,0);
		
		HashMap<Node, Integer> bl_dist = new HashMap<Node, Integer>(); // by use of bellman ford , find the shortest distance from the external node to all nodes of the graph
		
		//bellman ford algorithm 
		bl_dist.put(source , 0);
		
		for (Node n : G)
			bl_dist.put(n , Integer.MAX_VALUE);
		
		for (int i = 0 ; i < G.length ; i++){
			for (Node n : G){
				for (Node neighb : n.neighbor.keySet()){
					if (bl_dist.get(n) + n.neighbor.get(neighb) < bl_dist.get(neighbor))
						bl_dist.get(neighbor) = bl_dist.get(n) + n.neighbor.get(neighb);
				}
			}
		}
		//check to see if there's a negative cycle in the original graph (note that adding an external node would not create negative cycle in our graph)
		for (Node n : G){
			for (Node neighb : n.neighbor.keySet()){
				if (bl_dist.get(n) + n.neighbor.get(neighb) < bl_dist.get(n))
					return null; // if there's a negative cycle in the graph G , return null
			}
		}
		
		HashMap<Node , HashMap<Node, Integer>> updated_weight = new HashMap<Node, HashMap<Node, Integer>>();
		//create new weights for edges , w'(u,v) = w(u,v) + bl_dist(u) - bl_dist(v)
		for (Node n : G){
			HashMap<Node, Integer> n_updated_weights = new HashMap<Node, Integer>();
			for (Node neighb : n.neighbor.keySet())
				n_updated_weights.put(neighb , n.neighbor.get(neighb) + bl_dist.get(neighbor) - bl_dist.get(n));
			updated_weight.put(n , n_updated_weights);
		}
		
		
		HashMap<Node, Integer> index = new HashMap<Node, Integer>();
		for (int i = 0 ; i < G.length ; i++)
			index.put(G[i] , i); // assign one index for each node, so to be able to fill the result array
		
		//apply dijsktra with the new weights for each node
		for (Node root : G){
			Node current = root;
			HashMap<Node, Integer> dist_map = new HashMap<Node, Integer>();
			HashSet<Node> visited = new HashSet<Node>();
			dist_map.put(current , 0);
			while (visited.size() < G.length){
				Node min_neighb = null;
				visited.add(current);
				for (Node neighb : current.neighbor.keySet()){
					if (!visited.contains(neighb)){
						if (dist_map.get(current) + updated_weight.get(current).get(neighb) < dist_map.get(neighb))
							dist_map.put(neighb, dist_map.get(current) + updated_weight.get(current).get(neighb));
						if (min == null || dist_map.get(neighb) < dist_map.get(neighb))
							min = neighb;
					}
				}
				current = min;
			}
			for (Node n : dist_map.keySet(root)) // fill in the result array
				dist[index.get(root)][index.get(n)] = dist_map.get(n); 
		}
		return dist;
	}
}
