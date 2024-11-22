import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VendorTest {
    static Vending v;
    Vending[] vendors;
    private static final int INITIAL_STOCK = 5;

    @BeforeEach
    public void setUp() {
        v = new Vending();
        vendors = new Vending[5];
        vendors[0] = new Vending(5, 10);
        vendors[1] = new Vending(10, 15);
        vendors[2] = new Vending(7, 8);
        vendors[3] = new Vending(3, 12);
        vendors[4] = new Vending(6, 9);
    }

    @Test
    void testAddMoney() {
        v.addMoney(5.00);
        assertEquals(5.00, v.getBalance(), "Balance should be $5.00 after adding money.");
        v.addMoney(2.50);
        assertEquals(7.50, v.getBalance(), "Balance should be $7.50 after adding more money.");
    }

    @Test
    void testBuyItem() {
        v.addMoney(2.00);
        assertEquals(5, v.getStock("Candy"), "Initial Candy stock should be 5.");
        v.select("Candy");
        assertEquals(4, v.getStock("Candy"), "Candy stock should decrease by 1 after purchase.");
        assertEquals(0.75, v.getBalance(), "Balance should decrease by the item's price ($1.25).");
        assertEquals(1, v.getPurchaseTrends().get("Candy"), "Candy purchase count should be updated.");
    }

    @Test
    void testEmptyInventory() {
        v.addMoney(20.00);
        while (v.getStock("Candy") > 0) {
            v.select("Candy");
        }
        while (v.getStock("Gum") > 0) {
            v.select("Gum");
        }
        assertTrue(v.isInventoryEmpty(), "The inventory should be empty after all items are purchased.");
    }

    @Test
    void testInventoryNotEmpty() {
        assertFalse(v.isInventoryEmpty(), "Inventory should not be empty initially.");
    }

    @Test
    void testRestockItem() {
        assertEquals(5, v.getStock("Candy"));
        assertEquals(5, v.getStock("Gum"));
        v.restockItem("Candy", 3);
        v.restockItem("Gum", 2);
        assertEquals(8, v.getStock("Candy"));
        assertEquals(7, v.getStock("Gum"));
    }

    @Test
    void testRestockNonExistentItem() {
        v.restockItem("Soda", 10);
        assertEquals(10, v.getStock("Soda"));
    }

    @Test
    void testRestockItemAlreadyInStock() {
        v.restockItem("Candy", 5);
        assertEquals(10, v.getStock("Candy"));
    }

    @Test
    void testRenameNonExistentItem() {
        v.renameItem("Soda", "Cola");
        assertEquals(v.getStock(null), v.getStock("Cola"));
        assertEquals(v.getStock(null), v.getStock("Soda"));
    }

    @Test
    void testRenameItemWithNewNameAlreadyExist() {
        Vending v = new Vending();

        int result = v.renameItem("Candy", "Gum");

        assertEquals(-1, result, "Renaming 'Candy' to 'Gum' should return -1, as 'Gum' already exists.");

        assertEquals(5, v.getStock("Candy"), "'Candy' should still have its original stock of 5.");

        assertEquals(5, v.getStock("Gum"), "'Gum' should retain its original stock of 5.");
    }

    @Test
    void testRenameItem() {
        assertEquals(INITIAL_STOCK, v.getStock("Candy"), "Initial stock of Candy should be " + INITIAL_STOCK);

        int result = v.renameItem("Candy", "Gum");

        assertEquals(-1, result, "Renaming should fail if the new name already exists in the inventory.");

        assertEquals(INITIAL_STOCK, v.getStock("Candy"), "Candy stock should remain unchanged.");
        assertEquals(INITIAL_STOCK, v.getStock("Gum"), "Gum should retain its original stock after renaming attempt.");
    }

    @Test
    void testVendorInventoryInitialization() {
        assertEquals(5, v.getStock("Candy"), "Vendor should have 5 Candy.");
        assertEquals(5, v.getStock("Gum"), "Vendor should have 5 Gum.");
    }

    @Test
    void testMultipleVendorsInventory() {
        for (int i = 0; i < vendors.length; i++) {
            assertNotNull(vendors[i], "Vendor " + (i + 1) + " should be initialized.");
            System.out.println("Testing Vendor " + (i + 1) + " inventory:");
            vendors[i].printInventory();
        }
    }

    @Test
    void testRemoveItem() {
        assertEquals(5, v.getStock("Candy"), "Initial stock of Candy should be 5.");

        v.removeItem("Candy");

        assertEquals(-1, v.getStock("Candy"), "Candy should no longer exist in the inventory.");

        assertEquals(5, v.getStock("Gum"), "Gum should still be in the inventory.");
    }

    @Test
    void testRemoveNonExistentItem() {
        v.removeItem("Soda");

        assertEquals(-1, v.getStock("Soda"), "Non-existent item should not be removed or added.");
    }

    @Test
    void testPurchaseTrends() {
        v.addMoney(10.00);
        v.select("Candy");
        v.select("Candy");
        v.select("Gum");

        assertEquals(2, v.getPurchaseTrends().get("Candy"), "Candy should have 2 purchases.");
        assertEquals(1, v.getPurchaseTrends().get("Gum"), "Gum should have 1 purchase.");
    }

    @Test
    void testRestockItemWithNegativeAmount() {
        v.restockItem("Candy", -5);
        assertEquals(0, v.getStock("Candy"), "Restocking with negative amount should not change stock.");
    }

    @Test
    public void testGetItemDescription() {
        Vending machine = new Vending(10, 10);

        String candyDescription = machine.getItemDescription("Candy");
        assertEquals("A sweet and chewy treat.", candyDescription, "Candy description should be correct");

        String gumDescription = machine.getItemDescription("Gum");
        assertEquals("Refreshing mint-flavored gum.", gumDescription, "Gum description should be correct");

        String unknownItemDescription = machine.getItemDescription("Soda");
        assertEquals("Item not found.", unknownItemDescription, "Description should be 'Item not found.' for non-existent items");
    }

    @Test
    void testApplyDiscountToItem() {
        v.addMoney(5.00);
        assertEquals(1.25, v.getItem("Candy").getPrice(), "Initial price of Candy should be $1.25");
        v.applyDiscount("Candy", 0.20);
        assertEquals(1.00, v.getItem("Candy").getPrice(), "Price of Candy should be $1.00 after 20% discount");
    }

    @Test
    void testApplyDiscountToNonExistentItem() {
        v.applyDiscount("Soda", 0.15);
        Item soda = v.getItem("Soda");
        assertNull(soda, "Soda should not exist in the inventory.");
    }

    @Test
    void testApplyCategoryDiscount() {
        v.addMoney(10.00);
        v.applyCategoryDiscount(new String[]{"Candy", "Gum"}, 0.10);
        assertEquals(1.125, v.getItem("Candy").getPrice(), "Price of Candy should be $1.125 after 10% discount");
        assertEquals(0.675, v.getItem("Gum").getPrice(), "Price of Gum should be $0.675 after 10% discount");
    }

    @Test
    void testApplyNegativeDiscount() {
        v.addMoney(5.00);
        v.applyDiscount("Candy", -0.20);
        assertEquals(1.25, v.getItem("Candy").getPrice(), "Price of Candy should be $1.45 after a -20% discount");
    }

    @Test
    void testPurchaseItemAfterDiscount() {
        v.addMoney(5.00);
        v.applyDiscount("Candy", 0.20);
        v.select("Candy");
        assertEquals(4.00, v.getBalance(), "Balance should be $4.00 after purchasing Candy with a discount");
    }

    @Test
    void testApplyMultipleDiscounts() {
        System.out.println("Initial price of Candy: " + v.getItem("Candy").getPrice());
        System.out.println("Initial price of Gum: " + v.getItem("Gum").getPrice());

        v.applyDiscount("Candy", 0.10);
        v.applyDiscount("Gum", 0.20);

        assertEquals(1.125, v.getItem("Candy").getPrice(), 0.0001, "Price of Candy should be $1.125 after 10% discount");
        assertEquals(0.60, v.getItem("Gum").getPrice(), 0.0001, "Price of Gum should be $0.80 after 20% discount");
    }

    @Test
    void testSetPriceValid() {
        Vending vendingMachine = new Vending();
        vendingMachine.setPrice("Candy", 1.50);

        assertEquals(1.50, vendingMachine.getItem("Candy").getPrice(), "Price of Candy should be updated to $1.50");
    }

    @Test
    void testSetPriceInvalidNegative() {
        Vending vendingMachine = new Vending();
        vendingMachine.setPrice("Candy", -1.50);

        assertEquals(1.25, vendingMachine.getItem("Candy").getPrice(), "Price of Candy should remain $1.25 since negative prices are invalid");
    }

    @Test
    void testSetPriceNonExistentItem() {
        Vending vendingMachine = new Vending();
        vendingMachine.setPrice("Soda", 1.25);

        assertNull(vendingMachine.getItem("Soda"), "Soda should not exist in the inventory");
    }

    @Test
    void testSetPriceForZeroStockItem() {
        Vending vendingMachine = new Vending();
        vendingMachine.restockItem("Candy", 0);
        vendingMachine.setPrice("Candy", 2.00);

        assertEquals(2.00, vendingMachine.getItem("Candy").getPrice(), "Price of Candy should be updated even if stock is 0");
    }

    @Test
    void testSetPriceForMultipleItems() {
        Vending vendingMachine = new Vending();
        vendingMachine.setPrice("Candy", 2.00);
        vendingMachine.setPrice("Gum", 1.00);

        assertEquals(2.00, vendingMachine.getItem("Candy").getPrice(), "Price of Candy should be updated to $2.00");
        assertEquals(1.00, vendingMachine.getItem("Gum").getPrice(), "Price of Gum should be updated to $1.00");
    }

    @Test
    void testSetPriceForItemWithDiscount() {
        Vending vendingMachine = new Vending();
        vendingMachine.applyDiscount("Candy", 0.20);
        vendingMachine.setPrice("Candy", 1.75);

        assertEquals(1.75, vendingMachine.getItem("Candy").getPrice(), "Price of Candy should be updated to $1.75");
    }

    @Test
    void testMarkItemAsBestseller() {
        Vending vendingMachine = new Vending();

        vendingMachine.setBestseller("Candy", true);

        assertTrue(vendingMachine.isBestseller("Candy"), "Candy should be marked as a bestseller");
    }

    @Test
    void testMarkItemAsNotBestseller() {
        Vending vendingMachine = new Vending();


        vendingMachine.setBestseller("Candy", true);

        vendingMachine.setBestseller("Candy", false);

        assertFalse(vendingMachine.isBestseller("Candy"), "Candy should not be marked as a bestseller");
    }

    @Test
    void testMarkNonExistentItemAsBestseller() {
        Vending vendingMachine = new Vending();

        vendingMachine.setBestseller("Soda", true);

        assertFalse(vendingMachine.isBestseller("Soda"), "Soda should not be marked as a bestseller");
    }

    @Test
    void testMarkMultipleItemsAsBestsellers() {
        Vending vendingMachine = new Vending();

        vendingMachine.setBestseller("Candy", true);
        vendingMachine.setBestseller("Gum", true);

        assertTrue(vendingMachine.isBestseller("Candy"), "Candy should be a bestseller");
        assertTrue(vendingMachine.isBestseller("Gum"), "Gum should be a bestseller");
    }

    @Test
    void testCheckBestsellerStatusForItems() {
        Vending vendingMachine = new Vending();

        vendingMachine.setBestseller("Candy", true);

        assertTrue(vendingMachine.isBestseller("Candy"), "Candy should be a bestseller");
        assertFalse(vendingMachine.isBestseller("Gum"), "Gum should not be a bestseller");
    }

}
