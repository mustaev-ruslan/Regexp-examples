package regexp;

import org.jetbrains.annotations.NotNull;

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
            Integer flag = Objects.requireNonNullElse(flagMap.get(entry.getKey()), 0);
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
        strMap.put("IP", "((1[0-9][0-9]|[1-9]?[0-9]|2[0-4][0-9]|25[0-5])(.|$)){4}");
        // Российский номер автомобиля
        flagMap.put("AUTO_NUMBER", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        strMap.put("AUTO_NUMBER", "[АВЕКМНОРСТУХABEKMHOPCTYX]" +
                "([0-9][1-9]|[1-9][0-9])[0-9]" +
                "[АВЕКМНОРСТУХABEKMHOPCTYX]{2}" +
                "[17]?([0-9][1-9]|[1-9][0-9])" +
                "RUS");
        // Слова с удвоенной "н"
        flagMap.put("DOUBLE_N", Pattern.UNICODE_CHARACTER_CLASS);
        strMap.put("DOUBLE_N", "\\w*нн\\w*");
        // Русские полные прилагательные. -ая, -ое, -ие, -яя, ...
        flagMap.put("ADJECTIVE", Pattern.UNICODE_CHARACTER_CLASS);
        strMap.put("ADJECTIVE", "\\w+(ый|ого|ому|ым|ом|ий|его|ему|им|ем|ая|ой|ую|яя|ей|юю|ое|ее|ые|ых|ыми|ие|их|ими|ин|ья)");
        // Числовое равенство. 3+5=7
        strMap.put("NUM_EQUALITY", "-?[1-9]\\d*([+*/-][1-9]\\d*)*=-?[1-9]\\d*");
        // Телефонный номер со скобками и дефисами или без
        strMap.put("PHONE",
                "(" +
                    "\\+?(\\d(-?\\d)*)?(\\(\\d(-?\\d)*\\))?" +
                    "|" +
                    "\\(\\+\\d(-?\\d)*\\)" +
                ")" +
                "(\\d(-?\\d)*)?");
        // Красное или зеленое яблоко
        flagMap.put("APPLE", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        strMap.put("APPLE", "(красное|зел[её]ное) яблоко");
        // Шестнадцатиричное число без впереди идущих нулей
        flagMap.put("HEX", Pattern.CASE_INSENSITIVE);
        strMap.put("HEX", "(0|-?[1-9A-F][0-9A-F]*)");
        // Любое натуральное число
        strMap.put("NATURAL", "\\+?[1-9]\\d*");
        // Любое целое число
        strMap.put("INTEGER", "(0|[+-]?[1-9]\\d*)");
        // Любое рациональное число
        strMap.put("RATIO", "(" +
                "(0|[+-]?[1-9]\\d*)" + // Целое
                "|" +
                "[+-]?(0|[1-9]\\d*)[,.]\\d*[1-9]" + // С дробной частью
                ")");
        // Кот, но не котел и не икота
        flagMap.put("CAT", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        strMap.put("CAT", ".*\\Wкот\\W.*");
        // Ко...т - слово целиком
        flagMap.put("CA_T", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        strMap.put("CA_T", "(?<!\\w)ко\\w*т(?!\\w)");
        // Дата в формате DD.MM.YYYY
        strMap.put("DATE",
                        "(?!00)([012][0-9]|3[0-1])" + // DD
                        "\\." +
                        "(0[1-9]|1[12])" + // MM
                        "\\." +
                        "(?!0000)\\d{4}"); // YYYY
        // Предложение, в котором встречается повтор одного слова(возможно в разном регистре) и вхождения этого слова
        // разделены минимум одним пробельным символом.
        flagMap.put("DOUBLE_WORD_SENTENCE", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        strMap.put("DOUBLE_WORD_SENTENCE", "(?<!\\w)(\\w+)\\s+\\1(?!\\w)");
        // Два разных символа
        strMap.put("DIFFERENT", "(.)(?!\\1).");
        // Слова вида аааббб, где кол-во а и б одинаково. а и б могут быть одинаковы
        strMap.put("ANBN", "(?:(?:((?!\\3).)(?=\\1*(\\2?+(.)(?=\\3|$))))+\\2|((.)\\5)\\4*)");
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

    // Сгруппировать по три цифры в числе: -1234567890.123456789 -> -1 234 567 890.123 456 789
    public static String groupDigits(@NotNull String text) {
        return text.replaceAll("((?<=\\d)(?<!\\.\\d+)(?=(\\d\\d\\d)+(?!\\d))|(?<=\\.(\\d\\d\\d)+)(?=\\d))", " ");
    }

    // Удаление концевых и внутренних лишних пробелов: "    hello    world    " -> "hello world"
    public static String clearSpaces(@NotNull String text) {
        return text
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("\\s+", " ");
    }

    // Заменить все вхождения Java пробел* 1./d на Java пробел /d.0
    public static String javaVersionReplace(String text) {
        throw new UnsupportedOperationException();
    }

    // Найти все температуры в фаренгейтах и заменить на цельсии
    public static String fahrenheitToCelsius(String text) {
        throw new UnsupportedOperationException();
    }

    // Заменить все капслоки на жирное начертание тегами
    public static String capsToBold(String text) {
        throw new UnsupportedOperationException();
    }

    // Удалить все вхождения слова из текста
    public static String deleteWord(String text, String word) {
        throw new UnsupportedOperationException();
    }

    // Разделить текст на слова, разделитель - пробельный символ
    public static List<String> splitBySpaces(String text) {
        throw new UnsupportedOperationException();
    }
}
