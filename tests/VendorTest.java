import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
    void buyTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void addition() {
        assertEquals(2, 1 + 1);
    }

}