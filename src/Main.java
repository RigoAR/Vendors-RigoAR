public class Main {
    public static void main(String[] args) {
        Vending machine = new Vending();
        machine.addMoney(10.00);
        System.out.println("Balance: " + machine.getBalance());
        machine.select("Candy");
        System.out.println("Balance after purchase: " + machine.getBalance());
        System.out.println("Stock of Candy: " + machine.getStock("Candy"));
    }
}
