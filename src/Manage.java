import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manage {
    public static final Scanner in = new Scanner(System.in);
    private static final ArrayList<Account> la = new ArrayList<>();

    private static String MD5Encryption(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] byteData = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : byteData) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Encryption algorithm not found.");
            return "";
        }
    }

    public static void addAccount() {
        System.out.print("Enter Username: ");
        String username = Validate.checkInputUsername(la);
        System.out.print("Enter Password: ");
        String password = Validate.checkInputString();
        System.out.print("Enter Name: ");
        String name = Validate.checkInputString();
        System.out.print("Enter Phone: ");
        String phone = Validate.checkInputPhone();
        System.out.print("Enter Email: ");
        String email = Validate.checkInputEmail();
        System.out.print("Enter Address: ");
        String address = Validate.checkInputString();
        System.out.print("Enter Date Of Birth: ");
        String dateOfBirth = Validate.checkInputDate();

        la.add(new Account(username, MD5Encryption(password), name, phone, email, address, dateOfBirth));
        System.out.println("Account added successfully!");
    }

    public static void login() {
        if (la.isEmpty()) {
            System.err.println("No accounts available.");
            return;
        }
        System.out.print("Enter Username: ");
        String username = Validate.checkInputString();
        System.out.print("Enter Password: ");
        String password = Validate.checkInputString();
        Account accountLogin = findAccount(username, password);

        if (accountLogin != null) {
            System.out.println("Welcome, " + accountLogin.getUsername());
            System.out.print("Would you like to change your password now? (Y/N): ");
            String response = in.nextLine().trim();
            while (!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("N")) {
                System.out.print("Invalid input. Please enter Y or N: ");
                response = in.nextLine().trim();
            }
            if (response.equalsIgnoreCase("Y")) {
                changePassword(accountLogin);
            }
        } else {
            System.err.println("Invalid username or password.");
        }
    }

    private static Account findAccount(String username, String password) {
        for (Account account : la) {
            if (username.equalsIgnoreCase(account.getUsername()) &&
                MD5Encryption(password).equalsIgnoreCase(account.getPassword())) {
                return account;
            }
        }
        return null;
    }

    private static void changePassword(Account accountLogin) {
        while (true) {
            System.out.print("Old password (or press 'q' to quit): ");
            String oldPassword = Validate.checkInputString();
            if (oldPassword.equalsIgnoreCase("q")) {
                System.out.println("Password change canceled.");
                return;
            }
    
            if (!MD5Encryption(oldPassword).equalsIgnoreCase(accountLogin.getPassword())) {
                System.out.println("Old password is incorrect. Please try again.");
                continue;
            }
    
            while (true) {
                System.out.print("New password: ");
                String newPassword = Validate.checkInputString();
                System.out.print("Confirm new password: ");
                String confirmPassword = Validate.checkInputString();
    
                if (!newPassword.equals(confirmPassword)) {
                    System.out.println("New password and confirmation do not match. Please try again.");
                } else {
                    accountLogin.setPassword(MD5Encryption(newPassword));
                    System.out.println("Password changed successfully.");
                    return;
                }
            }
        }
    }
    
    public static void menu() {
        while (true) {
            System.out.println("1. Add user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = Validate.checkIntLimit(1, 3);
            switch (choice) {
                case 1:
                    addAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    return;
            }
        }
    }
}
