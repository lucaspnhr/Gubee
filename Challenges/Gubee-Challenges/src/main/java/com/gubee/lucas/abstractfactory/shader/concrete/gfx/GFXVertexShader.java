package com.gubee.lucas.abstractfactory.shader.concrete.gfx;

import com.gubee.lucas.abstractfactory.shader.interfaces.VertexShader;

public class GFXVertexShader implements VertexShader {
    @Override
    public void processVertexShader() {
        System.out.println("Processing vertex shader v2.0");
    }
}
