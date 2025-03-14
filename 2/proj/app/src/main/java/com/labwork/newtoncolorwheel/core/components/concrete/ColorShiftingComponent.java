package com.labwork.newtoncolorwheel.core.components.concrete;

import com.labwork.newtoncolorwheel.core.general.Color;
import com.labwork.newtoncolorwheel.core.general.Entity;
import com.labwork.newtoncolorwheel.core.components.common.Component;

public class ColorShiftingComponent extends Component {

    private final int colorChannelMinValue = 0;
    private final int colorChannelMaxValue = 255;

    private Color color;
    private int r, g, b;
    private int step = 1;

    public ColorShiftingComponent(Entity entity) {
        super(entity);
    }

    @Override
    public void onStart() {
        this.color = super.getEntity().getComponent(RenderingComponent.class).getMaterial().getColorAlbedo();
        this.r = this.color.getR();
        this.g = this.color.getG();
        this.b = this.color.getB();
    }

    @Override
    public void onUpdate() {
        this.r -= this.step;
        this.g -= this.step;
        this.b -= this.step;

        if (this.r >= this.colorChannelMaxValue || this.r <= this.colorChannelMinValue)
            this.step = -this.step;

        this.color.setR(this.r);
        this.color.setG(this.g);
        this.color.setB(this.b);
    }
}
