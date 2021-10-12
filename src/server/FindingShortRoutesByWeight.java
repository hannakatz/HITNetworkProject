package server;

import java.util.*;

class Node implements Comparator<Node> {
    public int node;
    public int cost;
    public Node() { } //empty constructor
    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }
    @Override
    public int compare(Node node1, Node node2)
    {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        return 0;
    }
}


public class FindingShortRoutesByWeight {

    int dist[];
    Set<Integer> visited;
    PriorityQueue<Node> pqueue;
    int V; // Number of vertices
    List<List<Node>> adj_list;
    void FindingShortRoutesByWeight(){
        this.V = V;
        dist = new int[V];
        visited = new HashSet<>();
        pqueue = new PriorityQueue<Node>(V, new Node());
    }
    public void FindingShortRoute(int[][] array,Index start,Index finish){

    }
}
