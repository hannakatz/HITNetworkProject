import java.util.LinkedList;
import java.util.Queue;

public class FindingShortRoutes {
    public FindingShortRoutes() {
    }

    public void  FindingShortRoutesFuncBFS(Matrix Array, Index Start, Index finish){
        // Stores indices of the matrix cells
        Queue<Node> q = new LinkedList<>();
        boolean[][] vis = new boolean[Array.primitiveMatrix.length][Array.primitiveMatrix.length];
        // Mark the starting cell as visited
        // and push it into the queue
        q.add(new Node(Start, null));
        vis[Start.row][Start.column] = true;

        // Iterate while the queue
        // is not empty
        while (!q.isEmpty())
        {
            Node   = q.peek();
            int x = cell.first;
            int y = cell.second;

            System.out.print(grid[x][y] + " ");

            q.remove();

            // Go to the adjacent cells
            for(int i = 0; i < 4; i++)
            {
                int adjx = x + dRow[i];
                int adjy = y + dCol[i];

                if (isValid(vis, adjx, adjy))
                {
                    q.add(new pair(adjx, adjy));
                    vis[adjx][adjy] = true;
                }
            }
        }
    }

}
