import java.util.HashMap;
import java.util.Map;

/**
 * Class for a Vending Machine. Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    private double balance;
    private HashMap<String, Item> inventory;
    private HashMap<String, Integer> purchaseTrends;


    public Vending() {
        inventory = new HashMap<>();
        purchaseTrends = new HashMap<>();
        this.balance = 0;


        inventory.put("Candy", new Item("Candy", 1.25, 5, "A sweet and chewy treat."));
        inventory.put("Gum", new Item("Gum", 0.75, 5, "Refreshing mint-flavored gum."));
    }

    public Vending(int candyStock, int gumStock) {
        inventory = new HashMap<>();
        purchaseTrends = new HashMap<>();
        this.balance = 0;

        inventory.put("Candy", new Item("Candy", 1.25, candyStock, "A sweet and chewy treat."));
        inventory.put("Gum", new Item("Gum", 0.75, gumStock, "Refreshing mint-flavored gum."));
    }

    public void addMoney(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public int getStock(String itemName) {
        Item item = inventory.get(itemName);
        return item != null ? item.getStock() : -1;
    }

    public String getItemDescription(String itemName) {
        Item item = inventory.get(itemName);
        return item != null ? item.getDescription() : "Item not found.";
    }

    public void select(String itemName) {
        Item item = inventory.get(itemName);
        if (item != null && item.getStock() > 0 && balance >= item.getPrice()) {
            item.decreaseStock();
            balance -= item.getPrice();
            purchaseTrends.put(itemName, purchaseTrends.getOrDefault(itemName, 0) + 1);
        }
    }

    public boolean isInventoryEmpty() {
        return inventory.values().stream().allMatch(item -> item.getStock() == 0);
    }

    public void restockItem(String itemName, int quantity) {
        Item item = inventory.get(itemName);
        if (item != null) {
            item.increaseStock(quantity);
        } else {
            inventory.put(itemName, new Item(itemName, 1.00, quantity, "Default description"));
        }
    }

    public int renameItem(String oldName, String newName) {
        if (!inventory.containsKey(oldName)) {
            return -1;
        }


        if (inventory.containsKey(newName)) {
            return -1;
        }


        Item item = inventory.remove(oldName);

        inventory.put(newName, item);

        return item.getStock();
    }

    public void removeItem(String itemName) {
        inventory.remove(itemName);
    }

    public Map<String, Integer> getPurchaseTrends() {
        return purchaseTrends;
    }

    public void printInventory() {
        inventory.forEach((key, item) -> System.out.println(key + ": " + item.getStock() + " in stock"));
    }
}
