import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Spreadsheet spreadsheet = new Spreadsheet();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Spreadsheet Menu =====");
            System.out.println("1. Create / Set Cell Content");
            System.out.println("2. Display Spreadsheet");
            System.out.println("3. Save Spreadsheet");
            System.out.println("4. Load Spreadsheet");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter cell coordinate (e.g., A1): ");
                    String coord = scanner.nextLine().toUpperCase();
                    System.out.print("Enter cell content: ");
                    String content = scanner.nextLine();
                    spreadsheet.setCellContent(coord, content);
                    break;

                case 2:
                    spreadsheet.displaySpreadsheet();
                    break;

                case 3:
                    System.out.print("Enter filename to save: ");
                    String saveFile = scanner.nextLine();
                    spreadsheet.saveToFile(saveFile);
                    break;

                case 4:
                    System.out.print("Enter filename to load: ");
                    String loadFile = scanner.nextLine();
                    spreadsheet.loadFromFile(loadFile);
                    break;

                case 0:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
