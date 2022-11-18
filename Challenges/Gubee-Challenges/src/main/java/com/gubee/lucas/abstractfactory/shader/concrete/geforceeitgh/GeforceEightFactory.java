package com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;
import com.gubee.lucas.abstractfactory.shader.interfaces.ShaderFactory;
import com.gubee.lucas.abstractfactory.shader.interfaces.VertexShader;
import com.gubee.lucas.annotation.abstractandproxy.PixelShaderProxy;

public class GeforceEightFactory implements ShaderFactory {
    @Override
    public PixelShader createPixelShader() {
        return new PixelShaderProxy(new GeforceEitghPixelShader());
    }

    @Override
    public VertexShader createVertexShader() {
        return new GeforceEitghVertexShader();
    }
}
