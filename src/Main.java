public class Main {
    public static void main(String[] args) {
        try {
            Manage.menu();
        } finally {
            Manage.in.close();
        }
    }
}
