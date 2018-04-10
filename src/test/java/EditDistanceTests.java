import org.junit.Test;

/**
 * Created by karthik on 30/11/17.
 */

public class EditDistanceTests {

    @Test
    public void testEditDistance() {
        String source = "nivetha"; String target = "niveditha";

        System.out.println(findEditDistance(source, target));
    }

    private static int findEditDistance(String source, String target) {
        if(source == null || source.length() == 0) {
            return target != null ? target.length() : 0;
        }

        int editDistance = 0;
        int columnCount = source.length() + 1;
        int rowCount = target.length() + 1;

        // Create the table.
        int[][] memoizationTable = new int[rowCount][columnCount];
        for(int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            memoizationTable[0][columnIndex] = columnIndex + 1;
        }

        for(int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            memoizationTable[rowIndex][0] = rowIndex;
        }

        for(int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for(int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                char currentSourceCharacter = source.charAt(columnIndex - 1);
                char currentTargetCharacter = target.charAt(rowIndex - 1);

                int xLess_y = memoizationTable[rowIndex][columnIndex - 1];
                int x_yLess = memoizationTable[rowIndex - 1][columnIndex];
                int xLess_yLess = memoizationTable[rowIndex - 1][columnIndex - 1];

                if(currentSourceCharacter == currentTargetCharacter) {
                    int deletionCost = Math.min(xLess_y, x_yLess) + 1;
                    memoizationTable[rowIndex][columnIndex] = Math.min(xLess_yLess, deletionCost) == xLess_yLess ? xLess_yLess : deletionCost;
                } else {
                    int deletionCost = Math.min(xLess_y, x_yLess) + 1;
                    int replacementCost = xLess_yLess + 1;
                    memoizationTable[rowIndex][columnIndex] = Math.min(replacementCost, deletionCost);
                }
            }
        }

        for(int i = 0; i < rowCount; i++) {
            for(int j = 0; j < columnCount; j++)
                System.out.println(memoizationTable[i][j]);
            System.out.println("\n");
        }

        return memoizationTable[rowCount - 1][columnCount - 1];
    }
}
