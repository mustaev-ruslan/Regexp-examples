import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import regexp.RegexpHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class RegexpHelperTests {

    @SafeVarargs
    private static <T> Set<T> Set(T ... varargs) {
        return Stream.of(varargs).collect(Collectors.toSet());
    }

    @DataProvider
    public Object[][] isMatchesData() {
        return new Object[][]{
                {"MAIL", null, false},
                {"MAIL", "", false},
                {"MAIL", "@", false},
                {"MAIL", "a@", false},
                {"MAIL", "a@@a", false},
                {"MAIL", "@a", false},
                {"MAIL", "a!a@a", false},
                {"MAIL", "a%@a", false},
                {"MAIL", "y@df!", false},
                {"MAIL", "1@a", false},
                {"MAIL", ".@a", false},
                {"MAIL", "a.@a", false},
                {"MAIL", ".a@a", false},
                {"MAIL", "y@1", false},
                {"MAIL", "y@1as", false},
                {"MAIL", "-y@s", false},
                {"MAIL", "y-@a", false},
                {"MAIL", "y..a@a", false},
                {"MAIL", "a@a..a", false},
                {"MAIL", "y-a@a", true},
                {"MAIL", "a@a-a", true},
                {"MAIL", "a@a", true},
                {"MAIL", "A@B", true},
                {"MAIL", "a12df@a12", true},
                {"MAIL", "a@a.ru", true},
                {"MAIL", "a.b@a", true},
                {"MAIL", "mail______aaa@a.ru.rt", true},
        };
    }

    @Test(dataProvider = "isMatchesData")
    public void testIsMatch(String patternName, String text, boolean expected) {
        final boolean actual = RegexpHelper.isMatches(text, RegexpHelper.getPattern(patternName));
        assertEquals(actual, expected, "mail: " + text);
    }

    @DataProvider
    public static Object[][] findUniqueMatchesData() {
        return new Object[][]{
                {"MAIL", null, Set()},
                {"MAIL", "", Set()},
                {"MAIL", "@", Set()},
                {"MAIL", "Hello world!", Set()},
                {"MAIL", "Hello@ @hello", Set()},
                {"MAIL", "a@A", Set("a@A")},
                {"MAIL", "Hello, my name is Ruslan, my mail is: ruslan1989-mustaev@mail.ru",
                        Set("ruslan1989-mustaev@mail.ru")
                },
                {"MAIL", "This is list of any mails: hello@my, no,no, adid@b.r, aga#jl, afa@no, ha@, @ga, no@no.no.n.ru, hello@my",
                        Set("adid@b.r", "hello@my", "afa@no", "no@no.no.n.ru", "hello@my")
                },
                {"MAIL", "This is doubling of any mails: hello@my, hello@my, hello@my, no,no, adid@b.r, aga#jl, afa@no, afa@no," +
                        " ha@, @ga, no@no.no.n.ru, hello@my",
                        Set("hello@my", "adid@b.r", "afa@no", "no@no.no.n.ru", "hello@my")
                },
        };
    }

    @Test(dataProvider = "findUniqueMatchesData")
    public void testFindUniqueMatches(String patternName, String text, Set<String> expectedSet) {
        final Set<String> actualSet = RegexpHelper.findUniqueMatches(text, RegexpHelper.getPattern(patternName));
        assertEquals(actualSet, expectedSet, "text: " + text);
    }
}
