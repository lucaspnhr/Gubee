package com.gubee.lucas.proxy;

public class TestProxy {
    public static void main(String[] args)  {
        Person person = new Person("Lucas", 22);
        Human proxied = new HumanProxy(person);

        proxied.walk();
    }
}
