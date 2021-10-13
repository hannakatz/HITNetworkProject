import server.Index;

import java.util.*;

public class FindingShortRoutesByWeight<T>  {
    Stack<Node<T>> workingStack; // for discovered nodes
    Set<Node<T>> finished;

    public FindingShortRoutesByWeight(){
        workingStack = new Stack<>();
        finished = new LinkedHashSet<>();
    }
    public void FindingShortRoute(Matrix array, Index start, Index finish){

        workingStack.add(new Node(start, null));
        int arraySize = array.primitiveMatrix.length;

        int[] shortestDistances = new int[arraySize];
        boolean[] added = new boolean[arraySize];

        while(!workingStack.isEmpty())
        {
            Node<T> popped = workingStack.pop();
            //Collection<Index> reachableNodes = array.getNeighbors((Index)popped.getData());




        }




















    }
}
