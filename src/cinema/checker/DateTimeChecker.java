package cinema.checker;

import cinema.exception.InvalidDateTimeException;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeChecker {

    public static LocalDateTime check(String dateTimeString) {
        String regex = "(\\d{2})\\.(\\d{2})\\.(\\d{4}) ([01]\\d|2[0-3]):([0-5]\\d)";
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(dateTimeString);
            matcher.matches();

            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            int hours = Integer.parseInt(matcher.group(4));
            int minutes = Integer.parseInt(matcher.group(5));

            return LocalDateTime.of(year, month, day, hours, minutes);
        } catch (Exception e) {
            throw new InvalidDateTimeException();
        }
    }
}
