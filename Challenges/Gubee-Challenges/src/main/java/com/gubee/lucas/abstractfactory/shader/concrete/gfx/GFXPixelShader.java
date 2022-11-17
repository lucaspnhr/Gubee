package com.gubee.lucas.abstractfactory.shader.concrete.gfx;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;

public class GFXPixelShader implements PixelShader {
    @Override
    public void processPixelShader() {
        System.out.println("Processing pixel shader v2.0");
    }
}
