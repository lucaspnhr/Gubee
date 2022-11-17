package com.gubee.lucas.abstractfactory.shader.interfaces;

public interface ShaderFactory {
    PixelShader createPixelShader();
    VertexShader createVertexShader();
}
