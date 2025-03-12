package com.labwork.exampleopengles.rendering.passes.common;

import java.util.List;
import com.labwork.exampleopengles.core.general.Entity;

public abstract class RenderPass {

    public abstract void execute(List<Entity> dispatchedEntities);
}