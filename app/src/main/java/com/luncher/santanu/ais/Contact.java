package com.luncher.santanu.ais;

import java.util.ArrayList;

public class Contact {
    private String mName;
    private String mNumber;

    public Contact(String name, String number) {
        mName = name;
        mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }

    private static int lastContactId = 0;

    public static ArrayList<Contact> createContactsList() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        contacts.add(new Contact("Nich", "+6281394222888"));
        contacts.add(new Contact("Mike", "+6281211117425"));
        contacts.add(new Contact("Vishal", "+919599955675"));
        contacts.add(new Contact("Jyoti", "+919871860721"));
        contacts.add(new Contact("Santanu", "+917002019851"));

        return contacts;
    }
}
