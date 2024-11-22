import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class VendorTest {
    static Vending v;
    private Vending vendor;

    @BeforeEach
    public void setUp() {
        v = new Vending(5, 5);
        vendor = new Vending(10, 5);
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
        assertEquals(0, v.getStock("Candy"), "Candy should be removed from stock after renaming.");
        assertEquals(5, v.getStock("Gum"), "Gum should retain its original stock value after renaming.");
    }

    @Test
    void testVendorInventoryInitialization() {
        assertEquals(10, vendor.getStock("Candy"), "Vendor should have 10 Candy.");
        assertEquals(5, vendor.getStock("Gum"), "Vendor should have 5 Gum.");
    }

    @Test
    void testMultipleVendorsInventory() {
        Vending[] vendors = new Vending[5];
        vendors[0] = new Vending(10, 5);
        vendors[1] = new Vending(20, 10);
        vendors[2] = new Vending(30, 15);
        vendors[3] = new Vending(5, 25);
        vendors[4] = new Vending(50, 0);

        for (int i = 0; i < vendors.length; i++) {
            assertNotNull(vendors[i], "Vendor " + (i + 1) + " should be initialized.");
            System.out.println("Testing Vendor " + (i + 1) + " inventory:");
            vendors[i].printInventory();
        }
    }
}
