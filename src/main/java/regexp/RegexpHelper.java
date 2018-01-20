package regexp;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;

public class RegexpHelper {
    public static boolean isMail(String mail) {
        if (mail == null || mail.isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile(
                "^[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*@[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*$"
        );
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    public static Set<String> findUniqueMails(String text) {
        Set<String> mailSet = new HashSet<>();
        if (text == null || text.isEmpty()) {
            return mailSet;
        }
        Pattern p = Pattern.compile(
                "[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+"
        );
        Matcher m = p.matcher(text);
        while (m.find()) {
            mailSet.add(m.group());
        }
        return mailSet;
    }
}
