package com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh;

import com.gubee.lucas.abstractfactory.shader.interfaces.VertexShader;

public class GeforceEitghVertexShader implements VertexShader {
    @Override
    public void processVertexShader() {
        System.out.println("Processing vertex shader v4.0");
    }
}
