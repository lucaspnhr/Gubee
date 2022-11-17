package com.gubee.lucas.annotation.javaproxy;

import com.gubee.lucas.annotation.Transactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Processor {

    static class Handler implements InvocationHandler{

        private Object target;

        public Handler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method declaredMethods = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

            if(declaredMethods.isAnnotationPresent(Transactional.class)){
                System.out.printf("Iniciando execução do método $%s.%s %n",method.getName(), target.getClass().getSimpleName());
                try{
                    method.invoke(target, args);

                    System.out.printf("Finalizando execução do método $%s.%s com sucesso %n",method.getName(),target.getClass().getSimpleName());
                }catch (Exception e){
                    System.out.printf("Finalizando execução do método $%s.%s com error %n",method.getName(),target.getClass().getSimpleName());
                    System.out.println(e);
                }

            }

            return proxy;
        }
    }

    public static <T> T create(Class<T> clazz, Object target){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[] {clazz}, new Handler(target));
    }
}
