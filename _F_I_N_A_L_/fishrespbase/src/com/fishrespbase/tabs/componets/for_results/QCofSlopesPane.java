package com.fishrespbase.tabs.componets.for_results;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.components.modalWindows.ModalWindowManager;
import com.fishrespbase.tabs.Results;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.rosuda.JRI.REXP;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class QCofSlopesPane extends BaseTitledPane {

    private TextField lengthNow, lengthAlter;
    private ComboBox<String> traitsCombobox;
    private ComboBox<Integer> chambersCombobox;
    private ObservableList<String> traitsOptions;
    private ObservableList<Integer> chambersOptions;
    private ColoredButton button, cleanButton;
    private boolean costyl;

    public QCofSlopesPane(double x, double y, double w, double h) {
        super("QC of slopes", x, y, w, h);
        this.setDisable(true);
    }

    @Override
    protected void createContent() {
        Label traitsLabel = new Label("Target trait");
        traitsLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(10));

        traitsOptions = FXCollections.observableArrayList("Trait 1","Trait 2");
        traitsCombobox = new ComboBox(traitsOptions);
        traitsCombobox.relocate(DPIkiller.kill(80),DPIkiller.kill(5));
        traitsCombobox.setPrefSize(DPIkiller.kill(140),DPIkiller.kill(20));
        traitsCombobox.setValue("Trait 1");

        traitsCombobox.setOnAction(event -> {
                askButton();
            });

        Label lengthLabel = new Label("Length [s]:  now");
        lengthLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(40));

        lengthNow = new TextField("");
        lengthNow.relocate(DPIkiller.kill(95),DPIkiller.kill(35));
        lengthNow.setPrefWidth(DPIkiller.kill(45));

        Label alterLabel = new Label("alter");
        alterLabel.relocate(DPIkiller.kill(145),DPIkiller.kill(40));

        lengthAlter = new TextField("");
        lengthAlter.relocate(DPIkiller.kill(175),DPIkiller.kill(35));
        lengthAlter.setPrefWidth(DPIkiller.kill(45));

        Label chamberLabel = new Label("Chamber No.");
        chamberLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(70));

        chambersOptions = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
        chambersCombobox = new ComboBox(chambersOptions);
        chambersCombobox.relocate(DPIkiller.kill(80),DPIkiller.kill(65));
        chambersCombobox.setPrefSize(DPIkiller.kill(45),DPIkiller.kill(20));
        chambersCombobox.setValue(1);
        chambersCombobox.getStyleClass().add("my-list-cell");

        Label resLabel = new Label("Residuals");
        resLabel.relocate(DPIkiller.kill(145),DPIkiller.kill(70));

        CheckBox resCheckBox = new CheckBox();
        resCheckBox.relocate(DPIkiller.kill(200),DPIkiller.kill(70));

        costyl = false;
        chambersCombobox.setOnAction(event -> {
                if (costyl)
                    askButton();
            });

        cleanButton = new ColoredButton(true, Constants.OBJECT_GRAY, "Remove Data");
        cleanButton.setMaxWidth(DPIkiller.kill(100));
        cleanButton.setMinWidth(DPIkiller.kill(100));
        cleanButton.relocate(DPIkiller.kill(10), DPIkiller.kill(95));
        cleanButton.setOnAction(event -> {
            ModalWindowManager.createModal("CleanWindowQC");
        });


        button = new ColoredButton(true, Constants.OBJECT_GRAY, "Plot QC");
        button.setPrefWidth(DPIkiller.kill(100));
        button.relocate(DPIkiller.kill(120), DPIkiller.kill(95));

        button.setOnAction(event -> {
            if (traitsCombobox.getValue().length()>0) {
                SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Plotting a graph");
                AlertAccordion.pushMessage(alert);

                int INDEX = 1;// = (traitsCombobox.getValue() == traitsOptions.get(0))?1:2;
                if (traitsCombobox.getValue() == Results.tab.firsttrait)
                    INDEX = 1;
                if (traitsCombobox.getValue() == Results.tab.secondtrait)
                    INDEX = 2;
                else
                    System.out.println(traitsCombobox.getValue());
                String quest = "n.graph <- QC.slope(Trait."+INDEX+".slope, Trait."+INDEX+".clean, chamber = \"CH"+chambersCombobox.getValue()+"\", current = " + lengthNow.getText() + ", alter = " + lengthAlter.getText() + ", residuals = "+(resCheckBox.isSelected()?"T":"F")+")";
                RLogger.log(quest);
                REXP x;
                x = Main.re.eval(quest);
                if (x != null) {
                    AlertAccordion.remove(alert);
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));

                    int graphsCount = x.asInt() == 0 ? Double.valueOf(x.asDouble()).intValue() : x.asInt();
                    Results.tab.getGraphModule().setBaseFilename("QC.slope_", graphsCount);
                    //System.out.println(x.asInt()+"!!!!!!!!!!!!!!!!!!!!!!!");
                }
                else {
                    AlertAccordion.remove(alert);
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR,Constants.DEFAULT_ALERTLIFETIME,"An error occurred while plotting a graph"));
                }
            }
        });

        contentPane.getChildren().addAll(traitsLabel,traitsCombobox,lengthLabel,lengthNow,alterLabel,lengthAlter,chamberLabel,chambersCombobox,resLabel,resCheckBox,cleanButton, button);
    }

    public void renameTraits(String first, String second) {
        traitsOptions.set(0, first);
        if (traitsOptions.size()==2)
            if (second.length()==0)
                traitsOptions.remove(1);
            else
                traitsOptions.set(1, second);
        else
            if (second.length()!=0)
                traitsOptions.add(1,second);

        traitsCombobox.setValue(first);
    }

    public void reset() {
        traitsCombobox.setValue(traitsCombobox.getItems().get(0));
        chambersCombobox.setValue(1);
        lengthNow.setText("");
        lengthAlter.setText("");
        cleanButton.changeColor(false, Constants.OBJECT_GRAY);
        button.changeColor(false,Constants.OBJECT_GRAY);
        this.setDisable(true);
    }

    public void enable() {
        cleanButton.changeColor(true, Constants.OBJECT_RED);
        button.changeColor(true,Constants.OBJECT_BLUE);
        this.setDisable(false);
    }

    private void askButton() {
        if (this.isDisabled()) {
            button.changeColor(true, Constants.OBJECT_GRAY);
        } else {
            button.changeColor(true, Constants.OBJECT_BLUE);
        }
        Results.tab.changeTabColor(Constants.OBJECT_YELLOW);
    }

    public void setChamberNumbers() {
        chambersOptions = FXCollections.observableArrayList(1);
        for (int i = 2; i <= Integer.parseInt(Saver.getDefaultChamberNumber()); i++)
            chambersOptions.add(i);
        costyl = true;
        chambersCombobox.setItems(chambersOptions);
        costyl = false;
    }

    public int getTargetTrait() {
        if (traitsCombobox.getValue().equals(traitsCombobox.getItems().get(0)))
            return 1;
        else
            return 2;
    }

    public void setActiveTrait(int trait) {
        traitsCombobox.setValue(traitsCombobox.getItems().get(trait == 2 ? 1 : 0));
    }

    public int getChamber() {
        return chambersCombobox.getValue();
    }
}
