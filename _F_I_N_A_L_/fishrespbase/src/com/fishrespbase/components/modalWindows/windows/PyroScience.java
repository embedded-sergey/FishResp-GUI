package com.fishrespbase.components.modalWindows.windows;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;

import java.io.File;
import java.net.URI;

/**
 * Created by R7Bishop on 08.11.2017.
 */
public class PyroScience extends Stage {
    private Pane root;
    private WebEngine webEngine;
    private ColoredButton exportButton;
    private TextField measureField, waitField;
    private ComboBox chamberComboBox;
    private ComboBox dateFormatComboBox;
    private TextField fileAdress1Field;
    private TextField filesAdress1Field;
    private Button openFiles1Button;
    public PyroScience() {
        super();
        root = new Pane();
        root.setStyle("-fx-background-color: #FFFFFF;");
        Scene scn = new Scene(root, DPIkiller.kill(635), DPIkiller.kill(345));
        scn.getStylesheets().add(getClass().getClassLoader().getResource("com/fishrespbase/css/modalWindow.css").toExternalForm());
        this.setScene(scn);
        this.setTitle("FishResp "+ Main.programVersion+": PyroScience");
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(Main.primaryStage);
        this.getIcons().add(new Image("com/fishrespbase/img/programIcon.png"));
        this.setResizable(false);

        //createContent();

        //this.showAndWait();
    }

    public void showWindow() {
        this.show();
        createContent();
        fileAdress1Field.setText("");
        chamberComboBox.setValue("");
        dateFormatComboBox.setValue("");
        measureField.setText("");
        waitField.setText("");
        filesAdress1Field.setText("");
        /*try {
            if (Main.OS == "mac")
                webEngine.load(new File(System.getenv("PDIR") + File.separator + "rMR.html").toURI().toURL().toString());
            else
                webEngine.load(new File(System.getProperty("user.dir") + File.separator + "rMR.html").toURI().toURL().toString());
        }
        catch (Exception e) {
            System.out.println("Tutorial opening exception: " + e);
        }*/
    }

    private void createContent() {
        root.getChildren().clear();
        Pane pane = new Pane();

        Label adress1Label = new Label("The address of the 'AquaResp' file");
        adress1Label.relocate(DPIkiller.kill(20),DPIkiller.kill(25));

        fileAdress1Field = new TextField();
        fileAdress1Field.relocate(DPIkiller.kill(20), DPIkiller.kill(50));
        fileAdress1Field.setPrefSize(DPIkiller.kill(190), DPIkiller.kill(25));
        fileAdress1Field.textProperty().addListener(observable -> askButton());

        FileChooser fileChooser = new FileChooser();

        Button openFile1Button = new Button();
        openFile1Button.relocate(DPIkiller.kill(210), DPIkiller.kill(50));
        openFile1Button.setPrefSize(DPIkiller.kill(25), DPIkiller.kill(25));
        openFile1Button.setStyle("-fx-background-image: url('/com/fishrespbase/img/OpenFileIcon.png')");
        openFile1Button.setOnAction(
                (ActionEvent event) -> {
                    fileChooser.setTitle("Select raw data");
                    if (Saver.getLastInputDirectory().length() > 0)
                        fileChooser.setInitialDirectory(new File(Saver.getLastInputDirectory()));
                    File selectedFile = fileChooser.showOpenDialog(Main.primaryStage);
                    if (selectedFile != null) {
                        Saver.setLastInputDirectory(selectedFile.getPath().substring(0,selectedFile.getPath().length()-selectedFile.getName().length()));
                        fileAdress1Field.setText(selectedFile.getPath());
                    }
                    askButton();
                });

        Label chambersLabel = new Label("Number of chambers");
        chambersLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(90));

