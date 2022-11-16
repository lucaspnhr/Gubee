package com.gubee.lucas.proxy;

import java.util.HashMap;
import java.util.Map;

public class HumanProxy extends Human{

    private Human realHuman;
    private Map<String, Integer> calls;

    public HumanProxy(Human realHuman) {
        super("", 0);
        calls = new HashMap<>();
        this.realHuman = realHuman;
    }

    @Override
    public void walk() {
        calls.merge("walk", 1, Integer::sum);
        realHuman.walk();
    }

    @Override
    public void talk() {
        calls.merge("talk", 1, Integer::sum);
        realHuman.talk();
    }

    @Override
    public String toString() {
        return calls.toString();
    }
}
