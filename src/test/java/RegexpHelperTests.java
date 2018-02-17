import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import regexp.RegexpHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class RegexpHelperTests {

    @SafeVarargs
    private static <T> Set<T> Set(T... varargs) {
        return Stream.of(varargs).collect(Collectors.toSet());
    }

    @DataProvider
    public Object[][] isMatchesData() {
        return new Object[][]{
                {"MAIL", "y-a@a", true},
                {"MAIL", "a@a-a", true},
                {"MAIL", "a@a", true},
                {"MAIL", "A@B", true},
                {"MAIL", "a12df@a12", true},
                {"MAIL", "a@a.ru", true},
                {"MAIL", "a.b@a", true},
                {"MAIL", "mail______aaa@a.ru.rt", true},
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

                {"NAME_SURRNAME", "Иван    Иванов", true},
                {"NAME_SURRNAME", "Иван Иванов-Петров", true},
                {"NAME_SURRNAME", "Иван", false},
                {"NAME_SURRNAME", "Иванов", false},
                {"NAME_SURRNAME", "Иван Иванов Иванович", false},
                {"NAME_SURRNAME", "И И", false},

                {"PAGE_ADDRESS", "http://site", true},
                {"PAGE_ADDRESS", "http://site/", true},
                {"PAGE_ADDRESS", "http://site/home", true},
                {"PAGE_ADDRESS", "http://site/home/video", true},
                {"PAGE_ADDRESS", "http://www.site.com/home/video/main.jsp", true},
                {"PAGE_ADDRESS", "https://www.site.com/home/video/main.jsp", true},
                {"PAGE_ADDRESS", "http://www.site.com/home/video/main.jsp/", false},
                {"PAGE_ADDRESS", "www.site.com/home/video/main.jsp", false},
                {"PAGE_ADDRESS", "main.jsp", false},
                {"PAGE_ADDRESS", "ftp://www.site.com/home/video/main.jsp", false},

                {"DIGITAL_CLOCK", "00:00", true},
                {"DIGITAL_CLOCK", "01:10", true},
                {"DIGITAL_CLOCK", "23:59", true},
                {"DIGITAL_CLOCK", "19:30", true},
                {"DIGITAL_CLOCK", "24:00", false},
                {"DIGITAL_CLOCK", "00:60", false},
                {"DIGITAL_CLOCK", "99:99", false},

                {"IP", "0.0.0.0", true},
                {"IP", "1.1.1.1", true},
                {"IP", "127.0.0.1", true},
                {"IP", "255.255.255.255", true},
                {"IP", "199.199.199.99", true},
                {"IP", "01.0.0.0", false},
                {"IP", "256.0.0.0", false},
                {"IP", "0.0.0.0.0", false},

                {"AUTO_NUMBER", "С065МК78RUS", true},
                {"AUTO_NUMBER", "C065MK78RUS", true},
                {"AUTO_NUMBER", "с065мк78RUS", true},
                {"AUTO_NUMBER", "c065mk78RUS", true},
                {"AUTO_NUMBER", "A777AA02RUS", true},
                {"AUTO_NUMBER", "X999XX799RUS", true},
                {"AUTO_NUMBER", "С065МК01RUS", true},
                {"AUTO_NUMBER", "С065МК101RUS", true},
                {"AUTO_NUMBER", "С065МК777RUS", true},
                {"AUTO_NUMBER", "С000МК78RUS", false},
                {"AUTO_NUMBER", "С065RК78RUS", false},
                {"AUTO_NUMBER", "Я065МК78RUS", false},
                {"AUTO_NUMBER", "С065МК78UKR", false},
                {"AUTO_NUMBER", "С065МК00RUS", false},
                {"AUTO_NUMBER", "С065МК201RUS", false},
                {"AUTO_NUMBER", "С065МК100RUS", false},
                {"AUTO_NUMBER", "С065МК699RUS", false},

                {"DOUBLE_N", "Инна", true},
                {"DOUBLE_N", "ннада", true},
                {"DOUBLE_N", "привет", false},
                {"DOUBLE_N", "очки ннада?", false},

                {"ADJECTIVE", "клёвый", true},
                {"ADJECTIVE", "зелёный", true},
                {"ADJECTIVE", "индуистский", true},
                {"ADJECTIVE", "луной", true},
                {"ADJECTIVE", "голубой", true},
                {"ADJECTIVE", "кривой", true},
                {"ADJECTIVE", "кривая", true},
                {"ADJECTIVE", "фиолетовую", true},
                {"ADJECTIVE", "Привет", false},
                {"ADJECTIVE", "кровь", false},
                {"ADJECTIVE", "бельё", false},

                {"APPLE", "зеленое яблоко", true},
                {"APPLE", "Зелёное яблоко", true},
                {"APPLE", "красное яблоко", true},
                {"APPLE", "зеленое яблокохххх", false},

                {"NATURAL", "1", true},
                {"NATURAL", "2", true},
                {"NATURAL", "99999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999", true},
                {"NATURAL", "90", true},
                {"NATURAL", "100000000000", true},
                {"NATURAL", "+777", true},
                {"NATURAL", "красное яблоко", false},
                {"NATURAL", "-1", false},
                {"NATURAL", "0", false},
                {"NATURAL", "infinity", false},
                {"NATURAL", "1.1", false},
                {"NATURAL", "1,1", false},
                {"NATURAL", "1 1", false},
                {"NATURAL", "5!", false},
                {"NATURAL", "00000001", false},

                {"INTEGER", "-1", true},
                {"INTEGER", "0", true},
                {"INTEGER", "1", true},
                {"INTEGER", "2", true},
                {"INTEGER", "99999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999", true},
                {"INTEGER", "90", true},
                {"INTEGER", "100000000000", true},
                {"INTEGER", "+50", true},
                {"INTEGER", "-50", true},
                {"INTEGER", "-9999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999", true},
                {"INTEGER", "красное яблоко", false},
                {"INTEGER", "infinity", false},
                {"INTEGER", "1.1", false},
                {"INTEGER", "1,1", false},
                {"INTEGER", "1 1", false},
                {"INTEGER", "5!", false},
                {"INTEGER", "00000001", false},
                {"INTEGER", "-0.5", false},
                {"INTEGER", "-0,5", false},
                {"INTEGER", "-infinity", false},

                {"RATIO", "-1", true},
                {"RATIO", "0", true},
                {"RATIO", "1.1", true},
                {"RATIO", "1,1", true},
                {"RATIO", "1", true},
                {"RATIO", "2", true},
                {"RATIO", "99999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999", true},
                {"RATIO", "90", true},
                {"RATIO", "100000000000", true},
                {"RATIO", "-0.5", true},
                {"RATIO", "-0,5", true},
                {"RATIO", "+50", true},
                {"RATIO", "-50", true},
                {"RATIO", "-9999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999" +
                        "999999999999999999999999999999999999999999999999999999999999", true},
                {"RATIO", "-0.33333333333333333333333333333333333", true},
                {"RATIO", "-0,000000000000000000000000000000001", true},
                {"RATIO", "0.33333333333333333333333333333333330001", true},
                {"RATIO", "красное яблоко", false},
                {"RATIO", "infinity", false},
                {"RATIO", "1 1", false},
                {"RATIO", "5!", false},
                {"RATIO", "00000001", false},
                {"RATIO", "-infinity", false},
                {"RATIO", "+-0.33333333333333333333333333333333333", false},
                {"RATIO", "0.3333333333333333333333333333333333000", false},
                {"RATIO", "01.3333333333333333333333333333333333000", false},
                {"RATIO", "-01.3333333333333333333333333333333333000", false},

                {"DIFFERENT", "AB", true},
                {"DIFFERENT", "AA", false},
                {"DIFFERENT", "A", false},
                {"DIFFERENT", "AABB", false},
                {"DIFFERENT", "ABAB", false},
                {"DIFFERENT", "ABC", false},

                {"ANBN", "aaaaaa", true},
                {"ANBN", "CCAA", true},
                {"ANBN", "AAACCC", true},
                {"ANBN", "CCBB", true},
                {"ANBN", "AA", true},
                {"ANBN", "AAAA", true},
                {"ANBN", "AAAAAA", true},
                {"ANBN", "AAAAAAAA", true},
                {"ANBN", "AAAAAbbbbb", true},
                {"ANBN", "0000011111", true},
                {"ANBN", "AAAAAABBBBBB", true},
                {"ANBN", "AAAAAAABBBBBBB", true},
                {"ANBN", "AAAA", true},
                {"ANBN", "AABB", true},
                {"ANBN", "AB", true},
                {"ANBN", "AAAABBBB", true},
                {"ANBN", "AAABBB", true},
                {"ANBN", "AAAAABBBBB", true},
                {"ANBN", "AAAAAAAABBBBBBBB", true},
                {"ANBN", "aaaaa", false},
                {"ANBN", "AAABB", false},
                {"ANBN", "AABC", false},
                {"ANBN", "ABCC", false},
                {"ANBN", "AAAAAB", false},
                {"ANBN", "AABBB", false},
                {"ANBN", "DFG", false},
                {"ANBN", "DDFFGG", false},
                {"ANBN", "AAA", false},
                {"ANBN", "AAABB", false},
                {"ANBN", "AABBB", false},

                {"NUM_EQUALITY", "1+2=3", true},
                {"NUM_EQUALITY", "1+2=4", true},
                {"NUM_EQUALITY", "1*2=3", true},
                {"NUM_EQUALITY", "1-2=3", true},
                {"NUM_EQUALITY", "1/2=3", true},
                {"NUM_EQUALITY", "1+2*5678/777-34=9993", true},
                {"NUM_EQUALITY", "1=2", true},
                {"NUM_EQUALITY", "-2=2", true},
                {"NUM_EQUALITY", "-1+2=3", true},
                {"NUM_EQUALITY", "1+2=-3", true},
                {"NUM_EQUALITY", "182=3", true},
                {"NUM_EQUALITY", "1 +2=3", false},
                {"NUM_EQUALITY", "1--2=3", false},
                {"NUM_EQUALITY", "1+2-=3", false},
                {"NUM_EQUALITY", "1+2=1+2", false},
                {"NUM_EQUALITY", "*2=2", false},

                {"PHONE", "89171234567", true},
                {"PHONE", "+79171234567", true},
                {"PHONE", "8(917)1234567", true},
                {"PHONE", "+7(917)1234567", true},
                {"PHONE", "8(9-17)1234567", true},
                {"PHONE", "+7(917)123-45-67", true},
                {"PHONE", "+7(917)1-2345-67", true},
                {"PHONE", "1-12", true},
                {"PHONE", "89171-234567", true},
                {"PHONE", "01", true},
                {"PHONE", "112", true},
                {"PHONE", "(8)9171234567", true},
                {"PHONE", "+(7)9171-234567", true},
                {"PHONE", "(+7-917)1-234567", true},
                {"PHONE", "+7917123456(7)", true},
                {"PHONE", "8(917)(123)4567", false},
                {"PHONE", "-89171234567", false},
                {"PHONE", "8+9171234567", false},
                {"PHONE", "89171234567-", false},
                {"PHONE", "aaa", false},
                {"PHONE", "+(+7)9171234567", false},
                {"PHONE", "+7()9171234567", false},
                {"PHONE", "+7917)1(234567", false},
                {"PHONE", "+(7)-9171234567", false},
                {"PHONE", "+79171(-23)4567", false},

                {"HEX", "0", true},
                {"HEX", "9", true},
                {"HEX", "A", true},
                {"HEX", "F", true},
                {"HEX", "10", true},
                {"HEX", "A0", true},
                {"HEX", "F0", true},
                {"HEX", "AF", true},
                {"HEX", "FF", true},
                {"HEX", "9999999999", true},
                {"HEX", "1000000000", true},
                {"HEX", "FFFFFFFFFF", true},
                {"HEX", "a", true},
                {"HEX", "d", true},
                {"HEX", "aF", true},
                {"HEX", "-AB", true},
                {"HEX", "G", false},
                {"HEX", "0A", false},
                {"HEX", "01", false},
                {"HEX", "AAA.A", false},
                {"HEX", "-0", false},

                {"CAT", "Здесь живёт кот Василий", true},
                {"CAT", "Лучший КОТ на свете", true},
                {"CAT", "Здесь нет кота", false},
                {"CAT", "Моя икота не проходит", false},
                {"CAT", "Добро пожаловать в котёл!", false},

                {"DATE", "01.01.1000", true},
                {"DATE", "31.02.2000", true},
                {"DATE", "01.12.1000", true},
                {"DATE", "31.12.9999", true},
                {"DATE", "01.01.0001", true},
                {"DATE", "32.01.1000", false},
                {"DATE", "1.01.1000", false},
                {"DATE", "01.13.1000", false},
                {"DATE", "01.01.999", false},
                {"DATE", "00.01.1000", false},
                {"DATE", "01.00.1000", false},
                {"DATE", "01.01.0000", false},
        };
    }

    @Ignore
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

                {"CA_T", "Раз кот два икота три КонгЛомерат четыре компотъ пять кобальт шесть компот",
                        Set("кот", "КонгЛомерат", "кобальт", "компот")
                },

                {"DOUBLE_WORD_SENTENCE", "Раз     рАз, два три, четыре четыре пята пятая",
                        Set("Раз     рАз", "четыре четыре")
                },
        };
    }

    @Ignore
    @Test(dataProvider = "findUniqueMatchesData")
    public void testFindUniqueMatches(String patternName, String text, Set<String> expectedSet) {
        final Set<String> actualSet = RegexpHelper.findUniqueMatches(text, RegexpHelper.getPattern(patternName));
        assertEquals(actualSet, expectedSet, "text: " + text);
    }

    @Test
    public void testGroupDigits() {
        String inputText = "В этом тексте есть цифры и числа: 1, 123, 1234, 12345, 123456, 1234567, " +
                "1234.1234, 0.1, 0.1234, -12345.12345678, 12345678901234567890";
        String expectedText = "В этом тексте есть цифры и числа: 1, 123, 1 234, 12 345, 123 456, 1 234 567, " +
                "1 234.123 4, 0.1, 0.123 4, -12 345.123 456 78, 12 345 678 901 234 567 890";
        String actualText = RegexpHelper.groupDigits(inputText);
        assertEquals(actualText, expectedText);
    }

    @Test
    public void testClearSpaces() throws Exception {
        String inputText = "             Тестовый текст        абвг абвг       гджз     ";
        String expectedText = "Тестовый текст абвг абвг гджз";
        String actualText = RegexpHelper.clearSpaces(inputText);
        assertEquals(actualText, expectedText);
    }

    @Test
    public void testJavaVersionReplace() throws Exception {
        String inputText = "Версии Java, которые устарели: " +
                "Java 1.0, Java 1.1, Java 1.2, Java 1.3, Java 1.4, Java 5.0, Java 6.0. " +
                "Версии, которые не существуют: Java 1.55, Java 2.5, Java 11.3.";
        String expectedText = "Версии Java, которые устарели: " +
                "Java 1.0, Java 1.1, Java 2.0, Java 3.0, Java 4.0, Java 5.0, Java 6.0. " +
                "Версии, которые не существуют: Java 1.55, Java 2.5, Java 11.3.";
        String actualText = RegexpHelper.javaVersionReplace(inputText);
        assertEquals(actualText, expectedText);
    }

    @Test
    public void testFahrenheitToCelsius() throws Exception {
    }

    @Test
    public void testCapsToBold() throws Exception {
    }

    @Test
    public void testDeleteWord() throws Exception {
    }

    @Test
    public void testSplitBySpaces() throws Exception {
    }
}
