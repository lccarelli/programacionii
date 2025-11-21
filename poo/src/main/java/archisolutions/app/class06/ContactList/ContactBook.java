package archisolutions.app.class06.ContactList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ContactBook {

    private static final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Contact> contacts;
    private int lastId = 0;

    public ContactBook() {
        this.contacts = new ArrayList<>();
    }

    public Contact addNewContact(String name, String lastName, String phoneNumber, String email){
        this.lastId++;
        Contact newContact = new Contact(this.lastId, name, lastName, phoneNumber, email);
        this.contacts.add(newContact);
        return newContact;
    }

    public boolean removeContact(int id){
        Contact contactToRemove = searchContactById(id);

        if(contactToRemove != null) {
            return this.contacts.remove(contactToRemove);
        }
        return false;
    }

    public List<Contact> searchContactsByLastName(String lastName){
        return this.contacts
                .stream()
                .filter(contact -> contact.getLastName()
                        .toLowerCase()
                        .contains(lastName.toLowerCase())
                )
                .collect(Collectors.toList());
    }

    public Contact searchContactById(int id){
        return this.contacts
                .stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void handleOptions(){
        while (true) {
            showMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    handleAddNewContact();
                    break;
                case "2":
                    handleRemoveContact();
                    break;
                case "3":
                    handleModifyContact();
                    break;
                case "4":
                    handleListAllContacts();
                    break;
                case "5":
                    handleSearchByLastName();
                    break;
                case "6":
                    System.out.println("¡Hasta pronto!");
                    return; // end process
                default:
                    System.out.println("Opción no válida. Por favor, elige un número del 1 al 6.");
            }
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
        }
    }

    /**
     * Muestra el menú de opciones al usuario.
     * Esta es la única responsabilidad de este método.
     */
    private static void showMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Agregar un nuevo contacto (Alta)");
        System.out.println("2. Eliminar un contacto (Baja)");
        System.out.println("3. Modificar un contacto");
        System.out.println("4. Listar todos los contactos");
        System.out.println("5. Buscar contactos por apellido");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    }

    private void handleAddNewContact(){
        System.out.println("\n--- Agregar Nuevo Contacto ---");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Apellido: ");
        String lastName = scanner.nextLine();
        System.out.print("Teléfono: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Contact createdContact = this.addNewContact(name, lastName, phone, email);
        System.out.println("¡Contacto agregado con éxito! ID asignado: " + createdContact.getId());
    }

    private void handleRemoveContact() {
        System.out.println("\n--- Eliminar Contacto ---");
        System.out.print("Ingresa el ID del contacto a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (this.removeContact(id)) {
                System.out.println("Contacto eliminado correctamente.");
            } else {
                System.out.println("Error: No se encontró ningún contacto con el ID " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un número de ID válido.");
        }
    }

    private void handleModifyContact() {
        System.out.println("\n--- Modificar Contacto ---");
        System.out.print("Ingresa el ID del contacto a modificar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Contact contactToModify = this.searchContactById(id);

            if (contactToModify == null) {
                System.out.println("Error: No se encontró ningún contacto con ese ID.");
                return;
            }

            System.out.println("Datos actuales: " + contactToModify);
            System.out.println("Ingresa los nuevos datos (deja en blanco para no cambiar).");

            System.out.print("Nuevo nombre (" + contactToModify.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.isBlank()) contactToModify.updateName(newName);

            System.out.print("Nuevo apellido (" + contactToModify.getLastName() + "): ");
            String newLastName = scanner.nextLine();
            if (!newLastName.isBlank()) contactToModify.updateLastName(newLastName);

            System.out.print("Nuevo teléfono (" + contactToModify.getPhoneNumber() + "): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.isBlank()) contactToModify.updatePhoneNumber(newPhone);

            System.out.print("Nuevo email (" + contactToModify.getEmail() + "): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isBlank()) contactToModify.updateEmail(newEmail);

            System.out.println("Contacto actualizado.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un número de ID válido.");
        }
    }

    private void handleListAllContacts() {
        System.out.println("\n--- Lista Completa de Contactos ---");
        List<Contact> allContacts = this.listAllContacts();
        if (allContacts.isEmpty()) {
            System.out.println("Tu agenda está vacía.");
        } else {
            allContacts.forEach(System.out::println);
        }
    }

    private void handleSearchByLastName() {
        System.out.println("\n--- Búsqueda por Apellido ---");
        System.out.print("Ingresa las primeras letras del apellido a buscar: ");
        String prefix = scanner.nextLine();
        List<Contact> results = this.searchContactsByLastName(prefix);

        if (results.isEmpty()) {
            System.out.println("No se encontraron contactos cuyo apellido comience con '" + prefix + "'.");
        } else {
            System.out.println("Resultados encontrados:");
            results.forEach(System.out::println);
        }
    }

    /**
     * Devuelve una copia de la lista de todos los contactos.
     * @return Una lista con todos los contactos.
     */
    private List<Contact> listAllContacts() {
        return new ArrayList<>(this.contacts);
    }

}
