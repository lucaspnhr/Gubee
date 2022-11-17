package com.gubee.lucas.abstractfactory.shader;

import com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh.GeforceEightFactory;
import com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh.GeforceEitghPixelShader;
import com.gubee.lucas.abstractfactory.shader.concrete.geforceeitgh.GeforceEitghVertexShader;
import com.gubee.lucas.abstractfactory.shader.concrete.gfx.GFXFactory;
import com.gubee.lucas.abstractfactory.shader.interfaces.ShaderFactory;

public final class ShaderManager {

    private ShaderManager(){}

    public static ShaderFactory getShader(VideoCard videoCard){
        return videoCard.getShaderFactory();
    }

    enum VideoCard{
        GEFORCE_EIGHT(new GeforceEightFactory()),
        GEFORCE_FX(new GFXFactory());

        private final ShaderFactory shaderFactory;

        VideoCard(ShaderFactory shaderFactory) {
            this.shaderFactory = shaderFactory;
        }

        public ShaderFactory getShaderFactory() {
            return shaderFactory;
        }
    }
}
