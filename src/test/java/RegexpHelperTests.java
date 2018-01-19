import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import regexp.RegexpHelper;

import static org.testng.Assert.*;

public class RegexpHelperTests {

    @DataProvider
    public Object[][] isMailData() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"@", false},
                {"a@", false},
                {"a@@a", false},
                {"@a", false},
                {"a!a@a", false},
                {"a%@a", false},
                {"y@df!", false},
                {"1@a", false},
                {".@a", false},
                {"a.@a", false},
                {".a@a", false},
                {"y@1", false},
                {"y@1as", false},
                {"-y@s", false},
                {"y-@a", false},
                {"y..a@a", false},
                {"a@a..a", false},
                {"y-a@a", true},
                {"a@a-a", true},
                {"a@a", true},
                {"A@B", true},
                {"a12df@a12", true},
                {"a@a.ru", true},
                {"a.b@a", true},
                {"mail______aaa@a.ru.rt", true},
        };
    }

    @Test(dataProvider = "isMailData")
    public void testIsMail(String mail, boolean expected) {
        final boolean actual = RegexpHelper.isEmail(mail);
        assertEquals(actual, expected, "mail: " + mail);
    }
}
