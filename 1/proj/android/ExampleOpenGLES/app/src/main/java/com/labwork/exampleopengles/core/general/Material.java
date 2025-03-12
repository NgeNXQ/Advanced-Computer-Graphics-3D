package com.labwork.exampleopengles.core.general;

import java.util.Map;
import java.util.HashMap;

public final class Material {

    private Color base;
    private final Map<Class<?>, Shader> shaders;

    public Material(Color base, Shader... shaders) {
        this.base = base;
        this.shaders = new HashMap<>();

        for (Shader shader : shaders)
            this.shaders.put(shader.getRenderPass(), shader);
    }

    public Color getBase() {
        return this.base;
    }

    public Shader getShader(Class<?> renderPass) {
        return this.shaders.getOrDefault(renderPass, null);
    }
}
