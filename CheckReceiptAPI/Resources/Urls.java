package CheckReceiptAPI.Resources;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Urls {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static final String REGISTRATION = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/signup";
    public static final String LOGIN = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/login";
    public static final String RESTORE = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/restore";

    public static String getCheckUrl (String fiscalNumber, String fiscalDocument, String fiscalSign,
                                      LocalDateTime date, double sum) {

        if (isNullOrWhiteSpace(fiscalSign)) {

            throw new IllegalArgumentException("Недопустимое значение параметра fiscalSign");
        }

        if (isNullOrWhiteSpace(fiscalNumber)) {

            throw new IllegalArgumentException("Недопустимое значение параметра fiscalNumber");
        }

        if (isNullOrWhiteSpace(fiscalDocument)) {

            throw new IllegalArgumentException("Недопустимое значение параметра fiscalDocument");
        }

        if (date.equals(LocalDateTime.MIN)) {

            throw new IllegalArgumentException("Недопустимое значение параметра date");
        }

        return String.format("https://proverkacheka.nalog.ru:9999/v1/ofds/*/inns/*/fss/%s/operations/1/tickets/%s" +
                        "?fiscalSign=%s&date=%s&sum=%s", fiscalNumber, fiscalDocument, fiscalSign,
                date.format(FORMATTER), getSum(sum));
    }

    private static String getSum(double sum) {

        return Integer.toString((int) (Math.round(sum) * 100));
    }

    public static String getReceiveUrl (String fiscalNumber, String fiscalDocument, String fiscalSign) {

        if (isNullOrWhiteSpace(fiscalSign)) {

            throw new IllegalArgumentException("Недопустимое значение параметра fiscalSign");
        }

        if (isNullOrWhiteSpace(fiscalNumber)) {

            throw new IllegalArgumentException("Недопустимое значение параметра fiscalNumber");
        }

        if (isNullOrWhiteSpace(fiscalDocument)) {

            throw new IllegalArgumentException("Недопустимое значение параметра fiscalDocument");
        }

        return String.format("https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/%s/tickets/%s?fiscalSign=%s&sendToEmail=no",
                fiscalNumber, fiscalDocument, fiscalSign);
    }

    public static boolean isNullOrEmpty(String s) {

        return s == null || s.length() == 0;
    }

    public static boolean isNullOrWhiteSpace(String s) {

        return s == null || isWhiteSpace(s);
    }

    private static boolean isWhiteSpace(String s) {

        int length = s.length();

        if (length > 0) {

            for (int i = 0; i < length; i++) {

                if (!Character.isWhitespace(s.charAt(i))) {

                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
