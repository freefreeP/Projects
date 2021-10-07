package contacts;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

public class Organisation extends ContactsBase{
    private String Organization;
    private String Address;

    public String getOrganization() {
        return Organization ;
    }

    public void setOrganization(String organization_name) {
        this.Organization  = organization_name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    Organisation(String phoneNumber, String organization_name, String address) {
        super(phoneNumber,false);
        this.Address = address;
        this.Organization  = organization_name;
        App.records.add(this);
        System.out.println();


    }


    @Override
    String getContactName() {
        return Organization ;
    }

    @Override
    Field[] changableFields() throws ClassNotFoundException {
        return Class.forName("contacts.Organisation").getDeclaredFields();
    }

    @Override
    void setField(String field, String value) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(Field tmpField: changableFields()) {
            if (tmpField.getName().toLowerCase().equals(field.toLowerCase())) {
                Class.forName("contacts.Organisation").getDeclaredMethod("set" + field, String.class).invoke(this,value);
                this.lastEdit = LocalDateTime.now().withSecond(0).withNano(0);
            }
        }
    }

    @Override
    String getField(String field) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       return (String) Class.forName("contacts.Organisation").getDeclaredMethod("get" + field).invoke(this);

    }
}
