int[][] Johnson_all_pairs_shortest_path(Node[] G){	
	
	int[][] dist = new int[G.length][G.length];
	
	for (int i = 0 ; i < dist.length ; i++)
		for (int j = 0 ; j < dist.length ; j++)
			dist[i][j] = Integer.MAX_VALUE;
			
	Node vertex = new Node();
	
	for (Node n : G)
		vertex.neighbor.put(n,0);
	
	HashMap<Node, Integer> bellman_ford_shortest_dist = new HashMap<Node, Integer>();
	
	bellman_ford_shortest_dist.put(source , 0);
	
	for (Node n : G)
		bellman_ford_shortest_dist.put(n , Integer.MAX_VALUE);
	
	for (int i = 0 ; i < G.length ; i++){
		for (Node n : G){
			for (Node neighb : n.neighbor.keySet()){
				if (bellman_ford_shortest_dist.get(n) + n.neighbor.get(neighb) < bellman_ford_shortest_dist.get(neighbor))
					bellman_ford_shortest_dist.get(neighbor) = bellman_ford_shortest_dist.get(n) + n.neighbor.get(neighb);
			}
		}
	}
	
	for (Node n : G){
		for (Node neighb : n.neighbor.keySet()){
			if (bellman_ford_shortest_dist.get(n) + n.neighbor.get(neighb) < bellman_ford_shortest_dist.get(n))
				return null;
		}
	}
	
	HashMap<Node , HashMap<Node, Integer>> updated_weight = new HashMap<Node, HashMap<Node, Integer>>();
	
	for (Node n : G){
		HashMap<Node, Integer> n_updated_weights = new HashMap<Node, Integer>();
		for (Node neighb : n.neighbor.keySet())
			n_updated_weights.put(neighb , n.neighbor.get(neighb) + bellman_ford_shortest_dist.get(neighbor - bellman_ford_shortest_dist.get(n));
		updated_weight.put(n , n_updated_weights);
	}
	
	//dijsktra with the new weights for each node
	
	HashMap<Node, Integer> index = new HashMap<Node, Integer>();
	
	for (int i = 0 ; i < G.length ; i++)
		index.put(G[i] , i);
	
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
		for (Node n : dist_map.keySet(root))
			dist[index.get(root)][index.get(n)] = dist_map.get(n); 
	}
	return dist;
}

class Node{
	HashMap<Node, Integer> neighbor;
	Node(){
		neighbor = new HashMap<Node, Integer>();
	}
}


HashMap<Node, Integer> Bellman_Ford(Node[] G , Node source){

	return dist;
}