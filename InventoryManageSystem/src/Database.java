import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Database extends Warehouse {

    private static Warehouse warehouse = new Warehouse();
    private static String ADMIN_FILE = "admin.txt";
    private static String DATA_FILE = "inventory.txt";

    public static boolean authentication() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.println("Enter your details to access the admin portal.");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");

                    if (stringEqual(username,data[0]) && stringEqual(password, data[1])) {
                        System.out.println("Authentication successful.");
                        return true;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading admin file.");
            }
            attempts++;
            System.out.println("Authentication failed. " + (maxAttempts - attempts) + " attempts left.");
        }
        return false;
    }

    public static void addProduct(Scanner scanner) {
        int itemId = 0;
        String name, category;
        int quantity = 0;
        double price = 0.0;

        while (true) {
            try {
                System.out.print("Enter Product ID: ");
                itemId = Integer.valueOf(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for Product ID.");
            }
        }

        System.out.print("Enter Product Name: ");
        name = scanner.nextLine();

        System.out.print("Enter Product Category: ");
        category = scanner.nextLine();

        while (true) {
            try {
                System.out.print("Enter Product Quantity: ");
                quantity = Integer.valueOf(scanner.nextLine());
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a valid quantity.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for Quantity.");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Product Price: ");
                price = Double.valueOf(scanner.nextLine());
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please enter a valid price.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for Price.");
            }
        }

        warehouse.addProduct(itemId, name, category, quantity, price);
        addToFile(DATA_FILE);
        System.out.println("Product added successfully.");
    }

    public static void removeProduct(Scanner scanner) {
        int itemId = 0;

        while (true) {
            System.out.print("Enter Product ID to remove: ");
            try {
                itemId = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }

        if (!searchId(DATA_FILE, itemId)) {
            System.out.println("No product found with ID: " + itemId);
            return;
        }

        if(warehouse.head != null){
            warehouse.head = null;
            warehouse.tail=null;
        }
        dataToLinkedList(DATA_FILE);
        warehouse.removeProduct(itemId);
        saveToFile(DATA_FILE);
    }


    public static void updateProduct(Scanner scanner) {

        int productId = 0;
        while (true) {
            System.out.print("Enter Product ID to update: ");
            try {
                productId = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }

        if(!searchId(DATA_FILE, productId)){
            System.out.println("No product found with ID: " + productId);
            return;
        }

        System.out.print("Enter new Name: ");
        String newName = scanner.nextLine();

        double newPrice = 0;
        while (true) {
            System.out.print("Enter new Price: ");
            try {
                newPrice = scanner.nextDouble();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid price. Please enter a valid number.");
                scanner.nextLine();
            }
        }
//        updateData(DATA_FILE, productId, newName,  newPrice);
//        System.out.println("Product with ID " + productId + " updated successfully.");

        if(warehouse.head != null){
            warehouse.head = null;
            warehouse.tail=null;
        }
            dataToLinkedList(DATA_FILE);
        warehouse.updateProduct(productId, newName, newPrice);
        saveToFile(DATA_FILE);
    }

    public static void displayAllProducts() {
        loadFromData(DATA_FILE);
    }

    public static void searchtByCategory(Scanner scanner) {
        System.out.print("Enter Category to search: ");
        String category = scanner.nextLine();
        searchCategory(DATA_FILE, category);
    }

    public static void sortProductByCategory() {
        if (warehouse.head != null){
            warehouse.head = null;
            warehouse.tail=null;
        }
        dataToLinkedList(DATA_FILE);
        warehouse.sortByCategory();
        System.out.println("Products sorted by Category.");
        saveToFile(DATA_FILE);
    }

    public static void sortProductById() {
        if (warehouse.head != null){
            warehouse.head = null;
            warehouse.tail=null;
        }
        dataToLinkedList(DATA_FILE);
        warehouse.sortByID();
        System.out.println("Products sorted by Item ID.");
        saveToFile(DATA_FILE);
    }

    public static void addToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            Product current = warehouse.tail;
            if (current != null) {
                do {
                    writer.append(current.getItemId() + "," + current.getName() + "," + current.getCategory() + "," + current.getQuantity() + "," + current.getPrice());
                    writer.newLine();
                    current = current.next;
                    if(current == warehouse.head)
                        break;

                } while (current != warehouse.head);
            }
            System.out.println("Data appended to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public static void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Product current = warehouse.head;
            if (current == null) {
                System.out.println("No Data to save Content in the File!");
                return;
            }
                do {
                    writer.write(current.getItemId() + "," + current.getName() + "," + current.getCategory() + "," + current.getQuantity() + "," + current.getPrice());
                    writer.newLine();
                    current = current.next;
                    if(current == warehouse.head)
                        break;

                } while (current != warehouse.head);

            System.out.println("Data saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }


    public static void loadFromData(String DATA_FILE) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                System.out.print("->Product [");
                for (int i = 0; i < data.length; i++) {
                    System.out.print(data[i]);
                    if (i < data.length - 1) {
                        System.out.print(" ,");
                    }
                }
                System.out.println("]");
            }
            System.out.println("Data loaded from file: " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public static void searchCategory(String DATA_FILE, String category){
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if(stringEqual(data[2],category)){
                    System.out.print("->Product [");
                    for (int i = 0; i < data.length; i++) {
                        System.out.print(data[i]);
                        if (i < data.length - 1) {
                            System.out.print(" ,");
                        }
                    }
                    System.out.println("]");
                }
            }
            System.out.println("Data loaded from file: " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public static boolean searchId(String DATA_FILE, int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (stringEqual(data[0],String.valueOf(id))){
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

//    public static void updateData(String DATA_FILE, int id, String newName, double newPrice) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
//            String line;
//            String updatedContent = "";
//
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(",");
//
//                if (String.valueOf(id).equals(data[0])) {
//                    line = id + "," + newName + "," + data[2] + "," + data[3] + "," + newPrice;
//                }
//                updatedContent = updatedContent + line + "\n";
//            }
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
//                writer.write(updatedContent);
//            }
//
//        } catch (IOException e) {
//            System.out.println("Error updating data in file: " + e.getMessage());
//        }
//    }

//    public static void deleteData(String DATA_FILE, int id) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
//            String line;
//            String updatedContent = "";
//
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(",");
//
//                if (String.valueOf(id).equals(data[0])) {
//                    continue;
//                }
//
//                updatedContent = updatedContent + line + "\n";
//            }
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
//                writer.write(updatedContent);
//            }
//
//            System.out.println("Product with ID " + id + " deleted successfully.");
//        } catch (IOException e) {
//            System.out.println("Error deleting data from file: " + e.getMessage());
//        }
//    }

    public static void dataToLinkedList(String DATA_FILE) {

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                int itemId = Integer.valueOf(data[0]);
                String name = data[1];
                String category = data[2];
                int quantity = Integer.valueOf(data[3]);
                double price = Double.valueOf(data[4]);

                warehouse.addProduct(itemId,name,category,quantity,price);
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public static boolean stringEqual(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }
        return true;
    }




}