import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import regexp.Patterns;
import regexp.RegexpHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class RegexpHelperTests {

    private static Set<String> Set(String ... args) {
        return Stream.of(args).collect(Collectors.toSet());
    }

    @DataProvider
    public Object[][] isMatchesData() {
        return new Object[][]{
                {Patterns.MAIL, null, false},
                {Patterns.MAIL, "", false},
                {Patterns.MAIL, "@", false},
                {Patterns.MAIL, "a@", false},
                {Patterns.MAIL, "a@@a", false},
                {Patterns.MAIL, "@a", false},
                {Patterns.MAIL, "a!a@a", false},
                {Patterns.MAIL, "a%@a", false},
                {Patterns.MAIL, "y@df!", false},
                {Patterns.MAIL, "1@a", false},
                {Patterns.MAIL, ".@a", false},
                {Patterns.MAIL, "a.@a", false},
                {Patterns.MAIL, ".a@a", false},
                {Patterns.MAIL, "y@1", false},
                {Patterns.MAIL, "y@1as", false},
                {Patterns.MAIL, "-y@s", false},
                {Patterns.MAIL, "y-@a", false},
                {Patterns.MAIL, "y..a@a", false},
                {Patterns.MAIL, "a@a..a", false},
                {Patterns.MAIL, "y-a@a", true},
                {Patterns.MAIL, "a@a-a", true},
                {Patterns.MAIL, "a@a", true},
                {Patterns.MAIL, "A@B", true},
                {Patterns.MAIL, "a12df@a12", true},
                {Patterns.MAIL, "a@a.ru", true},
                {Patterns.MAIL, "a.b@a", true},
                {Patterns.MAIL, "mail______aaa@a.ru.rt", true},
        };
    }

    @Test(dataProvider = "isMatchesData")
    public void testIsMatch(Patterns pattern, String text, boolean expected) {
        final boolean actual = RegexpHelper.isMatches(text, pattern.getPattern());
        assertEquals(actual, expected, "mail: " + text);
    }

    @DataProvider
    public static Object[][] findUniqueMatchesData() {
        return new Object[][]{
                {Patterns.MAIL, null, Set()},
                {Patterns.MAIL, "", Set()},
                {Patterns.MAIL, "@", Set()},
                {Patterns.MAIL, "Hello world!", Set()},
                {Patterns.MAIL, "Hello@ @hello", Set()},
                {Patterns.MAIL, "a@A", Set("a@A")},
                {Patterns.MAIL, "Hello, my name is Ruslan, my mail is: ruslan1989-mustaev@mail.ru",
                        Set("ruslan1989-mustaev@mail.ru")
                },
                {Patterns.MAIL, "This is list of any mails: hello@my, no,no, adid@b.r, aga#jl, afa@no, ha@, @ga, no@no.no.n.ru, hello@my",
                        Set("adid@b.r", "hello@my", "afa@no", "no@no.no.n.ru", "hello@my")
                },
                {Patterns.MAIL, "This is doubling of any mails: hello@my, hello@my, hello@my, no,no, adid@b.r, aga#jl, afa@no, afa@no," +
                        " ha@, @ga, no@no.no.n.ru, hello@my",
                        Set("hello@my", "adid@b.r", "afa@no", "no@no.no.n.ru", "hello@my")
                },
        };
    }

    @Test(dataProvider = "findUniqueMatchesData")
    public void testFindUniqueMatches(Patterns pattern, String text, Set<String> expectedSet) {
        final Set<String> actualSet = RegexpHelper.findUniqueMatches(text, pattern.getPattern());
        assertEquals(actualSet, expectedSet, "text: " + text);
    }
}
