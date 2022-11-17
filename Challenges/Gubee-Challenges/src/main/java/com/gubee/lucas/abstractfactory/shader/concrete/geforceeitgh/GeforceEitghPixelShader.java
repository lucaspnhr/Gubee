package com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;
import com.gubee.lucas.annotation.Transactional;

public class GeforceEitghPixelShader implements PixelShader {

    @Transactional
    @Override
    public void processPixelShader() {
        System.out.println("Processing pixel shader v4.0");
    }
}
