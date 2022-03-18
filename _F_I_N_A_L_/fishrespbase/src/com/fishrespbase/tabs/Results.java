package com.fishrespbase.tabs;


import com.fishrespbase.components.BaseTab;
import com.fishrespbase.components.Constants;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.components.Saver;
import com.fishrespbase.tabs.componets.CleanAllPane;
import com.fishrespbase.tabs.componets.GraphModule;
import com.fishrespbase.tabs.componets.for_results.ExtractSlopesPane;
import com.fishrespbase.tabs.componets.for_results.QCofSlopesPane;
import com.fishrespbase.tabs.componets.for_results.ResultsOfAnalysisPane;
import javafx.beans.value.ObservableValue;

/**
 * Created by R7Bishop on 20.02.2017.
 */
public class Results extends BaseTab {
    public static Results tab;

    private boolean extract1Ready, extract2Ready;
    private boolean trait2State;

    private GraphModule graphModule;

    private ExtractSlopesPane firstExtractSlopesPane, secondExtractSlopesPane;
    private QCofSlopesPane qcofSlopesPane;
    private ResultsOfAnalysisPane resultsOfAnalysisPane;
    private CleanAllPane cleanAllPane;
    public Results(){
        super("Results", Constants.OBJECT_GRAY);

        //trait1Ready = trait2Ready = false;
        extract1Ready = extract2Ready = false;
        trait2State = true;

        graphModule = new GraphModule();

        firstExtractSlopesPane = new ExtractSlopesPane("Trait 1",1, DPIkiller.kill(5),DPIkiller.kill(5),DPIkiller.kill(230),DPIkiller.kill(150));
        secondExtractSlopesPane = new ExtractSlopesPane("Trait 2",2,DPIkiller.kill(5),DPIkiller.kill(160),DPIkiller.kill(230),DPIkiller.kill(150));
        qcofSlopesPane = new QCofSlopesPane(DPIkiller.kill(5),DPIkiller.kill(315),DPIkiller.kill(230),DPIkiller.kill(150));
        resultsOfAnalysisPane = new ResultsOfAnalysisPane(DPIkiller.kill(5),DPIkiller.kill(470),DPIkiller.kill(230),DPIkiller.kill(120));
        cleanAllPane = new CleanAllPane();

        mainPane.getChildren().addAll(firstExtractSlopesPane, secondExtractSlopesPane, qcofSlopesPane, resultsOfAnalysisPane,
                graphModule.getPane(), cleanAllPane);

        mainPane.widthProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cleanAllPane.relocate();
            });
        mainPane.heightProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cleanAllPane.relocate();
            });
    }

    @Override
    public void resetTab() {
        extract1Ready = false;
        extract2Ready = false;

        firstExtractSlopesPane.reset();
        secondExtractSlopesPane.reset();
        qcofSlopesPane.reset();
        resultsOfAnalysisPane.reset();
        graphModule.clear();
        changeTabColor(Constants.OBJECT_GRAY);
    }

    public String firsttrait = "", secondtrait = "";
    public void renameTraits(String first, String second, int trait2) {
        firsttrait = first;
        secondtrait = second;
        firstExtractSlopesPane.renameTrait(first);
        secondExtractSlopesPane.renameTrait(second);
        if (trait2 != -1) {
            qcofSlopesPane.renameTraits(first, second);
            resultsOfAnalysisPane.renameTraits(first, second);
        }
        else {
            qcofSlopesPane.renameTraits(first, "");
            resultsOfAnalysisPane.renameTraits(first, "");
        }
    }

    public void enable() {
        resetTab();
        changeTabColor(Constants.OBJECT_YELLOW);

        firstExtractSlopesPane.enable();
        secondExtractSlopesPane.enable();
        qcofSlopesPane.reset();
        resultsOfAnalysisPane.reset();
    }

    public void check() {
        /*switch (INDEX) {
            case 1:
                trait1Ready = state;
                break;
            case 2:
                trait2Ready = state;
                break;
        }
        */

        if (Traits.trait1.isCompleted() && (Traits.trait2.isCompleted() || !Saver.isTrait2Enabled()))
            enable();
        else
            resetTab();
    }

    private int trait1QC = 0, trait2QC = 0;
    public void setCompleteTrait(int INDEX, int state) {
        /*state 0 - not completed, state 1 - enabledQC, state 2 - disabledQC*/
        if (INDEX == 1)
            trait1QC = state;
        else
            trait2QC = state;

        if (trait1QC == 2 && (trait2QC == 2 || !trait2State)) {
            qcofSlopesPane.setVisible(false);
            if (trait2State)
                resultsOfAnalysisPane.relocate(DPIkiller.kill(5),DPIkiller.kill(315));//315|160|440
            else
                resultsOfAnalysisPane.relocate(DPIkiller.kill(5),DPIkiller.kill(160));
        }
        else {
            qcofSlopesPane.setVisible(true);
            if (trait2State)
                resultsOfAnalysisPane.relocate(DPIkiller.kill(5),DPIkiller.kill(470));
            else
                resultsOfAnalysisPane.relocate(DPIkiller.kill(5),DPIkiller.kill(315));//315|160|440

            qcofSlopesPane.renameTraits(firsttrait,secondtrait);
            if (trait1QC == 2) {
                qcofSlopesPane.renameTraits(secondtrait,"");
            }
            if (trait2QC == 2 || !trait2State) {
                qcofSlopesPane.renameTraits(firsttrait,"");
            }
        }

    }

    public void secondTrait(boolean state) {
        if (trait2State != state) {
            if (state) {
                //mainPane.getChildren().add(secondExtractSlopesPane);
                secondExtractSlopesPane.setVisible(true);
                qcofSlopesPane.relocate(DPIkiller.kill(5), DPIkiller.kill(315));
                resultsOfAnalysisPane.relocate(DPIkiller.kill(5), DPIkiller.kill(410));
            }
            else {
                //mainPane.getChildren().remove(secondExtractSlopesPane);
                secondExtractSlopesPane.setVisible(false);
                qcofSlopesPane.relocate(DPIkiller.kill(5), DPIkiller.kill(160));
                resultsOfAnalysisPane.relocate(DPIkiller.kill(5), DPIkiller.kill(285));
            }
            trait2State = state;
        }
    }

    public void extractComplete(boolean state, int INDEX) {
        switch (INDEX) {
            case 1:
                extract1Ready = state;
                break;
            case 2:
                extract2Ready = state;
                break;
        }

        if (extract1Ready && (extract2Ready || !Saver.isTrait2Enabled())) {
            resultsOfAnalysisPane.enable();
            qcofSlopesPane.enable();
        }
        else {
            qcofSlopesPane.reset();
            resultsOfAnalysisPane.reset();
            graphModule.clear();
        }
    }

    public GraphModule getGraphModule() {
        return graphModule;
    }

    public void setChamberNumbers() {
        qcofSlopesPane.setChamberNumbers();
    }

    public int getTargetTrait() {
        return qcofSlopesPane.getTargetTrait();
    }

    public void setActiveTrait(int trait) {
        qcofSlopesPane.setActiveTrait(trait);
    }

    public int getQCChamber() {
        return qcofSlopesPane.getChamber();
    }

    public void resetTrait(int targetTrait) {
        if (targetTrait == 1) {
            firstExtractSlopesPane.reset();
            firstExtractSlopesPane.enable();
            extract1Ready = false;
        } else {
            secondExtractSlopesPane.reset();
            secondExtractSlopesPane.enable();
            extract2Ready = false;
        }
        qcofSlopesPane.reset();
        resultsOfAnalysisPane.reset();
        graphModule.clear();
    }
}
