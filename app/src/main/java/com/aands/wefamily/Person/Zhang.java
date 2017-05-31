package com.aands.wefamily.Person;

/**
 * Created by renwendi on 17/5/31.
 */

public class Zhang {
    private String name;
    private String age;
    private String email;
    private String address;
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }


    public Zhang(String name, String age, String email, String address) {
        super();
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", email=" + email
                + ", address=" + address + "]";
    }
}
