package com.labwork.newtoncolorwheel.rendering.passes.common;

import java.util.List;
import com.labwork.newtoncolorwheel.core.general.Entity;

public abstract class RenderPass {

    public abstract void execute(List<Entity> dispatchedEntities);
}