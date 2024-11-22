import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VendorTest {
    static Vending v;
    Vending[] vendors;

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
        v.renameItem("Candy", "Gum");
        assertEquals(-1, v.getStock("Candy"), "Candy should be removed from stock after renaming.");
        assertEquals(5, v.getStock("Gum"), "Gum should retain its original stock value after renaming.");
    }

    @Test
    void testRenameItem() {
        assertEquals(5, v.getStock("Candy"), "Initial stock of Candy should be 5.");

        int result = v.renameItem("Candy", "Gum");
        assertEquals(5, result, "Rename should be successful.");

        assertEquals(-1, v.getStock("Candy"), "Candy should be removed after renaming.");

        assertEquals(5, v.getStock("Gum"), "New Gum should have the same stock as Candy.");

        v.printInventory();
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
        assertEquals(5, v.getStock("Candy"), "Restocking with negative amount should not change stock.");
    }
}
