package com.gubee.lucas.annotation.javaproxy;

public class TestJavaProxy {
    public static void main(String[] args) {
        MyTransactionalService proxied = Processor.create(MyTransactionalService.class, new MyTransactionalService.MyTransactionalServiceImp());

        proxied.exampleMethod("I am in a transaction");
    }
}
