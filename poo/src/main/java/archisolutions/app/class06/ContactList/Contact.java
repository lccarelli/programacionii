package archisolutions.app.class06.ContactList;

public class Contact {
    private int id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Contact(int id, String name, String lastName, String phoneNumber, String email){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateLastName(String lastName){
        this.lastName = lastName;
    }

    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void updateEmail(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Contacto: %s %s | Tel: %s | Email: %s",
                this.name, this.lastName, this.phoneNumber, this.email);
    }
}
