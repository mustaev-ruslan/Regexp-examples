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
                {"NAME_SURRNAME", "Иван    Иванов", true},
                {"NAME_SURRNAME", "Иван", false},
                {"NAME_SURRNAME", "Иванов", false},
                {"NAME_SURRNAME", "Иван Иванов-Петров", true},
                {"NAME_SURRNAME", "Иван Иванов Иванович", false},
                {"NAME_SURRNAME", "И И", false},
                {"PAGE_ADDRESS", "http://site", true},
                {"PAGE_ADDRESS", "http://site/", true},
                {"PAGE_ADDRESS", "http://site/home", true},
                {"PAGE_ADDRESS", "http://site/home/video", true},
                {"PAGE_ADDRESS", "http://www.site.com/home/video/main.jsp", true},
                {"PAGE_ADDRESS", "http://www.site.com/home/video/main.jsp/", false},
                {"PAGE_ADDRESS", "www.site.com/home/video/main.jsp", false},
                {"PAGE_ADDRESS", "main.jsp", false},
                {"PAGE_ADDRESS", "ftp://www.site.com/home/video/main.jsp", false},
                {"PAGE_ADDRESS", "https://www.site.com/home/video/main.jsp", true},
                {"DIGITAL_CLOCK", "00:00", true},
                {"DIGITAL_CLOCK", "01:10", true},
                {"DIGITAL_CLOCK", "23:59", true},
                {"DIGITAL_CLOCK", "24:00", false},
                {"DIGITAL_CLOCK", "00:60", false},
                {"DIGITAL_CLOCK", "99:99", false},
                {"DIGITAL_CLOCK", "19:30", true},
                {"IP", "0.0.0.0", true},
                {"IP", "1.1.1.1", true},
                {"IP", "127.0.0.1", true},
                {"IP", "255.255.255.255", true},
                {"IP", "01.0.0.0", false},
                {"IP", "256.0.0.0", false},
                {"IP", "0.0.0.0.0", false},
                {"IP", "199.199.199.99", true},
                {"AUTO_NUMBER", "С065МК78RUS", true},
                {"AUTO_NUMBER", "C065MK78RUS", true},
                {"AUTO_NUMBER", "с065мк78RUS", true},
                {"AUTO_NUMBER", "c065mk78RUS", true},
                {"AUTO_NUMBER", "A777AA02RUS", true},
                {"AUTO_NUMBER", "X999XX799RUS", true},
                {"AUTO_NUMBER", "С000МК78RUS", false},
                {"AUTO_NUMBER", "С065RК78RUS", false},
                {"AUTO_NUMBER", "Я065МК78RUS", false},
                {"AUTO_NUMBER", "С065МК78UKR", false},
                {"AUTO_NUMBER", "С065МК01RUS", true},
                {"AUTO_NUMBER", "С065МК00RUS", false},
                {"AUTO_NUMBER", "С065МК101RUS", true},
                {"AUTO_NUMBER", "С065МК201RUS", false},
                {"AUTO_NUMBER", "С065МК100RUS", false},
                {"AUTO_NUMBER", "С065МК699RUS", false},
                {"AUTO_NUMBER", "С065МК777RUS", true},
        };
    }

    @Test(dataProvider = "isMatchesData")
    public void testIsMatch(String patternName, String text, boolean expected) {
        final boolean actual = RegexpHelper.isMatches(text, RegexpHelper.getPattern(patternName));
        assertEquals(actual, expected, "text: " + text);
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
                {"NAME_SURRNAME", "Иванов Иван Иванович, разрешите должить. Пётр    Петров-Водкин прибыл! А Н Р",
                        Set("Иванов Иван", "Пётр    Петров-Водкин")
                },
        };
    }

    @Test(dataProvider = "findUniqueMatchesData")
    public void testFindUniqueMatches(String patternName, String text, Set<String> expectedSet) {
        final Set<String> actualSet = RegexpHelper.findUniqueMatches(text, RegexpHelper.getPattern(patternName));
        assertEquals(actualSet, expectedSet, "text: " + text);
    }
}
