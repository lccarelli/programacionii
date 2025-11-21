package archisolutions.app.class05.Hotel;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner sc = new Scanner(System.in);

        System.out.print("Nombre: ");
        String name = sc.nextLine();
        System.out.print("DNI: ");
        String dni = sc.nextLine();
        Huesped guest = new Huesped(name, dni);

        System.out.print("Elegí habitación (1-4): ");
        int number = Integer.parseInt(sc.nextLine());


    }
}
