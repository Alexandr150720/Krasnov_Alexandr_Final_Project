package cinema.checker;

public class ValidValueChecker {

    public static boolean isValidInteger(String input) {
        while (true) {
            try {
                Integer.parseInt(input);
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
