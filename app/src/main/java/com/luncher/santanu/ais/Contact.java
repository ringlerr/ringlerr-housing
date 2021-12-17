package com.luncher.santanu.ais;

import java.util.ArrayList;

public class Contact {
    private final String mName;
    private final String mNumber;

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

    private static final int lastContactId = 0;

    public static ArrayList<Contact> createContactsList() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        contacts.add(new Contact("a", "+0"));
        contacts.add(new Contact("b", "+0"));
        contacts.add(new Contact("c", "+0"));
        contacts.add(new Contact("d", "+0"));
        contacts.add(new Contact("e", "+0"));

        return contacts;
    }
}
