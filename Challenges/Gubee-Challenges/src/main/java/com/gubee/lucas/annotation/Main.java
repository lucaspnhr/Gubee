package com.gubee.lucas.annotation;

import com.gubee.lucas.abstractfactory.shader.ShaderManager;
import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;
import com.gubee.lucas.abstractfactory.shader.interfaces.ShaderFactory;
import com.gubee.lucas.annotation.abstractandproxy.PixelShaderProxy;

public class Main {
    public static void main(String[] args)  {
//        MyTransactionalService proxied = Processor.create(MyTransactionalService.class, new MyTransactionalService.MyTransactionalServiceImp());
//
//        proxied.exampleMethod("I am in a transaction");

        ShaderFactory shaderFactory = ShaderManager.getShader(ShaderManager.VideoCard.GEFORCE_EIGHT);

        PixelShader pixelShader = shaderFactory.createPixelShader();


        pixelShader.processPixelShader();
    }
}