import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import regexp.RegexpHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final boolean actual = RegexpHelper.isMail(mail);
        assertEquals(actual, expected, "mail: " + mail);
    }

    @DataProvider
    public static Object[][] findUniqueMailData() {
        return new Object[][]{
                {null, Collections.EMPTY_SET},
                {"", Collections.EMPTY_SET},
                {"@", Collections.EMPTY_SET},
                {"Hello world!", Collections.EMPTY_SET},
                {"Hello@ @hello", Collections.EMPTY_SET},
                {"a@A", Collections.singleton("a@A")},
                {"Hello, my name is Ruslan, my mail is: ruslan1989-mustaev@mail.ru",
                        Collections.singleton("ruslan1989-mustaev@mail.ru")
                },
                {"This is list of any mails: hello@my, no,no, adid@b.r, aga#jl, afa@no, ha@, @ga, no@no.no.n.ru, hello@my",
                        Stream.of("adid@b.r", "hello@my", "afa@no", "no@no.no.n.ru", "hello@my").collect(Collectors.toSet())
                },
                {"This is doubling of any mails: hello@my, hello@my, hello@my, no,no, adid@b.r, aga#jl, afa@no, afa@no," +
                        " ha@, @ga, no@no.no.n.ru, hello@my",
                        Stream.of("hello@my", "adid@b.r", "afa@no", "no@no.no.n.ru", "hello@my").collect(Collectors.toSet())
                },
        };
    }

    @Test(dataProvider = "findUniqueMailData")
    public void testFindUniqueMails(String text, Set<String> expectedSet) {
        final Set<String> actualSet = RegexpHelper.findUniqueMails(text);
        assertEquals(actualSet, expectedSet, "text: " + text);
    }
}
