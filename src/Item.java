class Item {
    private String name;
    private double price;
    private int stock;
    private String description;
    private boolean bestseller;

    public Item(String name, double price, int stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.bestseller = false;
    }

    public boolean isBestseller() {
        return bestseller;
    }

    public void setBestseller(boolean bestseller) {
        this.bestseller = bestseller;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }
}
