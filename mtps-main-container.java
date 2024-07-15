import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> paths = new ArrayList<>();
        paths.add("transactions[0].completingAction[0].details.despotionCode");
        paths.add("transactions[0].completingAction[0].details.account.institionNumber");
        paths.add("transactions[0].account.accountNumber");

        System.out.println(getMaxBracketPaths(paths));
    }

    public static List<String> getMaxBracketPaths(List<String> paths) {
        int maxBrackets = 0;
        List<String> maxBracketPaths = new ArrayList<>();

        for (String path : paths) {
            int brackets = countBrackets(path);
            if (brackets > maxBrackets) {
                maxBrackets = brackets;
                maxBracketPaths.clear();
                maxBracketPaths.add(path);
            } else if (brackets == maxBrackets) {
                maxBracketPaths.add(path);
            }
        }

        return maxBracketPaths;
    }

    public static int countBrackets(String path) {
        int count = 0;
        for (char c : path.toCharArray()) {
            if (c == '[') {
                count++;
            }
        }
        return count;
    }
}