        /**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/ObservableList<String> chamberOptions = FXCollections.observableArrayList("1","2","3","4");
        chamberComboBox = new ComboBox(chamberOptions);
        chamberComboBox.relocate(DPIkiller.kill(145),DPIkiller.kill(85));
        chamberComboBox.setPrefSize(DPIkiller.kill(90),DPIkiller.kill(25));
        chamberComboBox.setValue("");

        Label dateFormatLabel = new Label("Date format (PyroSci.)");
        dateFormatLabel.relocate(DPIkiller.kill(20),DPIkiller.kill(125));

        ObservableList<String> dateFormatOptions = FXCollections.observableArrayList("DMY", "MDY", "YMD");
        dateFormatComboBox = new ComboBox(dateFormatOptions);
        dateFormatComboBox.relocate(DPIkiller.kill(155), DPIkiller.kill(120));
        dateFormatComboBox.setPrefSize(DPIkiller.kill(80),DPIkiller.kill(20));
        dateFormatComboBox.setValue("");

        dateFormatComboBox.setOnAction(event -> {
            askButton();
        });

        Label measureLabel = new Label("Measure phase [s]");
        measureLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(160));

        measureField = new TextField();
        measureField.relocate(DPIkiller.kill(155), DPIkiller.kill(155));
        measureField.setPrefSize(DPIkiller.kill(80), DPIkiller.kill(25));
        measureField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue,0,9999)) {
                        measureField.setText(newValue);
                        askButton();
                    }
                    else
                        measureField.setText(oldValue);
                });
        Label waitLabel = new Label("Wait phase [s]");
        waitLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(195));

        waitField = new TextField();
        waitField.relocate(DPIkiller.kill(155), DPIkiller.kill(190));
        waitField.setPrefSize(DPIkiller.kill(80), DPIkiller.kill(25));
        waitField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue,0,9999)) {
                        waitField.setText(newValue);
                        askButton();
                    }
                    else
                        waitField.setText(oldValue);
                });


        Label adressesLabel = new Label("The address of the 'PyroScience' file");
        adressesLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(240));

        filesAdress1Field = new TextField();
        filesAdress1Field.relocate(DPIkiller.kill(20), DPIkiller.kill(270));
        filesAdress1Field.setPrefSize(DPIkiller.kill(190), DPIkiller.kill(25));
        filesAdress1Field.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    askButton();
                });

        openFiles1Button = new Button();
        //openFiles1Button.setDisable(true);
        openFiles1Button.relocate(DPIkiller.kill(210), DPIkiller.kill(270));
        openFiles1Button.setPrefSize(DPIkiller.kill(25), DPIkiller.kill(25));
        openFiles1Button.setStyle("-fx-background-image: url('/com/fishrespbase/img/OpenFileIcon.png')");
        openFiles1Button.setOnAction(
                (ActionEvent event) -> {
                    fileChooser.setTitle("Select raw data");
                    if (Saver.getLastInputDirectory().length() > 0)
                        fileChooser.setInitialDirectory(new File(Saver.getLastInputDirectory()));
                    File selectedFile = fileChooser.showOpenDialog(Main.primaryStage);
                    if (selectedFile != null) {
                        Saver.setLastInputDirectory(selectedFile.getPath().substring(0,selectedFile.getPath().length()-selectedFile.getName().length()));
                        filesAdress1Field.setText(selectedFile.getPath());
                    }
                    askButton();
                });



        exportButton = new ColoredButton(false, Constants.OBJECT_GRAY, "Convert and Export");
        exportButton.setPrefWidth(DPIkiller.kill(180));
        exportButton.relocate(DPIkiller.kill(40), DPIkiller.kill(305));

        exportButton.setOnAction(event -> {
            StringBuffer tmp = new StringBuffer(fileAdress1Field.getText());
            int length = tmp.length();
            for (int i = 0; i < length; i++) {
                if (tmp.charAt(i) == '\\')
                    tmp.insert(i++,'\\');
            }

            StringBuffer tmp1 = new StringBuffer(filesAdress1Field.getText());
            length = tmp1.length();
            for (int i = 0; i < length; i++) {
                if (tmp1.charAt(i) == '\\')
                    tmp1.insert(i++,'\\');
            }

            //pyroscience.aquaresp(pyroscience.file = "raw_data.txt",
            //            aquaresp.file = "summary.txt",
            //            fishresp.file = "fishresp.txt",
            //            date.format = "MDY",
            //            n.chamber = 1,
            //            wait.phase = 120,
            //            measure.phase = 600)
            String quest = "pyroscience.aquaresp(pyroscience.file = \""+tmp1.toString()+"\", ";
            quest += "aquaresp.file = \""+tmp.toString()+"\", ";

            FileChooser fileChooser1 = new FileChooser();
            fileChooser1.setTitle("Export the results");
            fileChooser1.setInitialDirectory(new File(Saver.getLastInputDirectory()));
            fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text table","*.txt"));
            File file = fileChooser1.showSaveDialog(Main.primaryStage);
            if (file != null) {
                Saver.setLastInputDirectory(file.getParent().toString());
                tmp = new StringBuffer(file.toString());
                if (Main.OS == "linux" && !tmp.toString().endsWith(".txt"))
                    tmp.append(".txt");
                length = tmp.length();
                for (int i = 0; i < length; i++) {
                    if (tmp.charAt(i) == '\\')
                        tmp.insert(i++,'\\');
                    //tmp.setCharAt(i, '/');
                }

                quest += "fishresp.file = \""+tmp+"\", ";
                quest += "date.format = \""+dateFormatComboBox.getValue()+"\", n.chamber = "+chamberComboBox.getValue()+", wait.phase = "+waitField.getText()+", measure.phase = "+measureField.getText()+")";
                //RLogger.log(quest);
                //System.out.println(quest);

                if (Main.re.eval(quest) != null) {
                    exportButton.changeColor(true, Constants.OBJECT_GREEN);
                }
            }
        });

        pane.getChildren().addAll(adress1Label,fileAdress1Field,openFile1Button, chambersLabel,chamberComboBox, dateFormatLabel,dateFormatComboBox,
                measureLabel,measureField, waitLabel,waitField, adressesLabel, filesAdress1Field, openFiles1Button, exportButton);
        /**вертикальная линия**/
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(DPIkiller.kill(260.0));
        moveTo.setY(DPIkiller.kill(30.0));
        LineTo vLineTo = new LineTo();
        vLineTo.setX(DPIkiller.kill(260.0));
        vLineTo.setY(DPIkiller.kill(325.0));
        path.getElements().add(moveTo);
        path.getElements().add(vLineTo);
        /**вертикальная линия**/

