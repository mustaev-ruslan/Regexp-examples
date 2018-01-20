package regexp;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;

public class RegexpHelper {

    private static Matcher mailMatcher(String text) {
        Pattern p = Pattern.compile(
                "[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*@[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*"
        );
        return p.matcher(text);
    }

    public static boolean isMail(String mail) {
        if (mail == null || mail.isEmpty()) {
            return false;
        }
        return mailMatcher(mail).matches();
    }

    public static Set<String> findUniqueMails(String text) {
        Set<String> mailSet = new HashSet<>();
        if (text == null || text.isEmpty()) {
            return mailSet;
        }
        Matcher m = mailMatcher(text);
        while (m.find()) {
            mailSet.add(m.group());
        }
        return mailSet;
    }
}
