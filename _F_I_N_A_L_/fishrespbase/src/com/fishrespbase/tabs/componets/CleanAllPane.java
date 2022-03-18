package com.fishrespbase.tabs.componets;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.tabs.BGR;
import com.fishrespbase.tabs.General;
import com.fishrespbase.tabs.Results;
import com.fishrespbase.tabs.Traits;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class CleanAllPane extends BaseTitledPane {

    public CleanAllPane() {
        super("Make a fresh start?", 0, 0, DPIkiller.kill(230), DPIkiller.kill(60));
    }

    @Override
    protected void createContent() {
        Button cleanAllButton = new ColoredButton(true, Constants.OBJECT_RED,"Clean All Data");
        cleanAllButton.setMaxSize(DPIkiller.kill(110), DPIkiller.kill(25));
        cleanAllButton.setMinSize(DPIkiller.kill(110), DPIkiller.kill(25));
        cleanAllButton.relocate(DPIkiller.kill(60), DPIkiller.kill(5));

        cleanAllButton.setOnAction(
            (ActionEvent event) -> {
                Main.cleanPlots();

                RLogger.flush();

                General.tab.resetTab();

                BGR.tab.resetTab();
                Traits.trait1.resetTab();
                Traits.trait2.resetTab();
                Results.tab.resetTab();

                Main.tabPane.getSelectionModel().select(0);

                AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));
            });

        contentPane.getChildren().add(cleanAllButton);
    }

    public void relocate() {
        relocate(DPIkiller.kill(5),Main.baseScene.getHeight()-DPIkiller.kill(102));
    }

    public void reset() {

    }
}
