package com.labwork.illuminationexample.rendering.passes.common;

import java.util.List;
import com.labwork.illuminationexample.core.general.Shader;
import com.labwork.illuminationexample.core.general.Entity;

public abstract class RenderPass {
    protected Shader shader;

    public RenderPass(Shader shader) {
        this.shader = shader;
    }

    public abstract void execute(List<Entity> dispatchedEntities);
}