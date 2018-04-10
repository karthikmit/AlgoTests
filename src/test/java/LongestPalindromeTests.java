import org.junit.Test;

/**
 * Created by karthik on 10/04/18.
 */
public class LongestPalindromeTests {

    @Test
    public void findLPTest() {
        //String testString = "authors of malayalam think that malayali novelsslevon are great.";
        String testString = "allmalayalamlla ssssss authors of malayalam think that malayali novelsslevon are great.";
        System.out.println(findLP(testString));
    }

    private String findLP(String input) {
        char[] chars = input.toCharArray();

        // Longest palindrome found so far and edge palindrome pairs. Pair contains start and end indices of the palindrome.
        int[] longestPalindrome = {0,0}, edgePalindrome = {0, 0};
        int longestPalindromeLength = 1; int currentEdgePalindromeLength = 1;

        // sequence of Repeated letters
        boolean runOfChars = false;

        if(chars.length == 1) return input.substring(0, 1);
        if(chars.length >= 2) {
            if (chars[0] == chars[1]) {
                longestPalindrome[0] = 0; longestPalindrome[1] = 1;
                edgePalindrome[0] = 0; edgePalindrome[1] = 1;
                runOfChars = true;
            } else {
                longestPalindrome[0] = 1; longestPalindrome[1] = 1;
                edgePalindrome[0] = 1; edgePalindrome[1] = 1;
                runOfChars = false;
            }
        }


        //  Edge palindrome may not be the longest so far, but still capable of producing longest when we traverse further.
        for(int index = 2; index < chars.length; index++) {
            char newCharToExpand = chars[index];
            int leftIndexOfEdgePalindrome = edgePalindrome[0];
            int rightIndexOfEdgePalindrome = edgePalindrome[1];

            // Check the EP can be expanded ...
            if(leftIndexOfEdgePalindrome > 0 && chars[leftIndexOfEdgePalindrome - 1] == newCharToExpand) {
                edgePalindrome[0] = leftIndexOfEdgePalindrome - 1;
                edgePalindrome[1] = index;

                longestPalindromeLength = changeLPLengthIfNeeded(longestPalindrome, edgePalindrome, longestPalindromeLength);
                runOfChars = false;
            } else if(runOfChars && chars[leftIndexOfEdgePalindrome] == newCharToExpand) {
                edgePalindrome[1] = index;

                longestPalindromeLength = changeLPLengthIfNeeded(longestPalindrome, edgePalindrome, longestPalindromeLength);
            } else if(chars[rightIndexOfEdgePalindrome] == newCharToExpand) {
                // Mark the new character as the edge palindrome.
                edgePalindrome[0] = edgePalindrome[1];
                edgePalindrome[1] = index;

                longestPalindromeLength = changeLPLengthIfNeeded(longestPalindrome, edgePalindrome, longestPalindromeLength);
                runOfChars = true;
            } else {
                // Mark the new character as the edge palindrome.
                edgePalindrome[0] = index; edgePalindrome[1] = index;
                runOfChars = false;
            }
        }

        return input.substring(longestPalindrome[0], longestPalindrome[1] + 1);
    }

    private int changeLPLengthIfNeeded(int[] lp, int[] ep, int lpLength) {
        int epLength;
        epLength = ep[1] - ep[0] + 1;
        if(epLength > lpLength) {
            lp[0] = ep[0];
            lp[1] = ep[1];
            lpLength = epLength;
        }
        return lpLength;
    }
}
