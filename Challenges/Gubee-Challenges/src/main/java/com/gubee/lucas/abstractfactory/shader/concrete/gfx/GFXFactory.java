package com.gubee.lucas.abstractfactory.shader.concrete.gfx;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;
import com.gubee.lucas.abstractfactory.shader.interfaces.ShaderFactory;
import com.gubee.lucas.abstractfactory.shader.interfaces.VertexShader;
import com.gubee.lucas.annotation.abstractandproxy.PixelShaderProxy;

public class GFXFactory implements ShaderFactory {
    @Override
    public PixelShader createPixelShader() {
        return new PixelShaderProxy(new GFXPixelShader());
    }

    @Override
    public VertexShader createVertexShader() {
        return new GFXVertexShader();
    }
}
