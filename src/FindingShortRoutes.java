import java.util.*;

public class FindingShortRoutes<T> {
    public FindingShortRoutes() {
    }

    public void FindingShortRoutesFuncBFS(Matrix Array, Index Start, Index finish) {
        // Stores indices of the matrix cells
        Queue<Node<T>> q = new LinkedList<>();
        Queue<Node<T>> results = new LinkedList<>();

        boolean[][] vis = new boolean[Array.primitiveMatrix.length][Array.primitiveMatrix.length];
        // Mark the starting cell as visited
        // and push it into the queue
        q.add(new Node(Start, null));
        vis[Start.row][Start.column] = true;

        // Iterate while the queue
        int min=0;
        while (!q.isEmpty()) {
            Node NewNode = q.peek();
            Index test = (Index) NewNode.getData();
            q.remove();
            // Go to the adjacent cells
            List<Index> nibghers = (List<Index>) Array.getNeighbors(test);
            for (Index index : nibghers) {
                if (Array.getValue(index) == 1 && vis[index.row][index.column] == false) {
                    q.add(new Node(index, NewNode,NewNode.getNumber()+1));


                    if (index.row == finish.row && index.column == finish.column) {

                        if (min == 0 ) {
                            results.add(new Node(index, NewNode));
                            min = NewNode.getNumber();
                        }
                        else if (min > NewNode.getNumber()){
                            results.remove();
                            results.add(new Node(index, NewNode));
                            min = NewNode.getNumber();
                        }
                        else if(min == NewNode.getNumber()){
                            results.add(new Node(index, NewNode));
                        }
                    }
                }

            }
            vis[test.row][test.column] = true;


        }
        return;
    }

    public static void main(String[] args) {
        int[][] source = {
                {1, 0, 0,0},
                {1, 1, 1,1},
                {1, 0 ,0,1},
                {1, 1, 1,0},
        };

                new FindingShortRoutes().FindingShortRoutesFuncBFS(new Matrix(source), new Index(0, 0), new Index(3, 0));


    }
}