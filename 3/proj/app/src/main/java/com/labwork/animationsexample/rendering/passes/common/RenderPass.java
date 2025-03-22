package com.labwork.animationsexample.rendering.passes.common;

import java.util.List;
import com.labwork.animationsexample.core.general.Shader;
import com.labwork.animationsexample.core.general.Entity;

public abstract class RenderPass {
    protected Shader shader;

    public RenderPass(Shader shader) {
        this.shader = shader;
    }

    public abstract void execute(List<Entity> dispatchedEntities);
}