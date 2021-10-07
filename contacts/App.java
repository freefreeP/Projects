package contacts;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App {

    private Scanner scanner = new Scanner(System.in);
    public static List<ContactsBase> records = new ArrayList<>();


    public void startApp() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println("[menu] Enter action (add, list, search, count, exit):");
        String input = scanner.nextLine();
        while (!input.equals("exit")) {
            switch (input) {
                case ("add"):
                    add();
                    break;
                case ("list"):
                    list();
                    break;
                case ("search"):
                    searchFunction();


                    search();
                    break;
                case ("count"):
                    count();
                    break;
                case ("exit"):
                    //info();
                    break;


            }
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            input = scanner.nextLine();

        }


    }


    private void add() {
        System.out.println("Enter the type (person, organization):");
        if (scanner.nextLine().equals("person")) {
            System.out.println("Enter the name:");
            String name = scanner.nextLine();

            System.out.println("Enter the surname:");
            String surname = scanner.nextLine();

            System.out.println("Enter the birth date:");
            String birthdateString = scanner.nextLine();

            String birthdate = null;
            if (birthdateString.equals("")) {
                System.out.println("Bad birth date!");
            } else {
                birthdate = birthdateString;
            }

            String gender = null;
            System.out.println("Enter the gender (M, F):");
            String genderChar = scanner.nextLine();
            if (genderChar.equals("") || !genderChar.equals("M") || !genderChar.equals("F")) {
                System.out.println("Bad gender!");

            } else {
                gender = scanner.nextLine();
            }


            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            Persons person = new Persons(number, name, surname, birthdate, gender);

        } else {
            System.out.println("Enter the organization name:");
            String name = scanner.nextLine();

            System.out.println("Enter the address:");
            String address = scanner.nextLine();

            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            Organisation organisation = new Organisation(number, name, address);
        }


    }

    private void searchFunction() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println("Enter search query:");
        List<ContactsBase> result = new ArrayList<>();
        String query = scanner.nextLine();
        for(ContactsBase record: records) {
            String compare = "";
            for(Field recordfields: record.changableFields()) {
                compare += record.getField(recordfields.getName());
            }
            compare += record.getPhoneNumber();
            if (compare.toLowerCase().contains(query.toLowerCase())) {
                result.add(record);
            }
        }

        System.out.println("Found "+ result.size() + " results:");
        if(result.size() > 0) {
            int counter = 1;
            for(ContactsBase record: result) {
                System.out.println(counter + ". " + record.getContactName());
                counter++;
            }
        }
        System.out.println();


        System.out.println("[search] Enter action ([number], back, again):");
        query = scanner.nextLine();
        while (!query.equals("back")) {
            if(query.equals("again")) {
                System.out.println("[search] Enter action ([number], back, again):");
                searchFunction();
            } else {
                info(result.get(Integer.parseInt(query)-1));
                ContactsBase contactsBase = result.get(Integer.parseInt(query)-1);

                System.out.println("[record] Enter action (edit, delete, menu):");
                String choice = scanner.nextLine();
                while (!choice.equals("menu")) {
                    switch (choice) {
                        case ("delete"):
                            records.remove(contactsBase);
                            break;
                        case ("edit"):
                            String output = "Select a field (";
                            for (Field field : contactsBase.changableFields()) {
                                output += field.getName().toLowerCase() + ", ";
                            }
                            output = output.substring(0,output.length()-2);
                            output = output + ", number)";
                            System.out.println(output);



                            String input = scanner.nextLine();
                            System.out.println("Enter " + input + ":");
                            String field = input.substring(0,1).toUpperCase().concat(input.substring(1,input.length()));
                            System.out.println(field);

                            input = scanner.nextLine();
                            System.out.println(input);
                            contactsBase.setField(field, input);
                            System.out.println("Saved");

                            info(contactsBase);

                            break;


                    }
                    System.out.println("[record] Enter action (edit, delete, menu):");
                    choice = scanner.nextLine();

                }
                return;


            }
        }

        return;
    }

    private void search() {




    }

    private void list() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (records.size() != 0) {
            int counter = 1;
            for (ContactsBase record : records) {
                System.out.println(counter + ". " + record.getContactName()); //+ ", " + record.getPhoneNumber());
                counter++;
            }

            System.out.println();

            System.out.println("[list] Enter action ([number], back):");
            String input = scanner.nextLine();
            if (input.equals("back")) {
                return;
            } else {
                ContactsBase contactsBase = records.get(Integer.parseInt(input) - 1);
                info(contactsBase);


                System.out.println("[record] Enter action (edit, delete, menu):");
                String choice = scanner.nextLine();
                while (!choice.equals("menu")) {
                    switch (choice) {
                        case ("delete"):
                            records.remove(contactsBase);
                            break;
                        case ("edit"):
                            String output = "Select a field (";
                            for (Field field : contactsBase.changableFields()) {
                            output += field.getName().toLowerCase() + ", ";
                            }
                            output = output.substring(0,output.length()-2);
                            output = output + ", number)";
                            System.out.println(output);



                            input = scanner.nextLine();
                            System.out.println("Enter " + input + ":");
                            String field = input.substring(0,1).toUpperCase().concat(input.substring(1,input.length()));
                            System.out.println(field);

                            input = scanner.nextLine();
                            System.out.println(input);
                            contactsBase.setField(field, input);
                            System.out.println("Saved");

                            info(contactsBase);

                            break;


                    }
                    System.out.println("[record] Enter action (edit, delete, menu):");
                    choice = scanner.nextLine();

                }
                return;

            }

//            System.out.println("Select a record:");
//            records.remove(records.get(Integer.parseInt(scanner.nextLine()) - 1));
//            System.out.println("The record removed!");

        } else {
            System.out.println("No records!");
        }
    }

    private void remove() {
        if (records.size() != 0) {
            int counter = 1;
            for (ContactsBase record : records) {
                System.out.println(counter + ". " + record.getContactName()); //+ ", " + record.getPhoneNumber());
                counter++;
            }
            System.out.println("Select a record:");
            records.remove(records.get(Integer.parseInt(scanner.nextLine()) - 1));
            System.out.println("The record removed!");

        } else {
            System.out.println("No records to remove!");
        }

    }

    private void info(ContactsBase contactsBase) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for (Field field : contactsBase.changableFields()) {
            switch (field.getName()) {
                case ("Birth"):
                    System.out.println(field.getName() + " date: " + contactsBase.getField(field.getName()));
                    break;
                case ("Organization"):
                    System.out.println(field.getName() + " name: " + contactsBase.getField(field.getName()));
                    break;
                default:
                    System.out.println(field.getName() + ": " + contactsBase.getField(field.getName()));

            }


        }
        System.out.println("Number: " + contactsBase.getPhoneNumber());
        System.out.println("Time created: " + contactsBase.getCreated());
        System.out.println("Time last edit: " + contactsBase.lastEdit);
        System.out.println();


    }

    private void edit() {
        if (records.size() != 0) {
            int counter = 1;
            for (ContactsBase record : records) {
                System.out.println(counter + ". " + record.getContactName()); //+ ", " + record.getPhoneNumber());
                counter++;
            }
            System.out.println("Select a record:");
            ContactsBase edit = records.get(Integer.parseInt(scanner.nextLine()) - 1);

            if (edit.isPerson()) {
                Persons editPerson = (Persons) edit;
                System.out.println("Select a field (name, surname, birth, gender, number):");
                String input = scanner.nextLine();
                System.out.println("Enter " + input);

                switch (input) {
                    case ("number"):
                        editPerson.setPhoneNumber(scanner.nextLine());
                        editPerson.lastEdit = LocalDateTime.now();
                        break;
                    case ("surname"):
                        editPerson.setSurname(scanner.nextLine());
                        editPerson.lastEdit = LocalDateTime.now();
                        break;
                    case ("name"):
                        editPerson.setName(scanner.nextLine());
                        editPerson.lastEdit = LocalDateTime.now();

                        break;
                    case ("birth"):
                        editPerson.setBirth(scanner.nextLine());
                        editPerson.lastEdit = LocalDateTime.now();

                        break;
                    case ("gender"):
                        editPerson.setGender(scanner.nextLine());
                        editPerson.lastEdit = LocalDateTime.now();

                        break;
                }
            } else {
                Organisation editOrganisation = (Organisation) edit;
                System.out.println("Select a field (organization name, address, number):");
                String input = scanner.nextLine();
                System.out.println("Enter " + input);

                switch (input) {
                    case ("organization name"):
                        editOrganisation.setPhoneNumber(scanner.nextLine());
                        editOrganisation.lastEdit = LocalDateTime.now();

                        break;
                    case ("address"):
                        editOrganisation.setAddress(scanner.nextLine());
                        editOrganisation.lastEdit = LocalDateTime.now();

                        break;
                    case ("number"):
                        editOrganisation.setPhoneNumber(scanner.nextLine());
                        editOrganisation.lastEdit = LocalDateTime.now();

                        break;

                }
            }

            System.out.println("The record updated!");


        } else {
            System.out.println("No records to edit!");
        }

        System.out.println();
    }

    private void count() {
        System.out.println("The Phone Book has " + records.size() + " records.");
    }


}
