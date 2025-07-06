public class Product  {

    private int itemId;
    private String name;
    private String category;
    private int quantity;
    private double price;
    Product next, prev;

    public Product(int itemId, String name, String category, int quantity, double price) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStockLow(int threshold) {
        return quantity < threshold;
    }

    @Override
    public String toString() {
        return "Item ID: " + itemId + ", Name: " + name + ", Category: " + category +
                ", Quantity: " + quantity + ", Price: Rs." + price;
    }
}