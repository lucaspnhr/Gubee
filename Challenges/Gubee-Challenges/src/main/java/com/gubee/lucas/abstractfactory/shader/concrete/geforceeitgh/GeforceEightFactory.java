package com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;
import com.gubee.lucas.abstractfactory.shader.interfaces.ShaderFactory;
import com.gubee.lucas.abstractfactory.shader.interfaces.VertexShader;

public class GeforceEightFactory implements ShaderFactory {
    @Override
    public PixelShader createPixelShader() {
        return new GeforceEitghPixelShader();
    }

    @Override
    public VertexShader createVertexShader() {
        return new GeforceEitghVertexShader();
    }
}
