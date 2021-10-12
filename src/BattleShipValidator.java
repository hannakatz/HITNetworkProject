import java.util.*;

public class BattleShipValidator {
    public BattleShipValidator() {

    }

    public int numberOfGoodShips(int[][] myArray) {

        ArrayList<HashSet<Index>> results = new ArrayList<HashSet<Index>>();
        HashMap<Integer, HashSet<Index>> map = new HashMap<>();

        int count = 0;
        TraversableMatrix myMatrixGraph = null;
        DfsVisit<Index> dfsVisit = null;

        List<Index> connectedComponent = null;

        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray.length; j++) {
                if (myArray[i][j] == 1) {
                    myMatrixGraph = new TraversableMatrix(new Matrix(myArray));
                    myMatrixGraph.setStartIndex(new Index(i, j));
                    dfsVisit = new DfsVisit<>();
                    connectedComponent = dfsVisit.traverse(myMatrixGraph);
                    boolean isHorizontalShip = false;
                    boolean isVerticalShip = false;
                    boolean isDiagonalShip = false;
                    boolean b1 = false;
                    boolean b2 = false;
                    boolean b3 = false;
                    boolean b4 = false;

                    Index index = connectedComponent.get(0);
                    try {
                        if (myArray[index.row][index.column + 1] == 1 || myArray[index.row][index.column - 1] == 1) {
                            isHorizontalShip = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }

                    try {
                        if (myArray[index.row + 1][index.column] == 1 || myArray[index.row - 1][index.column] == 1) {
                            isVerticalShip = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }

                    try {
                        if (myArray[index.row + 1][index.column + 1] == 1 || myArray[index.row - 1][index.column - 1] == 1 || myArray[index.row - 1][index.column + 1] == 1 || myArray[index.row + 1][index.column + -1] == 1) {
                            isDiagonalShip = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }

                    if (isHorizontalShip == true && isVerticalShip == true && isDiagonalShip == false) {
                        continue;
                    } else if (isHorizontalShip == true && isVerticalShip == true && isDiagonalShip == true) {

                        if(i + 1 == myArray.length || j + 1 == myArray.length || i - 1 < 0 || j - 1 < 0 ){
                            continue;
                        }
                        if (myArray[index.row + 1][index.column + 1] == 1) {
                            b1 = isPossibleHorizontalShip(myArray, i, j) && isPossibleVerticalShip(myArray, i, j) && isPossibleHorizontalShip(myArray, i + 1, j + 1) &&
                                    isPossibleVerticalShip(myArray, i + 1, j + 1);
                        }

                        if (myArray[index.row - 1][index.column - 1] == 1) {
                            b2 = isPossibleHorizontalShip(myArray, i, j) && isPossibleVerticalShip(myArray, i, j) && isPossibleHorizontalShip(myArray, i - 1, j + 1) &&
                                    isPossibleVerticalShip(myArray, i - 1, j + 1);
                        }

                        if (myArray[index.row + 1][index.column - 1] == 1) {
                            b3 = isPossibleHorizontalShip(myArray, i, j) && isPossibleVerticalShip(myArray, i, j) && isPossibleHorizontalShip(myArray, i + 1, j - 1) &&
                                    isPossibleVerticalShip(myArray, i + 1, j - 1);
                        }

                        if (myArray[index.row - 1][index.column + 1] == 1) {
                            b4 = isPossibleHorizontalShip(myArray, i, j) || isPossibleVerticalShip(myArray, i, j) || isPossibleHorizontalShip(myArray, i - 1, j + 1) ||
                                    isPossibleVerticalShip(myArray, i - 1, j + 1);
                        }
                        if (b1 == true && b2 == true && b3 == true && b4 == true) {
                            count++;
                        }
                    } else if (isHorizontalShip == true) {
                        boolean b = isPossibleHorizontalShip(myArray, i, j);
                        if (b == true) {
                            count++;
                        }
                    } else if (isVerticalShip == true) {
                        boolean b = isPossibleVerticalShip(myArray, i, j);
                        if (b == true) {
                            count++;
                        }
                    }
                    myArray = changeArrayValues(myArray,connectedComponent);
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

    private boolean isPossibleHorizontalShip(int[][] array, int row, int col) {
        if(row - 1 < 0){
            if(array[row + 1][col] == 0){
                return isPossibleHorizontalShipRight(array, row, col + 1) && isPossibleHorizontalShipLeft(array, row, col - 1);
            }
        }
        if(row + 1 > array.length){
            if(array[row - 1][col] == 0){
                return isPossibleHorizontalShipRight(array, row, col + 1) && isPossibleHorizontalShipLeft(array, row, col - 1);
            }
        }
        if(array[row + 1][col] == 0 && array[row - 1][col] == 0) {
            return isPossibleHorizontalShipRight(array, row, col + 1) && isPossibleHorizontalShipLeft(array, row, col - 1);
        }
        return false;
    }

    private boolean isPossibleVerticalShip(int[][] array, int row, int col) {
        if(col + 1 > array.length){
            if(array[row ][col -1] == 0){
                return isPossibleVerticalShipUp(array, row + 1, col) && isPossibleVerticalShipDown(array, row - 1, col);
            }
        }
        if(col -1 < 0){
            if(array[row ][col + 1] == 0){
                return isPossibleVerticalShipUp(array, row + 1, col) && isPossibleVerticalShipDown(array, row - 1, col);
            }
        }

        if(array[row][col + 1] == 0 && array[row][col - 1] == 0) {
            return isPossibleVerticalShipUp(array, row + 1, col) && isPossibleVerticalShipDown(array, row - 1, col);
        }
        return false;
    }

    private boolean isPossibleVerticalShipUp(int[][] array, int row, int col) {
        if (row  == array.length || row  < 0 || array[row][col] == 0 ) {
            return true;
        }
        else if(col + 1 > array.length){
            if(array[row ][col -1] == 0){
                return isPossibleVerticalShipUp(array, row + 1, col );
            }
        }
        else if(col -1 < 0){
            if(array[row ][col + 1] == 0){
               return isPossibleVerticalShipUp(array, row + 1, col );
            }
        }

        else if (array[row][col + 1] == 0 && array[row][col - 1] == 0) {
           return isPossibleVerticalShipUp(array, row + 1, col );
        }
        return false;
    }

    private boolean isPossibleVerticalShipDown(int[][] array, int row, int col) {
        if (row  == array.length || row  < 0 || array[row][col] == 0 ) {
            return true;
        }
        if(col + 1 > array.length){
            if(array[row ][col -1] == 0){
                return isPossibleVerticalShipDown(array, row - 1, col );
            }
        }
        if(col -1 < 0){
            if(array[row ][col + 1] == 0){
               return isPossibleVerticalShipDown(array, row - 1, col );
            }
        }
        if (array[row][col + 1] == 0 && array[row][col - 1] == 0) {
           return isPossibleVerticalShipDown(array, row - 1, col );
        }
        return false;
    }

    private boolean isPossibleHorizontalShipLeft(int[][] array, int row, int col) {
        if (col  == array.length || col  < 0 || array[row][col] == 0 ) {
            return true;
        }
        if(row + 1 > array.length){
            if(array[row ][col -1] == 0){
                return isPossibleHorizontalShipLeft(array, row, col - 1 );
            }
        }
        if(row -1 < 0){
            if(array[row ][col + 1] == 0){
                return isPossibleHorizontalShipLeft(array, row, col - 1 );
            }
        }
        if (array[row + 1][col] == 0 && array[row - 1][col] == 0) {
           return isPossibleHorizontalShipLeft(array, row, col - 1 );
        }
        return false;
    }


    private boolean isPossibleHorizontalShipRight(int[][] array, int row, int col) {
        if (col  == array.length || col  < 0 || array[row][col] == 0 ) {
            return true;
        }
        if(row + 1 > array.length){
            if(array[row ][col -1] == 0){
                return  isPossibleHorizontalShipLeft(array, row, col + 1);
            }
        }
        if(row -1 < 0){
            if(array[row ][col + 1] == 0){
                return  isPossibleHorizontalShipLeft(array, row, col + 1);
            }
        }
        if (array[row + 1][col] == 0 && array[row - 1][col] == 0) {
            return isPossibleHorizontalShipLeft(array, row, col + 1);
        }
        return false;
    }

    public static void main (String[]args){
        int[][] source = {
                {1, 0, 1, 1, 0},
                {1, 0, 1, 1, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 1, 0, 0},
                {1, 0, 1, 0, 0}};

        int matrix = new BattleShipValidator().numberOfGoodShips(source);
    }


}

