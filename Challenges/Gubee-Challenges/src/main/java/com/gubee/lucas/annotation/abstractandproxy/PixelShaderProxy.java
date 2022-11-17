package com.gubee.lucas.annotation.abstractandproxy;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;
import com.gubee.lucas.annotation.Transactional;

import java.lang.reflect.Method;

public class PixelShaderProxy implements PixelShader {

    private final PixelShader realPixelShader;

    public PixelShaderProxy(PixelShader pixelShader) {
        this.realPixelShader = pixelShader;
    }

    @Override
    public void processPixelShader() {
        System.out.println("The method processPixelShader() is going to be execute "+realPixelShader.getClass().getSimpleName());
        try {
            Method processPixelShader = realPixelShader.getClass().getDeclaredMethod("processPixelShader");
            if (processPixelShader.isAnnotationPresent(Transactional.class)){
                System.out.println("Executing transactional method");
                realPixelShader.processPixelShader();
            }else{
                System.out.println("Executing a not transactional method");
            }
            System.out.println("The method was execute succefully");
        }catch (Exception e){
            System.out.println("The method was not execute, an error ocurred");
            System.err.println(e);
        }
    }
}
