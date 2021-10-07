package contacts;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Persons extends ContactsBase {
    private String Name;

    public String getName() {
        return Name;
    }

    private String Surname;

    public void setName(String name) {
        this.Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        this.Surname = surname;
    }

    public String getBirth() {
        return Birth;
    }

    public void setBirth(String birthdate) {
        this.Birth = birthdate;
    }


    private String Birth;

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    private String Gender;


    Persons(String phoneNumber, String name, String surname, String birthdate, String gender) {
        super(phoneNumber,true);
        this.Name = name;
        this.Surname = surname;
        this.Birth = birthdate == null ? "[no data]" : birthdate;
        this.Gender = gender == null ? "[no data]" : gender;
        App.records.add(this);
        System.out.println();


    }


    @Override
    String getContactName() {
        return Name + " " + Surname;
    }

    @Override
    Field[] changableFields() throws ClassNotFoundException {
        return Class.forName("contacts.Persons").getDeclaredFields();
    }



    @Override
    void setField(String field, String value) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(Field tmpField: changableFields()) {
            System.out.println(tmpField.getName());
            if(tmpField.getName().equalsIgnoreCase(field)) {
                Class.forName("contacts.Persons").getDeclaredMethod("set"+field, String.class).invoke(this,value);
                this.lastEdit = LocalDateTime.now().withSecond(0).withNano(0);            }

        }
//        Class.forName("contacts.Persons").getDeclaredMethod("set"+field, String.class).invoke(this,value);
//        this.lastEdit = LocalDateTime.now().withSecond(0).withNano(0);


    }

    @Override
    String getField(String field) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
                return (String) Class.forName("contacts.Persons").getDeclaredMethod("get" + field).invoke(this);



    }
}
