package com.fishrespbase.tabs.componets;

import com.fishrespbase.Main;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.components.Saver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.fishrespbase.Main.TMPDIR;

/**
 * Created by R7Bishop on 23.02.2017.
 */
public class GraphModule {

    private TitledPane graphicalModule;
    private Button plus, minus, left, right, export;
    public Label zoomIndicator, pageIndicator;
    private Pane graph;
    private Pane borderGraph;
    public ImageView bgImage;
    private ImageView plotView;

    private int graphsCount, currentCount;
    private String baseName;
    private Rectangle2D viewPort;
    public int zoom;

    private ScrollBar horizontalScrollBar, verticalScrollBar;
    private Rectangle clip;

    private Event lastMouseEvent;
    boolean mouseEntered, mousePressed;

    boolean graphMousePressed;

    private double indicatorShift;

    public GraphModule() {
        graphsCount = 0;
        currentCount = 1;
        baseName = "";
        zoom = 1;
        indicatorShift = 0;

        lastMouseEvent = null;
        mouseEntered = mousePressed = false;

        graphMousePressed = false;

        graphicalModule = new TitledPane();

        graphicalModule.setText("Graphical module");
        graphicalModule.setCollapsible(false);
        graphicalModule.prefWidthProperty().bind(Main.baseScene.widthProperty().subtract(DPIkiller.kill(245)));
        graphicalModule.prefHeightProperty().bind(Main.baseScene.heightProperty().subtract(DPIkiller.kill(47)));

        graphicalModule.relocate(DPIkiller.kill(240),DPIkiller.kill(5));

        bgImage = new ImageView();
        bgImage.setImage(new Image("com/fishrespbase/img/grayLogo.png"));
        bgImage.setFitWidth(DPIkiller.kill(256));
        bgImage.setFitHeight(DPIkiller.kill(256));
        bgImage.setX(graphicalModule.getWidth()/2 - DPIkiller.kill(128));
        bgImage.setY(graphicalModule.getHeight()/2 - DPIkiller.kill(128));

        plus = new Button();
        minus = new Button();
        left = new Button();
        right = new Button();
        export = new Button("Export as .png file");

        pageIndicator = new Label("0/0");
        zoomIndicator = new Label("100%");

        plus.setMaxSize(DPIkiller.kill(25),DPIkiller.kill(25));
        plus.setMinSize(DPIkiller.kill(25),DPIkiller.kill(25));
        minus.setMaxSize(DPIkiller.kill(25),DPIkiller.kill(25));
        minus.setMinSize(DPIkiller.kill(25),DPIkiller.kill(25));
        left.setMaxSize(DPIkiller.kill(25),DPIkiller.kill(25));
        left.setMinSize(DPIkiller.kill(25),DPIkiller.kill(25));
        right.setMaxSize(DPIkiller.kill(25),DPIkiller.kill(25));
        right.setMinSize(DPIkiller.kill(25),DPIkiller.kill(25));
        export.setMaxSize(DPIkiller.kill(140),DPIkiller.kill(25));
        export.setMinSize(DPIkiller.kill(140),DPIkiller.kill(25));

        plus.setStyle("-fx-background-image: url('/com/fishrespbase/img/plus.png')");
        minus.setStyle("-fx-background-image: url('/com/fishrespbase/img/minus.png')");
        left.setStyle("-fx-background-image: url('/com/fishrespbase/img/left.png')");
        right.setStyle("-fx-background-image: url('/com/fishrespbase/img/right.png')");

        plus.setDisable(true);
        minus.setDisable(true);
        left.setDisable(true);
        right.setDisable(true);
        export.setDisable(true);

        left.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plotView.getImage()!=null && currentCount>=1) {
                    currentCount--;
                    load();
                }
                if (currentCount==1)
                    left.setDisable(true);
                if (currentCount<graphsCount)
                    right.setDisable(false);

            }
        });

        right.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plotView.getImage()!=null && currentCount<=graphsCount) {
                    currentCount++;
                    load();
                }
                if (currentCount>1)
                    left.setDisable(false);
                if (currentCount==graphsCount)
                    right.setDisable(true);
            }
        });

        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plotView.getImage()!=null) {
                    switch (zoom) {
                        case 1:
                            horizontalScrollBar.setVisible(true);
                            verticalScrollBar.setVisible(true);

                            horizontalScrollBar.setMax(DPIkiller.kill(776));
                            verticalScrollBar.setMax(DPIkiller.kill(582));

                            zoom = 2;

                            minus.setDisable(false);
                            break;
                        case 2:
                            horizontalScrollBar.setMax(DPIkiller.kill(776)*2);
                            verticalScrollBar.setMax(DPIkiller.kill(582)*2);

                            zoom = 4;

                            plus.setDisable(true);
                            break;
                    }
                    zoomIndicator.setText(zoom+"00%");
                    resizeGraph();
                }

            }
        });

        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plotView.getImage()!=null) {
                    switch (zoom) {
                        case 2:
                            plotView.relocate(DPIkiller.kill(1),DPIkiller.kill(1));

                            horizontalScrollBar.setVisible(false);
                            verticalScrollBar.setVisible(false);

                            zoom = 1;

                            minus.setDisable(true);
                            break;
                        case 4:
                            plotView.relocate(plotView.getLayoutX()/2,plotView.getLayoutY()/2);

                            horizontalScrollBar.setMax(DPIkiller.kill(776));
                            verticalScrollBar.setMax(DPIkiller.kill(582));

                            zoom = 2;

                            plus.setDisable(false);
                            break;
                    }
                    zoomIndicator.setText(zoom+"00%");
                    resizeGraph();
                }
            }
        });

        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Export a graph");
                fileChooser.setInitialDirectory(new File(Saver.getLastInputDirectory()));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG image","*.png"));
                File file = fileChooser.showSaveDialog(Main.primaryStage);
                if (file != null) {
                    Saver.setLastInputDirectory(file.getParent().toString());
                    try {
                        if (Main.OS == "windows")
                            Files.copy(Paths.get(TMPDIR+System.getProperty("file.separator") + baseName + currentCount + ".png"), Paths.get(file.getPath().toString()), StandardCopyOption.REPLACE_EXISTING);
                        else
                            Files.copy(Paths.get(TMPDIR+System.getProperty("file.separator") + baseName + currentCount + ".png"), Paths.get(file.getPath().toString()+".png"), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        graph = new Pane();
        graph.relocate(DPIkiller.kill(5),DPIkiller.kill(35));

        graph.prefWidthProperty().bind(graphicalModule.widthProperty().subtract(DPIkiller.kill(12)));
        graph.prefHeightProperty().bind(graphicalModule.heightProperty().subtract(DPIkiller.kill(66)));

        graph.setMinSize(DPIkiller.kill(776),DPIkiller.kill(582));

        clip = new Rectangle();
        clip.setLayoutX(DPIkiller.kill(1));
        clip.setLayoutY(DPIkiller.kill(1));
        clip.widthProperty().bind(graphicalModule.widthProperty().subtract(DPIkiller.kill(14)));
        clip.heightProperty().bind(graphicalModule.heightProperty().subtract(DPIkiller.kill(68)));
        graph.setClip(clip);

        borderGraph = new Pane();
        borderGraph.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        borderGraph.prefWidthProperty().bind(graphicalModule.widthProperty().subtract(DPIkiller.kill(12)));
        borderGraph.prefHeightProperty().bind(graphicalModule.heightProperty().subtract(DPIkiller.kill(66)));
        borderGraph.relocate(DPIkiller.kill(5),DPIkiller.kill(35));
        borderGraph.setMinSize(DPIkiller.kill(776),DPIkiller.kill(582));
        borderGraph.setVisible(false);


        plotView = new ImageView();
        plotView.relocate(0,0);

        graphicalModule.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resizeGraph();
            }
        });
        graphicalModule.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resizeGraph();
            }
        });


        horizontalScrollBar = new ScrollBar();
        verticalScrollBar = new ScrollBar();
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        horizontalScrollBar.prefWidthProperty().bind(graphicalModule.widthProperty().subtract(DPIkiller.kill(27)));
        verticalScrollBar.prefHeightProperty().bind(graphicalModule.heightProperty().subtract(DPIkiller.kill(81)));
        horizontalScrollBar.setVisible(false);
        verticalScrollBar.setVisible(false);

        verticalScrollBar.setVisibleAmount(DPIkiller.kill(582));
        horizontalScrollBar.setVisibleAmount(DPIkiller.kill(776));

        horizontalScrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (!graphMousePressed)
                    plotView.relocate(DPIkiller.kill(1)-(double)newValue, plotView.getLayoutY());
            }
        });

        verticalScrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (!graphMousePressed)
                    plotView.relocate(plotView.getLayoutX(), DPIkiller.kill(1)-(double)newValue);

            }
        });

        graph.getChildren().addAll(plotView,horizontalScrollBar,verticalScrollBar);

        Pane graphicalModuleContent = new Pane();
        graphicalModuleContent.getChildren().addAll(bgImage,borderGraph,graph,plus,zoomIndicator,minus,left,pageIndicator,right,export);
        graphicalModule.setContent(graphicalModuleContent);

        plotView.setOnScroll(event -> {
            if (plotView.getImage()!=null && zoom>=1) {
                if (event.getDeltaY() > 0) {
                    if (zoom < 4) {
                        if (zoom == 1) {
                            double xres = -event.getX();
                            double yres = -event.getY();

                            if (xres > 0)
                                xres = 0;
                            if (xres < -(graphicalModule.widthProperty().getValue() - DPIkiller.kill(14)))
                                xres = -(graphicalModule.widthProperty().getValue() - DPIkiller.kill(14));
                            if (yres > 0)
                                yres = 0;
                            if (yres < -(graphicalModule.heightProperty().getValue() - DPIkiller.kill(68)))
                                yres = -(graphicalModule.heightProperty().getValue() - DPIkiller.kill(68));

                            plotView.relocate(xres, yres);
                            horizontalScrollBar.setValue(-xres);
                            verticalScrollBar.setValue(-yres);
                        }
                        else
                        if (zoom == 2){
                            double xres = plotView.getLayoutX()-event.getX();
                            double yres = plotView.getLayoutY()-event.getY();

                            if (xres > 0)
                                xres = 0;
                            if (yres > 0)
                                yres = 0;

                            plotView.relocate(xres, yres);
                            horizontalScrollBar.setValue(-xres);
                            verticalScrollBar.setValue(-yres);
                        }
                        zoom *= 2;
                    }
                }
                else
                if (zoom > 1) {
                    if (zoom == 4) {
                        double xres = -(plotView.getLayoutX()+event.getX())% (graphicalModule.widthProperty().getValue()-DPIkiller.kill(14));
                        double yres = -(plotView.getLayoutY()+event.getY())% (graphicalModule.heightProperty().getValue()-DPIkiller.kill(68));


                        if (xres < -(graphicalModule.widthProperty().getValue() - DPIkiller.kill(14)))
                            xres = -(graphicalModule.widthProperty().getValue() - DPIkiller.kill(14));
                        if (xres > 0)
                            xres = 0;
                        if (yres < -(graphicalModule.heightProperty().getValue() - DPIkiller.kill(68)))
                            yres = -(graphicalModule.heightProperty().getValue() - DPIkiller.kill(68));
                        if (yres > 0)
                            yres = 0;


                        plotView.relocate(xres, yres);
                        horizontalScrollBar.setValue(-xres);
                        verticalScrollBar.setValue(-yres);
                    }
                    else
                        plotView.relocate(0, 0);
                    zoom /= 2;
                }


                switch (zoom) {
                    case 1:
                        plotView.relocate(DPIkiller.kill(1),DPIkiller.kill(1));

                        horizontalScrollBar.setVisible(false);
                        verticalScrollBar.setVisible(false);
                        plus.setDisable(false);
                        minus.setDisable(true);
                        break;
                    case 2:
                        horizontalScrollBar.setVisible(true);
                        verticalScrollBar.setVisible(true);
                        plus.setDisable(false);
                        minus.setDisable(false);
                        break;
                    case 4:
                        horizontalScrollBar.setVisible(true);
                        verticalScrollBar.setVisible(true);
                        plus.setDisable(true);
                        minus.setDisable(false);
                        break;
                }
            }

            if (plotView.getImage()!=null && zoom>1) {
                lastMouseEvent = event;
                mouseEntered = true;
            }
            else
                mouseEntered = false;
            updateMouseCursor();
            zoomIndicator.setText(zoom+"00%");
            resizeGraph();
        });

        final double[] mouseX = {0};
        final double[] mouseY = {0};
        plotView.setOnMousePressed(event -> {
            if (plotView.getImage()!=null && zoom>1) {
                mouseX[0] = event.getSceneX() ;
                mouseY[0] = event.getSceneY() ;
                graphMousePressed = true;

                lastMouseEvent = event;
                mousePressed = true;
                updateMouseCursor();
                //((Node) event.getSource()).setCursor(Cursor.CLOSED_HAND);
            }
        });

        plotView.setOnMouseReleased(event -> {
            if (graphMousePressed) {
                graphMousePressed = false;

                lastMouseEvent = event;
                mousePressed = false;
                updateMouseCursor();
                //((Node) event.getSource()).setCursor(Cursor.DEFAULT);
            }
        });

        plotView.setOnMouseEntered(event -> {
            if (plotView.getImage()!=null && zoom>1) {
                lastMouseEvent = event;
                mouseEntered = true;
                updateMouseCursor();
            }
            //((Node) event.getSource()).setCursor(Cursor.OPEN_HAND);
        });

        plotView.setOnMouseExited(event -> {
            if (plotView.getImage()!=null && zoom>1) {
                lastMouseEvent = event;
                mouseEntered = false;
                updateMouseCursor();
            }
            //((Node) event.getSource()).setCursor(Cursor.DEFAULT);
        });

        plotView.setOnMouseDragged(event -> {
            if (plotView.getImage()!=null && zoom>1) {
                double deltaX = event.getSceneX() - mouseX[0];
                double deltaY = event.getSceneY() - mouseY[0];
                if (plotView.getLayoutX() + deltaX>0)
                    deltaX = 0;
                if (plotView.getLayoutY() + deltaY>0)
                    deltaY = 0;
                if (plotView.getLayoutX() + deltaX < -(graphicalModule.widthProperty().getValue()-DPIkiller.kill(14))*(zoom-1))
                    deltaX = 0;
                if (plotView.getLayoutY() + deltaY < -(graphicalModule.heightProperty().getValue()-DPIkiller.kill(68))*(zoom-1))
                    deltaY = 0;

                plotView.relocate(plotView.getLayoutX() + deltaX, plotView.getLayoutY() + deltaY);

                horizontalScrollBar.setValue(-plotView.getLayoutX() - deltaX);
                verticalScrollBar.setValue(-plotView.getLayoutY() - deltaY);

                mouseX[0] = event.getSceneX() ;
                mouseY[0] = event.getSceneY() ;
            }
        });

    }

    public TitledPane getPane() {
        return graphicalModule;
    }

    private void updateMouseCursor() {
        if (lastMouseEvent != null) {
            if (mouseEntered)
                if (mousePressed)
                    ((Node) lastMouseEvent.getSource()).setCursor(Cursor.CLOSED_HAND);
                else
                    ((Node) lastMouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
            else
                ((Node) lastMouseEvent.getSource()).setCursor(Cursor.DEFAULT);

        }

    }

    public void resizeGraph() {
//        minus.relocate(graphicalModule.getWidth()-DPIkiller.kill(325),DPIkiller.kill(5));
//        zoomIndicator.relocate(graphicalModule.getWidth()-DPIkiller.kill(298),DPIkiller.kill(10));
//        plus.relocate(graphicalModule.getWidth()-DPIkiller.kill(267),DPIkiller.kill(5));
//        left.relocate(graphicalModule.getWidth()-DPIkiller.kill(232),DPIkiller.kill(5));
//        pageIndicator.relocate(graphicalModule.getWidth()-DPIkiller.kill(205),DPIkiller.kill(10));
//        right.relocate(graphicalModule.getWidth()-DPIkiller.kill(185),DPIkiller.kill(5));
        left.relocate(graphicalModule.getWidth()-DPIkiller.kill(340)-DPIkiller.kill(indicatorShift),DPIkiller.kill(5));
        pageIndicator.relocate(graphicalModule.getWidth()-DPIkiller.kill(307)-DPIkiller.kill(indicatorShift),DPIkiller.kill(10));
        right.relocate(graphicalModule.getWidth()-DPIkiller.kill(282),DPIkiller.kill(5));
        minus.relocate(graphicalModule.getWidth()-DPIkiller.kill(248),DPIkiller.kill(5));
        zoomIndicator.relocate(graphicalModule.getWidth()-DPIkiller.kill(218),DPIkiller.kill(10));
        plus.relocate(graphicalModule.getWidth()-DPIkiller.kill(185),DPIkiller.kill(5));
        export.relocate(graphicalModule.getWidth()-DPIkiller.kill(150),DPIkiller.kill(5));

        bgImage.setX(graphicalModule.getWidth()/2 - DPIkiller.kill(128));
        bgImage.setY(graphicalModule.getHeight()/2 - DPIkiller.kill(128));

        plotView.setFitWidth((graphicalModule.widthProperty().getValue()-DPIkiller.kill(14))*zoom);
        plotView.setFitHeight((graphicalModule.heightProperty().getValue()-DPIkiller.kill(68))*zoom);

        horizontalScrollBar.relocate(0,graphicalModule.heightProperty().getValue()-DPIkiller.kill(81));
        verticalScrollBar.relocate(graphicalModule.widthProperty().getValue()-DPIkiller.kill(27),0);

        horizontalScrollBar.setMax((graphicalModule.widthProperty().getValue()-DPIkiller.kill(14))*(zoom-1));
        verticalScrollBar.setMax((graphicalModule.heightProperty().getValue()-DPIkiller.kill(68))*(zoom-1));

        horizontalScrollBar.setVisibleAmount(horizontalScrollBar.getMax()/zoom);
        verticalScrollBar.setVisibleAmount(verticalScrollBar.getMax()/zoom);
    }

    public void setBaseFilename(String param_baseFilename, int param_count) {
        baseName = param_baseFilename;

        graphsCount = param_count;
        currentCount = 1;
        left.setDisable(true);
        right.setDisable(true);
        zoom = 1;
        zoomIndicator.setText("100%");
        horizontalScrollBar.setVisible(false);
        verticalScrollBar.setVisible(false);
        plus.setDisable(false);
        minus.setDisable(true);

        if (graphsCount>0)
            load();
        resizeGraph();
    }

    private void load() {
        Image tmp = new Image(new File(TMPDIR+System.getProperty("file.separator")+baseName+currentCount+".png").toURI().toString());
        if (tmp != null) {
            plotView.setImage(tmp);

            viewPort = new Rectangle2D(0,0,tmp.getWidth(),tmp.getHeight());
            plotView.setViewport(viewPort);

            plus.setDisable(false);

            if (graphsCount>1)
                right.setDisable(false);

            export.setDisable(false);
            borderGraph.setVisible(true);
        }
        else
            System.out.println("Cant open: "+baseName+currentCount+".png");
        pageIndicator.setText(currentCount+"/"+graphsCount);
        indicatorShift = 0;
        if (currentCount > 9)
            indicatorShift += 5;
        if (graphsCount > 9)
            indicatorShift += 5;
        resizeGraph();
    }

    public void clear() {
        plotView.setImage(null);
        plotView.relocate(DPIkiller.kill(1),DPIkiller.kill(1));

        borderGraph.setVisible(false);

        zoom = 1;

        zoomIndicator.setText("100%");
        pageIndicator.setText("0/0");
        indicatorShift = 0;

        minus.setDisable(true);
        plus.setDisable(true);
        left.setDisable(true);
        right.setDisable(true);
        export.setDisable(true);

        horizontalScrollBar.setVisible(false);
        verticalScrollBar.setVisible(false);
    }

}
