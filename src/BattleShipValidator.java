import java.util.*;

public class BattleShipValidator {
    public BattleShipValidator() {

    }

    public int numberOfGoodShips(Matrix myArray) {

        ArrayList<HashSet<Index>> results = new ArrayList<HashSet<Index>>();
        HashMap<Integer, HashSet<Index>> map = new HashMap<>();

        int count = 0;
        TraversableMatrix myMatrixGraph = null;
        DfsVisit<Index> dfsVisit = null;

        List<Index> connectedComponent = null;

        for (int i = 0; i < myArray.primitiveMatrix.length; i++) {
            for (int j = 0; j < myArray.primitiveMatrix.length; j++) {
                if (myArray.primitiveMatrix[i][j] == 1) {
                    myMatrixGraph = new TraversableMatrix(new Matrix(myArray.primitiveMatrix));
                    myMatrixGraph.setStartIndex(new Index(i, j));
                    dfsVisit = new DfsVisit<>();
                    connectedComponent = dfsVisit.traverse(myMatrixGraph);
                    if(connectedComponent.size() == 1){
                        continue;
                    }
                    int maxX = 0;
                    int maxY = 0;
                    int minX = connectedComponent.get(0).column;
                    int minY = connectedComponent.get(0).row;
                    for (Index index : connectedComponent) {
                        if (index.row > maxY) {
                            maxY = index.row;
                        }
                        if (index.row < minY) {
                            minY = index.row;
                        }
                        if (index.column > maxX) {
                            maxX = index.column;
                        }
                        if (index.column < minX) {
                            minX = index.column;
                        }
                    }
                    boolean isShip = true;
                    for (int x = minX; x <= maxX; x++) {
                        for (int y = minY; y <= maxY; y++) {
                            if (myArray.primitiveMatrix[y][x] == 0) {
                                isShip = false;
                                break;
                            }
                        }
                    }

                    if(isShip == true){
                        count++;
                    }
                    myArray.primitiveMatrix = changeArrayValues(myArray.primitiveMatrix, connectedComponent);
                }
            }
        }

        System.out.println(count);
        return count;
    }

    private int[][] changeArrayValues(int array[][],List<Index> removeIndex){
        for  (int r=0 ; r < removeIndex.size();r++)
        {
            Index  temp = removeIndex.get(r);
            array[temp.row][temp.column] = 0;
        }
        return array;
    }

    public static void main (String[]args){
        int[][] source = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1}};

        int matrix = new BattleShipValidator().numberOfGoodShips(new Matrix(source));
    }
}

