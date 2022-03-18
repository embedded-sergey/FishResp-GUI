package com.fishrespbase.components.modalWindows.windows;

import com.fishrespbase.Main;
import com.fishrespbase.components.ColoredButton;
import com.fishrespbase.components.Constants;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.components.RLogger;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.components.modalWindows.ModalWindowManager;
import com.fishrespbase.tabs.Results;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.rosuda.JRI.REXP;

public class CleanWindowQC extends Stage {
    private Pane root;

    public CleanWindowQC() {
        super();
        root = new Pane();
        root.setStyle("-fx-background-color: #FFFFFF;");

        Scene scn = new Scene(root, DPIkiller.kill(240), DPIkiller.kill(150));
        scn.getStylesheets().add(getClass().getClassLoader().getResource("com/fishrespbase/css/modalWindow.css").toExternalForm());
        this.setScene(scn);
        //this.setTitle("FishResp "+ Main.programVersion+": PreSens");
        //this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(Main.primaryStage);
        this.getIcons().add(new Image("com/fishrespbase/img/programIcon.png"));
        this.setResizable(false);
    }

    public void showWindow() {
        this.show();
        createContent();
    }

    private void createContent() {
        Label label = new Label("Which measurement phases should be\nremoved for a selected chamber?");
        label.relocate(DPIkiller.kill(25), DPIkiller.kill(25));

        //Label textInput = new Label("c(\"M01\", \"M02\")");
        //textInput.relocate(41, 74);
        //textInput.setTextFill(Color.GRAY);

        TextField textField = new TextField();
        textField.relocate(DPIkiller.kill(35), DPIkiller.kill(70));
        textField.setPrefWidth(DPIkiller.kill(180));
        textField.setText("c(\"M1\", \"M2\")");
        //textField.setStyle("-fx-text-fill: white;");
        /*textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() < 1) {
                textInput.setText("c(\"M01\", \"M02\")");
                textInput.setTextFill(Color.GRAY);
            }
            else {
                textInput.setText(newValue);
                textInput.setTextFill(Color.BLACK);
            }
        });*/

        ColoredButton coloredButton = new ColoredButton(true, Constants.OBJECT_RED, "Remove phases");
        coloredButton.relocate(DPIkiller.kill(55), DPIkiller.kill(105));
        coloredButton.setPrefWidth(DPIkiller.kill(140));
        coloredButton.setOnAction(event -> {
            SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Removing phases");
            AlertAccordion.pushMessage(alert);

            String quest = "Trait." + Results.tab.getTargetTrait() + ".clean <- rm.data(Trait." + Results.tab.getTargetTrait() + ".clean, chamber = \"CH" + Results.tab.getQCChamber() + "\", M.phase = " + textField.getText() + ")";
            RLogger.log(quest);
            REXP x;
            x = Main.re.eval(quest);
            if (x != null) {
                AlertAccordion.remove(alert);
                AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));

                Results.tab.resetTrait(Results.tab.getTargetTrait());

                ModalWindowManager.closeModal("CleanWindowQC");
                //Results.tab.getGraphModule().setBaseFilename("QC.slope_",x.asInt());
                //System.out.println(x.asInt()+"!!!!!!!!!!!!!!!!!!!!!!!");
            }
            else {
                AlertAccordion.remove(alert);
                AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING,Constants.DEFAULT_ALERTLIFETIME,"Check the vector format"));
            }
        });

        root.getChildren().addAll(label, textField, /*textInput,*/ coloredButton);
    }
}
