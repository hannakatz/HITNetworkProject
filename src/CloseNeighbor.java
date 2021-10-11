

import java.util.*;

public class CloseNeighbor {
    public CloseNeighbor(){}

    public ArrayList<HashSet<Index>> allConnectedComponents(int[][] myArray){

        ArrayList<HashSet<Index>> results = new ArrayList<HashSet<Index>>();
        HashMap<Integer, HashSet<Index>> map = new HashMap<>();

        TraversableMatrix myMatrixGraph =null;
        DfsVisit<Index> dfsVisit = null;

        List<Index> connectedComponent =null;

        for (int i=0;i<myArray.length;i++) {
            for (int j = 0; j < myArray.length; j++) {
                if (myArray[i][j] == 1) {
                    myMatrixGraph = new TraversableMatrix(new Matrix(myArray));
                    myMatrixGraph.setStartIndex(new Index(i,j));
                    dfsVisit = new DfsVisit<>();
                    connectedComponent = dfsVisit.traverse(myMatrixGraph);
                    map.put(connectedComponent.size(),new HashSet<Index>(connectedComponent));
                    myArray = changeArrayValues(myArray,connectedComponent);

                }
            }
        }

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(x->results.add(x.getValue()));

        System.out.println(results);
        return results;
    }
    private int[][] changeArrayValues(int array[][],List<Index> removeIndex){
        for  (int r=0 ; r < removeIndex.size();r++)
        {
            Index  temp = removeIndex.get(r);
            array[temp.row][temp.column] = 0;
        }
        return array;
    }

    public static void main(String[] args) {
        int[][] source = {
                {1, 0, 0,1,0},
                {1, 0, 0,1,0},
                {1, 0, 1,1,0},
                {1, 0, 1,0,0},
                {1, 0, 1,0,1}};

        ArrayList<HashSet<Index>> matrix = new CloseNeighbor().allConnectedComponents(source);
    }
}


