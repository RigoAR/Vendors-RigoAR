import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


public class VendorTest {
    static Vending v;

    @BeforeEach
    public void setUp() {
        v = new Vending(5, 5);
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
    void testEmptyInventory()
    {
        v.addMoney(20.00);

        while (v.getStock("Candy") > 0)
        {
            v.select("Candy");
        }

        while (v.getStock("Gum") > 0)
        {
            v.select("Gum");
        }

        assertTrue(v.isInventoryEmpty(), "The inventory should be empty after all items are purchased.");
    }

    @Test
    void testInventoryNotEmpty()
    {
        assertFalse(v.isInventoryEmpty(), "Inventory should not be empty initially.");
    }

}