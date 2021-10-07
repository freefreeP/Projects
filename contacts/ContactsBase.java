package contacts;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

abstract  public class ContactsBase {
    private String Number = "";

    public String getPhoneNumber() {
        return Number;
    }

    private boolean isPerson;

    public boolean isPerson() {
        return isPerson;
    }

    LocalDateTime created;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(LocalDateTime lastEdit) {
        this.lastEdit = lastEdit;
    }

    LocalDateTime lastEdit;

    abstract String getContactName();

    ContactsBase(String phoneNumber,boolean isPerson) {
        this.Number = phoneNumber;
        this.isPerson = isPerson;
        this.created = LocalDateTime.now().withSecond(0).withNano(0);
        this.lastEdit = LocalDateTime.now().withSecond(0).withNano(0);
        setPhoneNumber(phoneNumber);


        System.out.println("A record created!");
    }

    abstract Field[] changableFields() throws ClassNotFoundException;

    abstract void setField(String field, String value) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    abstract String getField(String field) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;


    public boolean hasNumber() {
        if(Number.equals("")) {
            return true;
        }
        return false;
    }

    private  boolean isNumberValid(String phoneNumber) {
        String[] groups = phoneNumber.split("(-| )");

        boolean group1 = groups[0].matches("\\+?([\\w]{1,}|\\([\\w]{1,}\\))");

        if (groups.length > 1 && group1 == true) {

            if (groups[0].matches("\\+?\\([\\w]{1,}\\)") && groups[1].matches("\\+?\\([\\w]{2,}\\)")) {
                return false;
            }

            int counter = 0;

            for (String group : groups) {
                if (counter == 0) {
                    counter++;
                    continue;
                }

                if (!group.matches("\\+?([\\w]{2,}|\\([\\w]{2,}\\))") && counter == 1) {
                    return false;
                }

                if (!group.matches("\\+?[\\w]{2,}") && counter != 1 ) {
                    return false;
                }

                counter++;

            }


            return true;
        }
        return group1;
    }



    public void setPhoneNumber(String phoneNumber) {
        boolean isNumberValid = isNumberValid(phoneNumber);


        if(!isNumberValid) {
            System.out.println("Wrong number format!");
            this.Number = "[no number]";
        }

        if(isNumberValid) {
            this.Number = phoneNumber;
        }

    }


}
