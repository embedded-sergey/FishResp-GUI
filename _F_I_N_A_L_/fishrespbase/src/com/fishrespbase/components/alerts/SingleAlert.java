package com.fishrespbase.components.alerts;

import com.fishrespbase.components.DPIkiller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by R7Bishop on 16.03.2017.
 */
public class SingleAlert extends Pane {
    public int index;
    private Timeline timeline;

    private Label title;

    public SingleAlert(int TYPE, int lifeTime, String message) {
        this(TYPE, lifeTime, "", message);

        switch (TYPE) {
            case AlertType.WARNING:
                title.setText("Warning");
                break;
            case AlertType.SUCCESS:
                title.setText("Success");
                break;
            case AlertType.ERROR:
                title.setText("Error");
                break;
            case AlertType.WAIT:
                title.setText("Wait please");
                break;
            case AlertType.WARNING2LINES:
                title.setText("Warning");
                break;
        }
    }

    public SingleAlert(int TYPE, int lifeTime, String label, String message) {
        super();
        this.setPrefWidth(DPIkiller.kill(250));
        this.setPrefHeight(DPIkiller.kill(60));

        Pane bg = new Pane();
        bg.getStylesheets().add(getClass().getClassLoader().getResource("com/fishrespbase/css/alertMessage.css").toExternalForm());
        bg.setPrefWidth(DPIkiller.kill(250));
        bg.setPrefHeight(DPIkiller.kill(60));

        bg.getStyleClass().add("alertBG");

        Image img = null;
        ImageView imgView = null;

        switch (TYPE) {
            case AlertType.NONE:
                bg.setStyle("-fx-base: #ECECEC;");
                break;
            case AlertType.WARNING:
                img = new Image("com/fishrespbase/img/alert/warning.png");
                bg.setStyle("-fx-base: #FFFF80;");
                break;
            case AlertType.SUCCESS:
                img = new Image("com/fishrespbase/img/alert/success.png");
                bg.setStyle("-fx-base: #85FF85;");
                break;
            case AlertType.ERROR:
                img = new Image("com/fishrespbase/img/alert/error.png");
                bg.setStyle("-fx-base: #FF8080;");
                break;
            case AlertType.WAIT:
                img = new Image("com/fishrespbase/img/alert/wait.gif");
                bg.setStyle("-fx-base: #ECECEC;");
                break;
            case AlertType.WARNING2LINES:
                img = new Image("com/fishrespbase/img/alert/warning.png");
                bg.setStyle("-fx-base: #FFFF80;");
                break;
        }


        if (img != null) {
            imgView = new ImageView(img);
            imgView.relocate(DPIkiller.kill(5),DPIkiller.kill(25));
        }

        title = new Label(label);
        title.relocate(DPIkiller.kill(5),DPIkiller.kill(5));
        title.setFont(Font.font("System", FontWeight.BOLD, 12));

        Label x = new Label("x");
        x.relocate(DPIkiller.kill(235),DPIkiller.kill(-5));
        x.setFont(Font.font("System", FontWeight.BOLD, 18));
        x.setOnMouseClicked(event -> this.remove());

        Text text = new Text(message);

        this.getChildren().addAll(bg,title,x,text);

        if (TYPE == AlertType.NONE) {
            text.relocate(DPIkiller.kill(5), DPIkiller.kill(30));
            text.setWrappingWidth(DPIkiller.kill(245));
        }
        else {
            text.relocate(DPIkiller.kill(35),DPIkiller.kill(30));
            text.setWrappingWidth(DPIkiller.kill(215));
            this.getChildren().add(imgView);
        }

        if (TYPE == AlertType.WARNING2LINES || TYPE == AlertType.ERROR) {
            this.setPrefHeight(DPIkiller.kill(70));
            bg.setPrefHeight(DPIkiller.kill(70));
            imgView.relocate(DPIkiller.kill(5),DPIkiller.kill(35));
        }

        if (lifeTime>0) {
            SingleAlert tmp = this;
            timeline = new Timeline(new KeyFrame(new javafx.util.Duration(1000*lifeTime), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    tmp.remove();
                }
            }));
            timeline.play();
        }

}

    public void remove() {
        if (timeline != null )
            timeline.stop();
        AlertAccordion.remove(this);
    }
}
