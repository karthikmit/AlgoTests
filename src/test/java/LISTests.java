import org.junit.Test;

/**
 * Created by karthik on 18/03/18.
 */
public class LISTests {

    int longestIncreasingSubsequence(int[] arr) {
        if(arr == null) return 0;
        int lis[] = new int[arr.length];

        for(int i = 0; i < arr.length; i++) {
            lis[i] = 1;
            for(int j = i-1; j >= 0; j--) {
                if(arr[j] < arr[i]) {
                    if(lis[j] + 1 > lis[i]) {
                        lis[i] = lis[j] + 1;
                    }
                }
            }
        }

        int maxLisLength = 0;
        for(int i = 0; i < lis.length; i++) {
            if(lis[i] > maxLisLength) {
                maxLisLength = lis[i];
            }
        }
        return maxLisLength;
    }

    @Test
    public void mainDriver() {
        int n = 8;
        int[] arr = {2, 3, 4, 7, 1, 5, 6, 8};
        System.out.println("Input :: ");
        for(int arr_i = 0; arr_i < n; arr_i++) {
            System.out.print(arr[arr_i] + " ");
        }
        System.out.println("");
        int result = longestIncreasingSubsequence(null);
        System.out.println(result);
    }
}
