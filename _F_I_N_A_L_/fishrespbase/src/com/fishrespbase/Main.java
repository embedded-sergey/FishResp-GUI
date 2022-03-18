package com.fishrespbase;


import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.components.RLogger;
import com.fishrespbase.components.Saver;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.tabs.BGR;
import com.fishrespbase.tabs.General;
import com.fishrespbase.tabs.Results;
import com.fishrespbase.tabs.Traits;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.rosuda.JRI.Rengine;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main extends Application {
    public static Rengine re;
    public static String OS;
    public static Stage primaryStage;
    public static Scene baseScene;
    public static TabPane tabPane;
    public static String TMPDIR;
    public static String[] args;
    public static String programDIR;
    public static String programVersion = "1.0";

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);

        Main.primaryStage = primaryStage;

        //Font.loadFont(getClass().getResourceAsStream("font/segoeuib.ttf"), 12);
        //Font.loadFont(getClass().getResourceAsStream("font/segoeui.ttf"), 12);

        initR();
        primaryStage.setTitle("FishResp "+programVersion);
        primaryStage.getIcons().add(new Image("com/fishrespbase/img/programIcon.png"));
        Group root = new Group();
        Scene scene = new Scene(root, DPIkiller.kill(1033), DPIkiller.kill(695), Color.LIGHTGREY);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("com/fishrespbase/css/tab.css").toExternalForm());
        scene.getStylesheets().add(getClass().getClassLoader().getResource("com/fishrespbase/css/titledPane.css").toExternalForm());

        Main.baseScene = scene;

        tabPane = new TabPane();
        General.tab = new General();
        BGR.tab = new BGR();

        Traits.trait1 = new Traits("Trait 1", 1);
        Traits.trait2 = new Traits("Trait 2", 2);
        Results.tab = new Results();

        Main.tabPane.getTabs().addAll(General.tab, BGR.tab, Traits.trait1, Traits.trait2, Results.tab);

        Main.tabPane.prefWidthProperty().bind(scene.widthProperty());
        Main.tabPane.prefHeightProperty().bind(scene.heightProperty());

        Pane logoBorder = new Pane();

        ImageView fishRespLogo = new ImageView();
        fishRespLogo.relocate(DPIkiller.kill(1),DPIkiller.kill(1));
        fishRespLogo.setFitWidth(DPIkiller.kill(122));
        fishRespLogo.setFitHeight(DPIkiller.kill(30));
        fishRespLogo.setImage(new Image("com/fishrespbase/img/logo.png"));
        fishRespLogo.setOnMouseClicked(event -> {
            try {
                openWebPage(new URI("http://fishresp.org"));
            }
            catch (URISyntaxException openWebPageExeption) {
                System.out.println("Web link opening exception: "+openWebPageExeption);
            }
        });

        fishRespLogo.setOnMouseEntered(event -> {
            ((Node) event.getSource()).setCursor(Cursor.HAND);
        });

        fishRespLogo.setOnMouseExited(event -> {
            ((Node) event.getSource()).setCursor(Cursor.DEFAULT);
        });

        logoBorder.relocate(scene.getWidth() + DPIkiller.kill(-122 - 8 - 5 - 43),DPIkiller.kill(5));
        logoBorder.setPrefSize(DPIkiller.kill(122 + 2),DPIkiller.kill(30 + 2));
        logoBorder.getChildren().add(fishRespLogo);

        Pane logBorder = new Pane();
        ImageView saveLog = new ImageView();
        //saveLog.relocate(scene.getWidth() - 2 - 5 - 33,5);
        saveLog.setImage(new Image("com/fishrespbase/img/logIcon.png"));
        saveLog.setFitWidth(DPIkiller.kill(31));
        saveLog.setFitHeight(DPIkiller.kill(28));
        logBorder.setOnMouseClicked(event -> {
            FileChooser fileChooser1 = new FileChooser();
            fileChooser1.setTitle("Export the results");
            if (Saver.getLastInputDirectory() != "")
                fileChooser1.setInitialDirectory(new File(Saver.getLastInputDirectory()));
            fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("Log text file","*.txt"));
            File file = fileChooser1.showSaveDialog(Main.primaryStage);
            if (file != null) {
                Saver.setLastInputDirectory(file.getParent().toString());
                RLogger.writeToFile(file.toString());
            }
        });

        logBorder.relocate(scene.getWidth() + DPIkiller.kill(-2 - 5 - 33),DPIkiller.kill(7));
        logBorder.setPrefSize(DPIkiller.kill(33),DPIkiller.kill(30));
        logBorder.getChildren().add(saveLog);

        logBorder.setOnMouseEntered(event -> {
            ((Node) event.getSource()).setCursor(Cursor.HAND);
        });

        logBorder.setOnMouseExited(event -> {
            ((Node) event.getSource()).setCursor(Cursor.DEFAULT);
        });


        root.getChildren().addAll(Main.tabPane,logoBorder,logBorder, AlertAccordion.getInstance());

        primaryStage.setScene(scene);

        primaryStage.show();
        double pSw = primaryStage.widthProperty().getValue();
        double pSh = primaryStage.heightProperty().getValue();
        primaryStage.setMinWidth(pSw);
        primaryStage.setMinHeight(pSh);
        /*primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            if ((double)newValue < DPIkiller.kill(1033))
                primaryStage.setWidth(pSw);
            });
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            if ((double)newValue < DPIkiller.kill(1033))
                primaryStage.setHeight(pSh);
            });
*/
        AlertAccordion.getInstance().rebase(DPIkiller.kill(1033),DPIkiller.kill(695));

        scene.widthProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    logBorder.relocate(scene.getWidth() + DPIkiller.kill(-2 - 5 - 33),DPIkiller.kill(7));
                    logoBorder.relocate(scene.getWidth() + DPIkiller.kill(-122 - 8 - 5 - 43),DPIkiller.kill(5));
                    General.tab.getExtensionPane().setY(DPIkiller.kill(scene.getHeight() - 112));

                    AlertAccordion.getInstance().rebase(DPIkiller.kill(scene.getWidth()),DPIkiller.kill(scene.getHeight()));
                });
        scene.heightProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    logBorder.relocate(scene.getWidth() + DPIkiller.kill(-2 - 5 - 33),DPIkiller.kill(7));
                    logoBorder.relocate(scene.getWidth() + DPIkiller.kill(-122 - 8 - 5 - 43),DPIkiller.kill(5));
                    General.tab.getExtensionPane().setY(scene.getHeight() - DPIkiller.kill(112));

                    AlertAccordion.getInstance().rebase(scene.getWidth(),scene.getHeight());
                });

        primaryStage.setOnCloseRequest(
                (javafx.stage.WindowEvent event) -> {
                    cleanAndClose();
                });
        System.out.println("Current rem: " + DPIkiller.getRem());

        //ModalWindowManager.createModal("CleanWindowQC");
    }

    public static void openWebPage(URI url) {
        if (OS == "windows") {
            /*Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    //desktop.browse(url);
                    desktop.browse(URI.create("http://google.com/"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (OS == "linux") {
            Runtime rt = Runtime.getRuntime();
            String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx" };

            StringBuffer cmd = new StringBuffer();
            for (int i = 0; i < browsers.length; i++)
                if(i == 0)
                    cmd.append(String.format(    "%s \"%s\"", browsers[i], url));
                else
                    cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
            // If the first didn't work, try the next browser and so on

            try {
                rt.exec(new String[] { "sh", "-c", cmd.toString() });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (OS == "mac") {
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadRFunctions(String address) {
        try{
            String line = "";
            if (OS == "windows")
                for (String part : Files.readAllLines(Paths.get(address))) {
                    line += part+"\n";
                }
            else
            if (OS == "linux")
                for (String part : Files.readAllLines(Paths.get("/usr/lib/FishResp/"+address))) {
                    line += part+"\n";
                }
            else
                for (String part : Files.readAllLines(Paths.get(System.getenv("PDIR") + File.separator + address))) {
                    line += part + "\n";
                }
            Main.re.eval(line);
        }
        catch (Exception e)
        {
            System.out.println("Exception: "+e+" for "+address);
        }
    }

    private static void cleanAndClose() {
        if (Main.re != null)
            Main.re.end();

        cleanPlots();
    }

    public static void cleanPlots() {
        cleanPlot("test.oxygen_1.png");
        cleanPlot("test.oxygen_2.png");
        cleanPlot("test.temperature_1.png");
        cleanPlot("test.temperature_2.png");
        cleanPlot("meas.oxygen_1.png");
        cleanPlot("meas.oxygen_2.png");
        cleanPlot("meas.temperature_1.png");
        cleanPlot("meas.temperature_2.png");
        cleanPlot("QC.slope_1.png");
        cleanPlot("QC.slope_2.png");
        cleanPlot("QC.slope_3.png");
        cleanPlot("QC.slope_4.png");
        cleanPlot("QC.slope_5.png");
        cleanPlot("QC.slope_6.png");
        cleanPlot("QC.slope_7.png");
        cleanPlot("QC.slope_8.png");
        cleanPlot("QC.temperature_1.png");
        cleanPlot("QC.total.O2.chambers_1.png");
        cleanPlot("QC.total.O2.phases_1.png");
        cleanPlot("QC.corrected.O2.chambers_1.png");
        cleanPlot("QC.corrected.O2.phases_1.png");
        cleanPlot("extracted_slopes_1.png");
        cleanPlot("results_1.png");
        cleanPlot("results_2.png");
        cleanPlot("results_3.png");
        cleanPlot("results_4.png");
        cleanPlot("results_5.png");
        cleanPlot("results_6.png");
        cleanPlot("results_7.png");
        cleanPlot("results_8.png");
        cleanPlot("results_9.png");
    }

    private static void cleanPlot(String filename) {
        //if (OS == "windows")
        filename = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + filename;

        File tmpPlot = new File(filename);
        if (tmpPlot.exists())
            tmpPlot.delete();
    }

    private static void initR() {
        Main.re = new Rengine(new String[] {"--vanilla"}, false, new TextConsole());

        String lib;

        lib = System.getenv("R_LIB");
        String quest;
        if (OS == "windows") {
            quest = "library(chron, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"chron\" library for work");

                cleanAndClose();
                //return;
            }

            quest = "library(lattice, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"lattice\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(mclust, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"mclust\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(DBI, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"DBI\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(biglm, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"biglm\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(rMR, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"rMR\" library for work");

                cleanAndClose();
                //return;
            }

            quest = "library(shape, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"shape\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(testthat, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"testthat\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(gsw, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"gsw\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(oce, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"oce\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(seacarb, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"seacarb\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(marelac, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"marelac\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(measurements, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"measurements\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(respirometry, lib.loc = '" + lib.replace('\\', '/') + "')";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"respirometry\" library for work");

                cleanAndClose();
                //return;
            }
        }

        if (OS != "windows") {
            quest = "library(chron)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"chron\" library for work");

                cleanAndClose();
                //return;
            }

            quest = "library(lattice)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"lattice\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(mclust)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"mclust\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(DBI)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"DBI\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(biglm)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"biglm\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(rMR)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"rMR\" library for work");

                cleanAndClose();
                //return;
            }

            quest = "library(shape)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"shape\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(testthat)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"testthat\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(gsw)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"gsw\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(oce)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"oce\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(seacarb)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"seacarb\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(marelac)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"marelac\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(measurements)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"measurements\" library for work");

                cleanAndClose();
                //return;
            }
            quest = "library(respirometry)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Need \"respirometry\" library for work");

                cleanAndClose();
                //return;
            }
        }
        /*if (OS == "mac") {
            quest = "library(chron)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Can't load chron");
            }
            quest = "library(lattice)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Can't load lattice");
            }
            quest = "library(mclust)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Can't load lattice");
            }
            quest = "library(respirometry)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Can't load lattice");
            }
            quest = "library(rMR)";
            RLogger.log(quest);
            if (Main.re.eval(quest) == null) {
                System.out.println("Can't load lattice");
            }
        }*/
        TMPDIR = System.getProperty("java.io.tmpdir").replace('\\','/') + '/';
        System.out.println("TMP DIR: " + TMPDIR);
        Main.re.eval("TMPADRESS <- \"" + TMPDIR +"\"");

        loadRFunctions("R/import.test.R");
        loadRFunctions("R/import.meas.R");
        loadRFunctions("R/input.info.R");
        loadRFunctions("R/QC.oxygen.R");
        loadRFunctions("R/QC.oxygen.meas.R");
        loadRFunctions("R/QC.temperature.R");
        loadRFunctions("R/QC.temperature.meas.R");
        loadRFunctions("R/QC.meas.R");
        loadRFunctions("R/QC.slope.R");
        loadRFunctions("R/QC.activity.R");
        loadRFunctions("R/correct.meas.R");
        loadRFunctions("R/extract.slope.R");
        loadRFunctions("R/calculate.MR.R");
        loadRFunctions("R/calculate.both.R");
        loadRFunctions("R/calculate.both.MS.R");
        loadRFunctions("R/convert.respirometry.R");
        loadRFunctions("R/convert.rMR.R");
        loadRFunctions("R/presens.aquaresp.R");
        loadRFunctions("R/pyroscience.aquaresp.R");
        loadRFunctions("R/rm.data.R");

        RLogger.separator();
    }

    private static boolean getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            Main.OS = "windows";
            return true;
        }
        if (os.indexOf("mac") >= 0) {
            Main.OS = "mac";
            return true;
        }
        if (os.indexOf("nix") >=0
        ||  os.indexOf("nux") >=0) {
            Main.OS = "linux";
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        if (!getOS()) {
            System.out.println("OS error");
            return;
        }
        System.out.println("CURRENT OS: " + OS);
        if (OS == "mac") {
            /*
            PrintStream st = null;
            try {
                st = new PrintStream(new FileOutputStream("/Volumes/Transcend/out.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (st != null) {
                System.setErr(st);
                System.setOut(st);
                System.out.println("File output stream enabled.");
            }
            else
                System.out.println("can't create stream");*/

            if (System.getenv("PDIR") == null) {
                System.out.println("Here is no filepath for macOS");
                System.exit(1);
            }
        }

        launch(args);
    }
}
