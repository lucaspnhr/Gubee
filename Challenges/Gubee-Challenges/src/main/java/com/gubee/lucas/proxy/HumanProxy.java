package com.gubee.lucas.proxy;

import com.gubee.lucas.annotation.Transactional;

import java.lang.reflect.Method;
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
    public void walk()  {
        calls.merge("walk", 1, Integer::sum);
        try{
            Method walk = realHuman.getClass().getDeclaredMethod("walk");
            if (walk.isAnnotationPresent(Transactional.class)){
                System.out.println("I am a trasactional method");
            }
            realHuman.walk();
        }catch (Exception e){
            System.out.println(e);
        }
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
