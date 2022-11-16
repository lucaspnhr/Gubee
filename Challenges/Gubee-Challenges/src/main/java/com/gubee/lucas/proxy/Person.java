package com.gubee.lucas.proxy;

import javax.xml.namespace.QName;

public class Person extends Human{


    public Person(String name, int age) {
        super(name, age);
    }

    @Override
    public void walk() {
        System.out.println("Im "+ getName() +" and Im Walking ...." +" and I am "+getAge());

    }

    @Override
    public void talk() {
        System.out.println("Im "+ getName() +" and Im Talking ...." +" and I am "+getAge());
    }
}
