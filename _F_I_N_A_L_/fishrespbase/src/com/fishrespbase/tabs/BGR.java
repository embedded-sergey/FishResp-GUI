package com.fishrespbase.tabs;

import com.fishrespbase.components.BaseTab;
import com.fishrespbase.components.Constants;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.components.Saver;
import com.fishrespbase.tabs.componets.CleanAllPane;
import com.fishrespbase.tabs.componets.GraphModule;
import com.fishrespbase.tabs.componets.for_bgr.DataPane;
import javafx.beans.value.ObservableValue;


/**
 * Created by R7Bishop on 19.02.2017.
 */
public class BGR extends BaseTab {
    public static BGR tab = null;
    private int tests;
    /*GraphModule*/
    private GraphModule graphModule;
    /*GraphModule*/

    /*DataPane*/
    private DataPane preDataPane, postDataPane;
    private boolean bgrReady;
    /*DataPane*/

    /*CleanAllPane*/
    private CleanAllPane cleanAllPane;
    /*CleanAllPane*/
    public BGR(){
        super("Background respiration", Constants.OBJECT_GRAY);
        tests = Constants.BGR_NONE;
        graphModule = new GraphModule();
        preDataPane = new DataPane("Pre data", graphModule, "pre", DPIkiller.kill(5),DPIkiller.kill(5),DPIkiller.kill(230),DPIkiller.kill(255));
        postDataPane = new DataPane("Post data", graphModule, "post", DPIkiller.kill(5),DPIkiller.kill(265),DPIkiller.kill(230),DPIkiller.kill(255));
        cleanAllPane = new CleanAllPane();

        mainPane.getChildren().addAll(preDataPane, postDataPane, graphModule.getPane(), cleanAllPane);

        mainPane.widthProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cleanAllPane.relocate();
            });
        mainPane.heightProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cleanAllPane.relocate();
            });

        bgrReady = false;
    }

    public boolean isBgrReady() {
        return bgrReady;
    }

    public void setBgrReady(boolean bgrReady) {
        this.bgrReady = bgrReady;
    }

    @Override
    public void resetTab() {
        preDataPane.reset();
        postDataPane.reset();

        graphModule.clear();

        bgrReady = false;

        recolorTab();
    }

    public void recolorTab() {
        if (!General.tab.isTableReady()
        ||  preDataPane.isDisabled() && postDataPane.isDisabled()) {
            changeTabColor(Constants.OBJECT_GRAY);
            return;
        }

        if (!preDataPane.isDisabled() && !postDataPane.isDisabled() && preDataPane.checkPane() && postDataPane.checkPane()
        ||  !preDataPane.isDisabled() &&  postDataPane.isDisabled() && preDataPane.checkPane()
        ||   preDataPane.isDisabled() && !postDataPane.isDisabled() && postDataPane.checkPane()) {
            changeTabColor(Constants.OBJECT_GREEN);

            bgrReady = true;
            if (General.tab.isTableReady()) {
                Traits.trait1.enable();//changeTabColor(Constants.OBJECT_YELLOW);
                if (Saver.isTrait2Enabled())
                    Traits.trait2.enable();//changeTabColor(Constants.OBJECT_YELLOW);
            }
        }
        else {
            changeTabColor(Constants.OBJECT_YELLOW);

            bgrReady = false;

            Traits.trait1.disable();//changeTabColor(Constants.OBJECT_GRAY);
            Traits.trait2.disable();//changeTabColor(Constants.OBJECT_GRAY);

        }
    }

    public void setChamberAndSoftware(String chamberNumber, String softwareName) {
        preDataPane.setChamber(chamberNumber);
        postDataPane.setChamber(chamberNumber);

        preDataPane.setSoftware(softwareName);
        preDataPane.setSoftware(softwareName);
    }

    public void enableTests(int PARAM) {

        switch (PARAM){
            case Constants.BGR_PREONLY:

                preDataPane.setVisible(true);
                postDataPane.setVisible(false);

                break;
            case Constants.BGR_POSTONLY:

                preDataPane.setVisible(false);
                postDataPane.setVisible(true);
                postDataPane.relocate(DPIkiller.kill(5),DPIkiller.kill(5));

                break;
            case Constants.BGR_PREPOST:

                preDataPane.setVisible(true);
                postDataPane.setVisible(true);
                postDataPane.relocate(DPIkiller.kill(5),DPIkiller.kill(265));

                break;
        }



    }

    public void enable(int PARAM) {
        resetTab();
        Traits.trait1.resetTab();
        Traits.trait2.resetTab();
        Results.tab.resetTab();
        switch (PARAM){
            case Constants.BGR_NONE:
                preDataPane.setEnabled(false);
                postDataPane.setEnabled(false);

                Traits.trait1.enable();
                if (Saver.isTrait2Enabled())
                    Traits.trait2.enable();//changeTabColor(Constants.OBJECT_YELLOW);
                break;
            case Constants.BGR_PREONLY:
                preDataPane.setEnabled(true);
                postDataPane.setEnabled(false);

                bgrReady = false;
                Traits.trait1.disable();
                Traits.trait2.disable();

                break;
            case Constants.BGR_POSTONLY:
                preDataPane.setEnabled(false);
                postDataPane.setEnabled(true);

                bgrReady = false;
                Traits.trait1.disable();
                Traits.trait2.disable();

                break;
            case Constants.BGR_PREPOST:
                preDataPane.setEnabled(true);
                postDataPane.setEnabled(true);

                bgrReady = false;
                Traits.trait1.disable();
                Traits.trait2.disable();
                break;
        }
        recolorTab();
    }

    public int getTests() {
        return tests;
    }
}
