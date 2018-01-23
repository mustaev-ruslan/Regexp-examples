package regexp;

import java.util.*;
import java.util.regex.*;

public class RegexpHelper {

    private static Map<String, String> strMap;
    private static Map<String, Integer> flagMap;
    private static Map<String, Pattern> patternMap;

    static {
        strMap = new HashMap<>();
        flagMap = new HashMap<>();
        initStrAndFlagMap();
        patternMap = new HashMap<>();
        for (Map.Entry<String, String> entry : strMap.entrySet()) {
            Integer flag = flagMap.get(entry.getKey());
            flag = flag != null ? flag : 0;
            //noinspection MagicConstant
            patternMap.put(entry.getKey(), Pattern.compile(entry.getValue(), flag));
        }
    }

    private static void initStrAndFlagMap() {
        // Почта
        strMap.put("MAIL", "[a-zA-Z]([.-]?[a-zA-`Z0-9_]+)*@[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*");
        // То что может быть именем и фамилией, начинаются с большой буквы, не содержат небуквенных символов,
        // разделены минимум одним пробелом
        strMap.put("NAME_SURRNAME", "[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)?\\s+[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)?");
        // Адрес веб-странички http://host/address
        strMap.put("PAGE_ADDRESS", "https?://[a-zA-Z0-9._-]+(/[a-zA-Z0-9_-]+)*((/[a-zA-Z0-9._-]+)|/)?");
        // Цифровые часы. 10:55
        strMap.put("DIGITAL_CLOCK", "([01][0-9]|[2][0-3]):([0-5][0-9])");
        // IP-адрес в десятичном представлении. 127.0.0.1
        strMap.put("IP", "([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])." +
                "([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])." +
                "([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])." +
                "([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])");
        // Жадные круглые скобки. Возвращает любые скобочные выражения.
        strMap.put("GREEDY_BRACKETS", "");
        // Ленивые круглые скобки. Возвращает скобочные выражения без содержания внутри правильной скобочной последовательности.
        strMap.put("LAZY_BRACKETS", "");
        // Самые большие круглые скобки. Возращает только самые большие скобочные выражения. Не возвращает подскобочные скобочные выражения.
        strMap.put("BIG_BRACKETS", "");
        // Российский номер автомобиля
        flagMap.put("AUTO_NUMBER", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        strMap.put("AUTO_NUMBER", "[АВЕКМНОРСТУХABEKMHOPCTYX]" +
                "([0-9][1-9]|[1-9][0-9])[0-9]" +
                "[АВЕКМНОРСТУХABEKMHOPCTYX][АВЕКМНОРСТУХABEKMHOPCTYX]" +
                "[17]?([0-9][1-9]|[1-9][0-9])" +
                "RUS");
        // Слова с удвоенной "н"
        flagMap.put("DOUBLE_N", Pattern.UNICODE_CHARACTER_CLASS);
        strMap.put("DOUBLE_N", "\\w*нн\\w*");
        // Русские полные прилагательные. -ая, -ое, -ие, -яя, ...
        flagMap.put("ADJECTIVE", Pattern.UNICODE_CHARACTER_CLASS);
        strMap.put("ADJECTIVE", "\\w+(ый|ого|ому|ым|ом|ий|его|ему|им|ем|ая|ой|ую|яя|ей|юю|ое|ее|ые|ых|ым|ыми|ие|их|им|ими|юю|ин|ья)");
        // Числовое равенство. 3 + 5 = 7
        strMap.put("NUM_EQUALITY", "");
        // Телефонный номер со скобками и дефисами или без
        strMap.put("PHONE", "");
        // Красное или зеленое яблоко
        flagMap.put("APPLE", Pattern.UNICODE_CHARACTER_CLASS);
        strMap.put("APPLE", "(красное|зел[её]ное) яблоко");
        // Шестнадцатиричное число без впереди идущих нулей
        strMap.put("HEX", "");
        // Любое натуральное число
        strMap.put("NATURAL", "+?[1-9]\\d*");
        // Любое целое число
        strMap.put("INTEGER", "(0|[+-]?[1-9]\\d*)");
        // Любое рациональное число
        strMap.put("RATIO", "(" +
                "(0|[+-]?[1-9]\\d*)" + // Целое
                "|" +
                "[+-](0|[1-9]\\d*)[,.]\\d*[1-9]" + // С дробной частью
                ")");
        // Кот, но не котел и не икота
        strMap.put("CAT", "");
        // Ко...т - слово целиком
        strMap.put("CA_T", "");
        // Дата в любом формате
        strMap.put("DATE", "");
        // Предложение, в котором встречается повтор одного слова(возможно в разном регистре) и вхождения этого слова
        // разделены минимум одним пробельным символом.
        strMap.put("DOUBLE_WORD_SENTENCE", "");
        // Слова вида аааббб, где кол-во а и б одинаково, кол-во меньше пяти (в общем случае не разрешимо)
        strMap.put("AAABBB", "(..|(.)\1(.)\2|(.)\1{2}(.)\2{2}|(.)\1{3}(.)\2{3}|(.)\1{4}(.)\2{4})");
    }

    public static boolean isMatches(String text, Pattern pattern) {
        return !(text == null || text.isEmpty()) && pattern.matcher(text).matches();
    }

    public static Set<String> findUniqueMatches(String text, Pattern pattern) {
        Set<String> matchesSet = new HashSet<>();
        if (text == null || text.isEmpty()) {
            return matchesSet;
        }
        Matcher m = pattern.matcher(text);
        while (m.find()) {
            matchesSet.add(m.group());
        }
        return matchesSet;
    }

    public static Pattern getPattern(String patternName) {
        return patternMap.get(patternName);
    }
}
