package regexp;

import java.util.*;
import java.util.regex.*;

public class RegexpHelper {

    private static Map<String, String> strMap;
    private static Map<String, Pattern> patternMap;

    static {
        strMap = new HashMap<>();
        initStrMap();
        patternMap = new HashMap<>();
        for (Map.Entry<String, String> entry : strMap.entrySet()) {
            patternMap.put(entry.getKey(), Pattern.compile(entry.getValue()));
        }
    }

    private static void initStrMap() {
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
        strMap.put("IP", "");
        // Жадные круглые скобки. Возвращает любые скобочные выражения.
        strMap.put("GREEDY_BRACKETS", "");
        // Ленивые круглые скобки. Возвращает скобочные выражения без содержания внутри правильной скобочной последовательности.
        strMap.put("LAZY_BRACKETS", "");
        // Самые большие круглые скобки. Возращает только самые большие скобочные выражения. Не возвращает подскобочные скобочные выражения.
        strMap.put("BIG_BRACKETS", "");
        // Российский номер автомобиля
        strMap.put("AUTO_NUMBER", "");
        // Слова с удвоенной "н"
        strMap.put("DOUBLE_N", "");
        // Русские прилагательные. -ая, -ое, -ие, -яя, ...
        strMap.put("ADJECTIVE", "");
        // Числовое равенство. 3 + 5 = 7
        strMap.put("NUM_EQUALITY", "");
        // Телефонный номер со скобками и дефисами или без
        strMap.put("PHONE", "");
        // Красное или зеленое яблоко
        strMap.put("APPLE", "");
        // Шестнадцатиричное число без впереди идущих нулей
        strMap.put("HEX", "");
        // Любое натуральное число
        strMap.put("NATURAL", "");
        // Любое рациональное число
        strMap.put("RATIO", "");
        // Кот, но не котел и не икота
        strMap.put("CAT", "");
        // Ко...т - слово целиком
        strMap.put("CA_T", "");
        // Дата в любом формате
        strMap.put("DATE", "");
        // Предложение, в котором встречается повтор одного слова(возможно в разном регистре) и вхождения этого слова
        // разделены минимум одним пробельным символом.
        strMap.put("DOUBLE_WORD_SENTENCE", "");
        // Слова вида аааббб, где кол-во а и б одинаково
        strMap.put("AAABBB", "");
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
