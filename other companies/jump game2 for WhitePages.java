/*
 White Pages OA
 Can pass all the test cases and all the hidden test cases.

 @author Agraynel Chen
*/
import java.io.*;
import java.util.*;

public class Solution {
    
    private static List<Integer> helper(List<Integer> list) {
        if (list == null || list.size() == 0 || list.get(0) == 0) return null;
        int n = list.size();
        List<Integer> res = new ArrayList<>();
        int step = 0, lastJump = 0, currentJump = 0;
        int i = 0;
        for (i = 0; i < n; i++) {
            if (i > lastJump) {
                lastJump = currentJump;
                res.add(step);
            }
            int sum = i + list.get(i);
			if (sum > currentJump) step = i;
			currentJump = Math.max(currentJump, sum);
        }
        if (i > lastJump && step + list.get(step) >= n - 1) {
            res.add(step);
        }
        if (lastJump >= n || lastJump + list.get(lastJump) >= n) {
            return res;
        } else {
            return null;
        }
    }
    
    private static void failure() {
        System.out.println("failure");
    }
    
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        List<Integer> list = new ArrayList<>();
        try {
            while((line = reader.readLine()) != null){
                line = line.replaceAll("\\s+","");
                int n = -1;
                try {
                    n = Integer.parseInt(line);
                } catch (NumberFormatException exception) {
                    failure();
                    return;
                }
                if (n < 0) {
                    failure();
                    return;
                }
                list.add(n);
            }
        } catch (IOException exception) {
            failure();
            return;
        }
        List<Integer> res = helper(list);
        if (res == null || res.size() == 0) {
            failure();
        } else {
            StringBuilder sb = new StringBuilder();
            for (int num : res) {
                sb.append(num);
                sb.append(", ");
            }
            sb.append("out");
            System.out.println(sb.toString());
        }
    }
}