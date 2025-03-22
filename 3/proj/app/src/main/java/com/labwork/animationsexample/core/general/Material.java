package com.labwork.animationsexample.core.general;

import java.util.Map;
import java.util.HashMap;

public final class Material {

    private Color colorAlbedo;
    private final Map<Class<?>, Shader> shaders;

    public Material(Color base, Shader... shaders) {
        this.colorAlbedo = base;
        this.shaders = new HashMap<>();

        for (Shader shader : shaders)
            this.shaders.put(shader.getRenderPass(), shader);
    }

    public Color getColorAlbedo() {
        return this.colorAlbedo;
    }

    public void setColorAlbedo(Color value) {
        this.colorAlbedo = value;
    }

    public void setShader(Shader shader) {
        this.shaders.put(shader.getRenderPass(), shader);
    }

    public Shader getShader(Class<?> renderPass) {
        return this.shaders.getOrDefault(renderPass, null);
    }
}
