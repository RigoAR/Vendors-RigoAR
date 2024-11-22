import java.util.HashMap;

/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    private HashMap<String, Item> Stock = new HashMap<>();
    private double balance;

    Vending(int numCandy, int numGum) {
        Stock.put("Candy", new Item(1.25, numCandy));
        Stock.put("Gum", new Item(0.5, numGum));
        this.balance = 0;
    }

    void resetBalance() {
        this.balance = 0;
    }

    double getBalance() {
        return this.balance;
    }

    void addMoney(double amt) {
        this.balance = this.balance + amt;
    }

    void select(String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            if (balance >= item.price) {
                item.purchase(1);
                this.balance = this.balance - item.price;
            } else
                System.out.println("Gimme more money");
        } else {
            System.out.println("Sorry, don't know that item");
        }
    }

    int getStock(String itemName) {
        if (Stock.containsKey(itemName)) {
            return Stock.get(itemName).stock;
        }
        return -1;
    }

    boolean isInventoryEmpty() {
        for (Item item : Stock.values()) {
            if (item.stock > 0) {
                return false;
            }
        }
        return true;
    }

    void restockItem(String itemName, int amount) {
        if (Stock.containsKey(itemName)) {
            Stock.get(itemName).restock(amount);
        } else {
            Stock.put(itemName, new Item(1.00, amount));
            System.out.println("Added new item to stock: " + itemName);
        }
    }

    public int renameItem(String oldName, String newName) {
        if (Stock.containsKey(oldName)) {
            Item item = Stock.get(oldName);
            Stock.remove(oldName);
            Stock.put(newName, new Item(item.price, item.stock));
            System.out.println("Item renamed from " + oldName + " to " + newName);
            return 10;
        } else {
            System.out.println("Item not found to rename");
            return -1;
        }
    }

    void removeItem(String name) {
        if (Stock.containsKey(name)) {
            Stock.remove(name);
            System.out.println(name + " has been removed.");
        } else {
            System.out.println(name + " not found.");
        }
    }

    void printInventory() {
        System.out.println("Inventory for Vendor:");
        for (String itemName : Stock.keySet()) {
            Item item = Stock.get(itemName);
            System.out.println(itemName + ": " + item.getStock() + " available");
        }
    }
}