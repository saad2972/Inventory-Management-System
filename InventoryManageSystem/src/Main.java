import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database d1 = new Database();

        if (!Database.authentication()) {
            System.out.println("Access denied. Exiting program.");
            return;
        }

        while (true) {
            System.out.println("\nInventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Display All Products");
            System.out.println("5. Search Product by Category");
            System.out.println("6. Sort Products by Category");
            System.out.println("7. Sort Products by ID");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.valueOf(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    Database.addProduct(scanner);
                    break;
                case 2:
                    Database.removeProduct(scanner);
                    break;
                case 3:
                    Database.updateProduct(scanner);
                    break;
                case 4:
                    Database.displayAllProducts();
                    break;
                case 5:
                    Database.searchtByCategory(scanner);
                    break;
                case 6:
                    Database.sortProductByCategory();
                    break;
                case 7:
                    Database.sortProductById();
                    break;
                case 8:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid choice.");
            }
        }
    }
}
