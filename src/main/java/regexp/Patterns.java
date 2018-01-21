package regexp;

import java.util.regex.Pattern;

public enum Patterns {
    // Почта
      MAIL("[a-zA-Z]([.-]?[a-zA-`Z0-9_]+)*@[a-zA-Z]([.-]?[a-zA-Z0-9_]+)*")
    // То что может быть именем и фамилией, начинаются с большой буквы, не содержат небуквенных символов,
    // разделены минимум одним пробелом
    , NAME_SURRNAME("")
    // Адрес веб-странички http://host/address
    , PAGE_ADDRESS("")
    // Цифровые часы. 10:55
    , DIGITAL_CLOCK("")
    // IP-адрес в десятичном представлении. 127.0.0.1
    , IP("")
    // Жадные круглые скобки. Возвращает любые скобочные выражения.
    , GREEDY_BRACKETS("")
    // Ленивые круглые скобки. Возвращает скобочные выражения без содержания внутри правильной скобочной последовательности.
    , LAZY_BRACKETS("")
    // Самые большие круглые скобки. Возращает только самые большие скобочные выражения. Не возвращает подскобочные скобочные выражения.
    , BIG_BRACKETS("")
    // Российский номер автомобиля
    , AUTO_NUMBER("")
    // Слова с удвоенной "н"
    , DOUBLE_N("")
    // Русские прилагательные. -ая, -ое, -ие, -яя, ...
    , ADJECTIVE("")
    // Числовое равенство. 3 + 5 = 7
    , NUM_EQUALITY("")
    // Телефонный номер со скобками и дефисами или без
    , PHONE("")
    // Красное или зеленое яблоко
    , APPLE("")
    // Шестнадцатиричное число без впереди идущих нулей
    , HEX("")
    // Любое натуральное число
    , NATURAL("")
    // Любое рациональное число
    , RATIO("")
    // Кот, но не котел и не икота
    , CAT("")
    // Ко...т - слово целиком
    , CA_T("")
    // Дата в любом формате
    , DATE("")
    // Предложение, в котором встречается повтор одного слова(возможно в разном регистре) и вхождения этого слова
    // разделены минимум одним пробельным символом.
    , DOUBLE_WORD_SENTENCE("")
    // Слова вида аааббб, где кол-во а и б одинаково
    , AAABBB("")
    ;

    private final Pattern pattern;

    Patterns(String stringPattern) {
        pattern = Pattern.compile(stringPattern);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
