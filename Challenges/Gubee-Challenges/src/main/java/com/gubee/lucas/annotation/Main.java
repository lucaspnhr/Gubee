package com.gubee.lucas.annotation;

import com.gubee.lucas.annotation.javaproxy.MyTransactionalService;
import com.gubee.lucas.annotation.javaproxy.Processor;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        MyTransactionalService proxied = Processor.create(MyTransactionalService.class, new MyTransactionalService.MyTransactionalServiceImp());

        proxied.exampleMethod("I am in a transaction");
    }
}