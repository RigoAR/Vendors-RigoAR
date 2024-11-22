class Item {
    private String name;
    private double price;
    private int stock;
    private String description;

    public Item(String name, double price, int stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock() {
        if (stock > 0) {
            stock--;
        }
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
