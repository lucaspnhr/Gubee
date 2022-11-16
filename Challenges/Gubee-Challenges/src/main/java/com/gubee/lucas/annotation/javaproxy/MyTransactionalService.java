package com.gubee.lucas.annotation.javaproxy;

import com.gubee.lucas.annotation.Transactional;

public interface MyTransactionalService {

    void exampleMethod(String message);

    class MyTransactionalServiceImp implements MyTransactionalService {
        @Override
        @Transactional
        public void exampleMethod(String message) {
            System.out.println(message);
        }
    }

}
