package com.labwork.newtoncolorwheel.runtime;

import com.labwork.newtoncolorwheel.core.general.Scene;

public final class Framework {

    private static Framework instance;

    private Scene scene;

    private Framework() {}

    public static Framework getInstance() {
        if (Framework.instance == null) {
            synchronized (Framework.class) {
                if (Framework.instance == null) {
                    Framework.instance = new Framework();
                }
            }
        }

        return Framework.instance;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void loadScene(Scene scene) {
        this.scene = scene;
    }
}