        WebView browser = new WebView();

        browser.relocate(DPIkiller.kill(260),DPIkiller.kill(0));
        browser.setPrefSize(DPIkiller.kill(375),DPIkiller.kill(355));
        //browser.prefWidthProperty().bind(Main.baseScene.widthProperty().subtract(435));
        //browser.prefHeightProperty().bind(Main.baseScene.heightProperty().subtract(70));

        webEngine = browser.getEngine();
        webEngine.setUserStyleSheetLocation(getClass().getClassLoader().getResource("com/fishrespbase/css/webEngine.css").toString());
        try {
            if (Main.OS == "mac")
                webEngine.load(new File(System.getenv("PDIR") + File.separator + "pyroscience.html").toURI().toURL().toString());
            else
                webEngine.load(new File(System.getProperty("user.dir") + File.separator + "pyroscience.html").toURI().toURL().toString());
        }
        catch (Exception e) {
            System.out.println("RMR opening exception: "+e);
        }


        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> o, Worker.State old, final Worker.State state) {


                Document doc = webEngine.getDocument();
                if (doc != null) {
                    NodeList nodeList = doc.getElementsByTagName("a");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        EventTarget tmp = ((EventTarget) nodeList.item(i));
                        ((EventTarget) nodeList.item(i)).addEventListener("click", evt -> Main.openWebPage(URI.create(tmp.toString())), false);
                    }

                }
            }
        });

        root.getChildren().addAll(pane,path,browser);
    }

    private void askButton() {
        if (dateFormatComboBox != null)
        {
            if (!fileAdress1Field.getText().isEmpty()
            &&  !chamberComboBox.getValue().toString().isEmpty()
            &&  !dateFormatComboBox.getValue().toString().isEmpty()
            &&  !measureField.getText().isEmpty()
            &&  !waitField.getText().isEmpty()
            &&  !filesAdress1Field.getText().isEmpty()) {
                exportButton.changeColor(true, Constants.OBJECT_YELLOW);
            }
            else
                exportButton.changeColor(false, Constants.OBJECT_GRAY);


            // &&  !filesAdress1Field.getText().isEmpty()
            ////   &&  !filesAdress2Field.getText().isEmpty()
            //    &&  !filesAdress3Field.getText().isEmpty()
            //    &&  !filesAdress4Field.getText().isEmpty()
        }
        /*if (toComboBox != null) {
            /*if (!fileAdressField.getText().isEmpty()
            && !softwareComboBox.getValue().toString().isEmpty()
            && !chamberComboBox.getValue().toString().isEmpty()
            && !salinityField.getText().isEmpty()
            && !barometricComboBox.getValue().toString().isEmpty()
            && !barometricField.getText().isEmpty()) {
                fromComboBox.setDisable(false);
                toComboBox.setDisable(false);
            } else {
                fromComboBox.setDisable(true);
                fromComboBox.setValue(" ");
                toComboBox.setDisable(true);
                toComboBox.setValue(" ");
            }
*/
            /*if (fromComboBox != null && toComboBox != null)
                if (fromComboBox.getValue() == " " || toComboBox.getValue() == " ") {
                    exportButton.changeColor(false, Constants.OBJECT_GRAY);
                } else {
                    exportButton.changeColor(true, Constants.OBJECT_YELLOW);
                }
            //exportButton.changeColor(false, Constants.OBJECT_GRAY);
        }*/
    }
}
