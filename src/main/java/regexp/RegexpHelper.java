package regexp;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;

public class RegexpHelper {

    private static Pattern mailPattern = Pattern.compile(
            "[a-zA-Z]([.-]?[a-zA-`Z0-9_]+)*@[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*"
    );

    public static boolean isMail(String mail) {
        if (mail == null || mail.isEmpty()) {
            return false;
        }
        return mailPattern.matcher(mail).matches();
    }

    public static Set<String> findUniqueMails(String text) {
        Set<String> mailSet = new HashSet<>();
        if (text == null || text.isEmpty()) {
            return mailSet;
        }
        Matcher m = mailPattern.matcher(text);
        while (m.find()) {
            mailSet.add(m.group());
        }
        return mailSet;
    }
}
