package com.fishrespbase.components.alerts;

import com.fishrespbase.components.DPIkiller;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Created by R7Bishop on 16.03.2017.
 */
public final class AlertAccordion extends Pane {
    private static AlertAccordion _instance = null;

    private double x, y;
    private double padding;
    private ArrayList<SingleAlert> alerts;
    private static synchronized void checkInstance() {
        if (_instance == null) {
            _instance = new AlertAccordion();
            _instance.alerts = new ArrayList<>();
        }
    }

    public static synchronized void pushMessage(SingleAlert alert) {
        checkInstance();

        _instance.alerts.add(alert);
        _instance.getChildren().add(alert);

        recombine();
    }

    public static synchronized AlertAccordion getInstance() {
        checkInstance();

        return _instance;
    }

    public static synchronized void remove(SingleAlert alert) {
        if (alert.index >= _instance.alerts.size())
            return;
        _instance.alerts.remove(alert.index);
        _instance.getChildren().remove(alert);
        recombine();
    }

    public void rebase(double w, double h){
        _instance.x = w+DPIkiller.kill(-250-5);
        _instance.y = h;
        super.relocate(x,y-_instance.padding);
    }

    private static synchronized void recombine() {
        _instance.padding = 0;
        for (int i = 0; i < _instance.getChildren().size(); i++) {
            _instance.getChildren().get(i).relocate(DPIkiller.kill(0), DPIkiller.kill(_instance.padding));
            if (_instance.alerts.size() == i)
                break;
            _instance.padding += _instance.alerts.get(i).getPrefHeight()+DPIkiller.kill(5);
        }
        _instance.relocate(_instance.x,_instance.y-_instance.padding);
    }
}
