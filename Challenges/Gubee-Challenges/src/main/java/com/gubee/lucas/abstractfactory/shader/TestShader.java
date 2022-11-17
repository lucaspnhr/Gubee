package com.gubee.lucas.abstractfactory.shader;

import com.gubee.lucas.abstractfactory.shader.interfaces.PixelShader;
import com.gubee.lucas.abstractfactory.shader.interfaces.ShaderFactory;

public class TestShader {
    public static void main(String[] args) {
        ShaderFactory shaderFactory = ShaderManager.getShader(ShaderManager.VideoCard.GEFORCE_FX);

        PixelShader pixelShader = shaderFactory.createPixelShader();

        pixelShader.processPixelShader();

        shaderFactory = ShaderManager.getShader(ShaderManager.VideoCard.GEFORCE_EIGHT);

        pixelShader = shaderFactory.createPixelShader();

        pixelShader.processPixelShader();
    }
}
