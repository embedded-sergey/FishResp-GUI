package com.fishrespbase.components;

import javafx.scene.text.Text;

/**
 * Created by R7Bishop on 20.01.2018.
 */
public class DPIkiller {
    private static final double rem = Math.rint(new Text("").getLayoutBounds().getHeight());
    public static final double getRem() {
        return rem;
    }
    public static final double kill(double value) {
        return value;
    }
}
