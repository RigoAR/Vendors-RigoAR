public class Main {
    public static void main(String[] args) {
        Vending[] vendors = new Vending[5];
        vendors[0] = new Vending(10, 5);
        vendors[1] = new Vending(20, 10);
        vendors[2] = new Vending(30, 15);
        vendors[3] = new Vending(5, 25);
        vendors[4] = new Vending(50, 0);


        for (int i = 0; i < vendors.length; i++) {
            System.out.println("Vendor " + (i + 1) + " inventory:");
            vendors[i].printInventory();
            System.out.println();
        }
    }
}