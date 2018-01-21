package regexp;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;

public class RegexpHelper {

    public static boolean isMatches(String text, Pattern pattern) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return pattern.matcher(text).matches();
    }

    public static Set<String> findUniqueMatches(String text, Pattern pattern) {
        Set<String> matchesSet = new HashSet<>();
        if (text == null || text.isEmpty()) {
            return matchesSet;
        }
        Matcher m = pattern.matcher(text);
        while (m.find()) {
            matchesSet.add(m.group());
        }
        return matchesSet;
    }
}
