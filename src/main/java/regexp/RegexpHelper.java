package regexp;

import java.util.regex.*;

public class RegexpHelper {
    public static boolean isEmail(String mail) {
        if (mail == null || mail.isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile(
                "^[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*@[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*$"
        );
        Matcher m = p.matcher(mail);
        return m.matches();
    }
}
