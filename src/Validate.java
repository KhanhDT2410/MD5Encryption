import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Validate {
    private static final Scanner in = new Scanner(System.in);
    private static final String PHONE_VALID = "^\\d{9,10}$";
    private static final String EMAIL_VALID = "^[0-9A-Za-z+_.%]+@[0-9A-Za-z.-]+\\.[A-Za-z]{2,4}$";

    public static int checkIntLimit(final int min, final int max) {
        while (true) {
            try {
                int n = Integer.parseInt(in.nextLine());
                if (n < min || n > max) {
                    System.err.println("Please enter a number between " + min + " and " + max + ".");
                } else {
                    return n;
                }
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static String checkInputDate() {
        while (true) {
            try {
                String result = in.nextLine().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setLenient(false); // Ngăn chặn ngày không hợp lệ (như 32/01/2020)
                Date date = format.parse(result);
                return format.format(date);
            } catch (ParseException ex) {
                System.err.println("Invalid date format. Please enter a date in dd/MM/yyyy format.");
            }
        }
    }

    public static String checkInputPhone() {
        while (true) {
            String result = in.nextLine().trim();
            if (result.matches(PHONE_VALID)) {
                return result;
            } else {
                System.err.println("Invalid phone number. Please enter 9-10 digits.");
            }
        }
    }

    public static String checkInputEmail() {
        while (true) {
            String result = in.nextLine().trim();
            if (result.matches(EMAIL_VALID)) {
                return result;
            } else {
                System.err.println("Invalid email format. Please try again.");
            }
        }
    }

    public static String checkInputString() {
        while (true) {
            String result = in.nextLine().trim();
            if (result.isEmpty()) {
                System.err.println("Input cannot be empty. Please try again.");
            } else {
                return result;
            }
        }
    }

    public static String checkInputUsername(ArrayList<Account> accounts) {
        while (true) {
            String result = checkInputString();
            boolean exists = accounts.stream().anyMatch(account -> result.equalsIgnoreCase(account.getUsername()));
            if (exists) {
                System.err.println("Username already exists. Please try another.");
            } else {
                return result;
            }
        }
    }
}
