package com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;

public class GeforceEitghPixelShader implements PixelShader {
    @Override
    public void processPixelShader() {
        System.out.println("Processing pixel shader v4.0");
    }
}
