public class Warehouse
{
    Product head, tail;

    Warehouse() {
        head  = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void addProduct(int itemId, String name, String category, int quantity, double price) {
        Product newNode = new Product(itemId, name, category, quantity, price);

        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        tail.next = head;
        head.prev = tail;
    }

    public void removeProduct(int productId) {
        if (isEmpty()) return;

        Product current = head;
        do {
            if (current.getItemId() == productId) {
                if (current == head && current == tail) {
                    head = null;
                    tail = null;
                } else {
                    if (current == head) {
                        head = head.next;
                    }
                    if (current == tail) {
                        tail = tail.prev;
                    }
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                return;
            }
            current = current.next;
        } while (current != head);
    }

    public void updateProduct(int productId, String newName, double newPrice) {
        Product product = findByID(productId);
        if (product != null) {
            product.setName(newName);
            product.setPrice(newPrice);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    public Product findByID(int productId) {
        if (head == null) return null;

        Product current = head;
        do {
            if (current.getItemId() == productId) {
                return current;
            }
            current = current.next;
        } while (current != head);
        return null;
    }

//    public void displayProducts() {
//        if (isEmpty())
//        {
//            System.out.println("No products to display.");
//            return;
//        }
//
//        Product current = head;
//        do {
//            System.out.println(" -> " + current);
//            current = current.next;
//        } while (current != head);
//    }

//    public void filterProductsByCategory(String category) {
//        if (isEmpty())
//
//        {
//            System.out.println("No products in that category.");
//            return;
//        }
//
//        Product current = head;
//        do {
//            if (current.getCategory().equalsIgnoreCase(category)) {
//                System.out.println(current);
//            }
//            current = current.next;
//        } while (current != head);
//    }

    public void sortByCategory() {
        if (head == null || head.next == head) {
            return;
        }

        Product current = head;
        int size = 0;
        do {
            size++;
            current = current.next;
        } while (current != head);

        String[] categories = new String[size];
        Product[] products = new Product[size];

        current = head;
        for (int i = 0; i < size; i++) {
            categories[i] = current.getCategory();
            products[i] = current;
            current = current.next;
        }

        for (int i = 0; i < categories.length - 1; i++) {
            for (int j = i + 1; j < categories.length; j++) {
                int l = 0;
                int minLength = Math.min(categories[i].length(), categories[j].length());
                while (l < minLength && categories[i].charAt(l) == categories[j].charAt(l)) {
                    l++;
                }
                if (l < minLength && categories[i].charAt(l) > categories[j].charAt(l)) {
                    String tempCategory = categories[i];
                    categories[i] = categories[j];
                    categories[j] = tempCategory;

                    Product tempProduct = products[i];
                    products[i] = products[j];
                    products[j] = tempProduct;
                }
            }
        }

        head = products[0];
        Product prev = head;
        for (int i = 1; i < products.length; i++) {
            prev.next = products[i];
            products[i].prev = prev;
            prev = products[i];
        }
        prev.next = head;
        head.prev = prev;
    }



    public void sortByID() {
        if (head == null || head.next == head) {
            return;
        }

        Product current = head;
        int maxId = head.getItemId();
        do {
            if (current.getItemId() > maxId) {
                maxId = current.getItemId();
            }
            current = current.next;
        } while (current != head);

        Product[] countArray = new Product[maxId + 1];

        do {
            countArray[current.getItemId()] = current;
            current = current.next;
        } while (current != head);

        Product newHead = null;
        Product lastNode = null;
        for (int i = 0; i <= maxId; i++) {
            if (countArray[i] != null) {
                if (newHead == null) {

                    newHead = countArray[i];
                    newHead.prev = null;
                } else {

                    lastNode.next = countArray[i];
                    countArray[i].prev = lastNode;
                }
                lastNode = countArray[i];
            }
        }

        if (newHead != null && lastNode != null) {
            lastNode.next = newHead;
            newHead.prev = lastNode;
        }
        head = newHead;
    }
}