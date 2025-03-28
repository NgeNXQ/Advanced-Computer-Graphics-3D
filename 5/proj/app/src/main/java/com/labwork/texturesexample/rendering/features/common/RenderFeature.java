package com.labwork.texturesexample.rendering.features.common;

import java.util.List;
import com.labwork.texturesexample.core.general.Shader;
import com.labwork.texturesexample.core.general.Entity;

public abstract class RenderFeature {
    protected Shader shader;

    public RenderFeature(Shader shader) {
        this.shader = shader;
    }

    public abstract void execute(List<Entity> dispatchedEntities);
}