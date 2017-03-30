package jp.niro.jimcon.tutorial;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Created by niro on 2017/03/29.
 */
public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;


    public Person() {
        this("", "");
    }

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("some city");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1987, 9, 4));
    }

    // The getter and setter of "firstName"
    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }

    // The getter and setter of "lastName"
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }

    // The getter and setter of "street"
    public String getStreet(){
        return street.get();
    }
    public void setStreet(String street){
        this.street.set(street);
    }
    public StringProperty getStreetProperty(){
        return street;
    }

    // The getter and setter of "postalCode"
    public int getPostalCode(){
        return postalCode.get();
    }
    public void setPostalCode(int postalCode){
        this.postalCode.set(postalCode);
    }
    public IntegerProperty getIntegerProperty(){
        return postalCode;
    }

    // The getter and setter of "city"
    public String getCity(){
        return city.get();
    }
    public void setCity(String city){
        this.city.set(city);
    }
    public StringProperty getCityProperty(){
        return city;
    }

    // The getter and setter of "birthday"
    public LocalDate getBirthday(){
        return birthday.get();
    }
    public void setBirthday(LocalDate birthday){
        this.birthday.set(birthday);
    }
    public ObjectProperty<LocalDate> getBirthdayProperty(){
        return birthday;
    }

}
