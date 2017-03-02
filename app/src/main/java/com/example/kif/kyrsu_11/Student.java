package com.example.kif.kyrsu_11;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Kif on 29.12.2016.
 */

@Root
public class Student {
    @Element
    @SerializedName("FirstName")
    public String FirstName;

    @Element
    @SerializedName("LastName")
    public String LastName;

    @Attribute
    @SerializedName("Age")
    public int Age;

    public Student(String firstName, String lastName, int age) {
        FirstName = firstName;
        LastName = lastName;
        Age = age;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getAge() {
        return Age;
    }

    public Student() {
        super();
    }
}
