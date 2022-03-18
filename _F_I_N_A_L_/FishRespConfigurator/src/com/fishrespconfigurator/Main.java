package com.fishrespconfigurator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;
import java.util.prefs.Preferences;

public class Main extends Application {
    private String OS, arch;

    private DirectoryChooser directoryChooser;

    private TextField r_home, r_lib;
    private boolean r_homeCorrect, r_libCorrect;

    private ColoredButton saveButton,installButton;
    private String installScriptWay;

    private Pane root;
    private boolean installing = false;

    @Override
    public void start(Stage primaryStage) throws Exception{

        if(!getOS()) {
            System.out.println("OS error");
            return;
        }

        if (OS != "windows") {
            r_homeCorrect = true;
        }

        root = new Pane();
        primaryStage.setTitle("Configure folders");
        primaryStage.getIcons().add(new Image("com/fishrespconfigurator/img/programIcon.png"));

        if (OS == "windows")
            primaryStage.setScene(new Scene(root, 320, 275));
        else
            primaryStage.setScene(new Scene(root, 320, 160));
        primaryStage.show();
        primaryStage.setResizable(false);

        Label label = new Label("Configure folders");
        label.relocate(95,5);
        label.setFont(Font.font("System", FontWeight.BOLD, 18));

        Pane configure = new Pane();
        configure.relocate(5,35);
       // configure.setBorder(new Border(new BorderStroke(Color.BLACK,
       //         BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        if (OS == "windows") {
            configure.setMaxSize(320, 225);
            configure.setMinSize(320, 225);
        }
        else {
            configure.setMaxSize(320, 110);
            configure.setMinSize(320, 110);
        }

        Label r_homeLabel, r_libLabel;

        r_homeLabel = null;
        if (OS == "windows") {
            r_homeLabel = new Label("Please, specify the path to the R_HOME:\n" +
                    "e.g. \"C:\\Program Files\\R\\R-3.3.3\"");
            r_homeLabel.relocate(5, 5);
        }

        r_libLabel = new Label("Please, specify the path to the R_LIBRARY: \n" +
                "e.g. \"C:\\Users\\USER_NAME\\Documents\\R\\win-library\\3.3\"");
        if (OS == "windows")
            r_libLabel.relocate(5,95);
        else
            r_libLabel.relocate(5,5);

        r_home = null;
        if (OS == "windows") {
            r_home = new TextField();
            r_home.relocate(5, 45);
            r_home.setMaxWidth(285);
            r_home.setMinWidth(285);
            r_home.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (checkR_HOME())
                            r_home.setText(newValue);
                        else
                            r_home.setText(newValue);
                    });
        }

        r_lib = new TextField();
        if (OS == "windows")
            r_lib.relocate(5,135);
        else
            r_lib.relocate(5,45);
        r_lib.setMaxWidth(285);
        r_lib.setMinWidth(285);
        r_lib.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (checkR_LIB())
                    r_lib.setText(newValue);
                else
                    r_lib.setText(newValue);
            });

        directoryChooser = new DirectoryChooser();

        Button r_homeOpenFileButton, r_libOpenFileButton;

        r_homeOpenFileButton = null;
        if (OS == "windows") {
            r_homeOpenFileButton = new Button();
            r_homeOpenFileButton.relocate(290, 45);
            r_homeOpenFileButton.setPrefSize(25, 25);
            r_homeOpenFileButton.setStyle("-fx-background-image: url('/com/fishrespconfigurator/img/OpenFileIcon.png')");
            r_homeOpenFileButton.setOnAction(
                    (ActionEvent event) -> {
                        openEvent(primaryStage, r_home);
                    });
        }

        r_libOpenFileButton = new Button();
        if (OS == "windows")
            r_libOpenFileButton.relocate(290, 135);
        else
            r_libOpenFileButton.relocate(290, 45);
        r_libOpenFileButton.setPrefSize(25, 25);
        r_libOpenFileButton.setStyle("-fx-background-image: url('/com/fishrespconfigurator/img/OpenFileIcon.png')");
        r_libOpenFileButton.setOnAction(
                (ActionEvent event) -> {
                   openEvent(primaryStage, r_lib);
                });

        saveButton = new ColoredButton(false, Constants.OBJECT_GRAY,"Save");
        if (OS == "windows")
            saveButton.relocate(35,215);
        else
            saveButton.relocate(35,80);
        saveButton.setMaxWidth(100);
        saveButton.setMinWidth(100);

        saveButton.setOnAction(event -> {
            try {
                String ppath = setPath();
                Process process = Runtime.getRuntime().exec("cmd.exe /c \"" +
                                                                      "SETX R_HOME \"" + r_home.getText() + "\" && " +
                                                                      "SETX R_LIB \"" + r_lib.getText() + "\" && " +
                                                                      "SETX PATH \"" + ppath + "\"\"");
                process.waitFor();

                /*        process = Runtime.getRuntime().exec("cmd.exe /c \"" +
                                                                      "SET R_HOME=" + r_home.getText() + " && " +
                                                                      "SET R_LIB=" + r_lib.getText() + " && " +
                                                                      "SET PATH=" + ppath + " && " +
                                                                      "java -jar FishRespBase.jar\"");
                System.out.println("cmd.exe /c \"" +
                        "SET R_HOME=" + r_home.getText() + " && " +
                        "SET R_LIB=" + r_lib.getText() + " && " +
                        "SET PATH=" + ppath + " && " +
                        "java -jar FishRespBase.jar\"");
                */

                //process.waitFor();
                System.exit(1);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }


            //saveButton.changeColor(true,Constants.OBJECT_GREEN);
        });


        installButton = new ColoredButton(false, Constants.OBJECT_GRAY, "Install");
        if (OS == "windows")
            installButton.relocate(180,215);
        else
            installButton.relocate(180,80);
        installButton.setMaxWidth(100);
        installButton.setMinWidth(100);

        installButton.setOnAction(event -> {
            Platform.runLater(() -> {
                installButton.setText("Wait");
                installButton.setDisable(true);
            });

            if (!installing) {
                installing = true;
                String separatedWay = r_lib.getText().replace('\\','/');

                /*
                chron
                lattice
                mclust
                DBI
                biglm
                rMR
                shape
                testthat
                gsw
                oce
                seacarb
                marelac
                measurements
                respirometry

                 */
                String installScript = ".libPaths(\""+separatedWay+"\")\n" +
                        "install.packages(\"rJava\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"chron\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"lattice\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"mclust\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"DBI\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"biglm\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"rMR\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"shape\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"testthat\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"gsw\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"oce\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"seacarb\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"marelac\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"measurements\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"respirometry\", repos = \"http://cran.us.r-project.org\")\n" +
                        "install.packages(\"FishResp\", repos = \"http://cran.us.r-project.org\")";

                try {
                    String dir = r_lib.getText();
                    if (dir.charAt(dir.length()-1)!='/')
                        dir += '/';
                    PrintWriter out = new PrintWriter(dir+"installScript.R");
                    out.println(installScript);
                    installScriptWay = dir+"installScript.R";
                    out.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ProcessBuilder pb = new ProcessBuilder("\"" + r_home.getText() + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "Rscript.exe\" \"" + installScriptWay + "\"");
                            String TMPDIR = System.getProperty("java.io.tmpdir").replace('\\','/');
                            pb.redirectOutput(new File(TMPDIR+"/out.txt"));
                            pb.redirectError(new File(TMPDIR+"/out.txt"));
                            //pb.redirectErrorStream(true);
                            Process process = pb.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null)
                                System.out.println(line);
                            process.waitFor();

                            Platform.runLater(() -> {
                                installButton.setText("Install");
                                installButton.setDisable(false);
                                checkR_LIB();
                                File tmp = new File(installScriptWay);
                                if (tmp.exists())
                                    tmp.delete();
                                tmp = new File(TMPDIR+"/out.txt");
                                if (tmp.exists())
                                    tmp.delete();
                            });
                            installing = false;
                            checkR_LIB();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread1.start();





            }
        });

        Label r_libWarning = new Label("Note that the installation process may take a few minutes.");
        r_libWarning.relocate(5,180);

        if (OS == "windows")
            configure.getChildren().addAll(r_homeLabel,r_home,r_homeOpenFileButton);
        configure.getChildren().addAll(r_libLabel,r_lib,r_libOpenFileButton, r_libWarning);
        configure.getChildren().addAll(saveButton, installButton);

        root.getChildren().addAll(label, configure);

        getEnvironmentVariables();
    }


    private String setPath() throws Exception{
        String path_RHOME = r_home.getText(),
                path_RLIB = r_lib.getText();


        ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        StringBuilder processOutput = new StringBuilder();

        try (BufferedReader processOutputReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));)
        {
            String readLine;

            while ((readLine = processOutputReader.readLine()) != null)
            {
                processOutput.append(readLine + System.lineSeparator());
            }

            process.waitFor();
        }

        boolean x64 = false;
        String processOutputString = processOutput.toString();
        if (processOutputString.toLowerCase().contains("64-bit"))
            x64 = true;

        System.out.print("FOUND: ");
        if (x64)
            System.out.print("x64");
        else
            System.out.print("i386");
        System.out.println(" SYSTEM");

        if (path_RHOME.substring(path_RHOME.length()-1) != System.getProperty("file.separator"))
            path_RHOME += System.getProperty("file.separator");
        path_RHOME += "bin"+System.getProperty("file.separator");
        if (x64)
            path_RHOME += "x64";
        else
            path_RHOME += "i386";

        if (path_RLIB.substring(path_RLIB.length()-1) != System.getProperty("file.separator"))
            path_RLIB += System.getProperty("file.separator");
        path_RLIB += "rJava"+System.getProperty("file.separator")+"jri"+System.getProperty("file.separator");
        if (x64)
            path_RLIB += "x64";
        else
            path_RLIB += "i386";

        String path = System.getenv("PATH");
        if (path != null) {

            int index = path.lastIndexOf(path_RLIB);
            if (index == -1)
                path = path_RLIB + ";" + path;


            index = path.lastIndexOf(path_RHOME);
            if (index == -1)
                path = path_RHOME+";"+path;

        }
        else
            path = path_RHOME + ";" + path_RLIB;

        System.out.println(path);

        return path;
    }

    private void installWin() {

            //System.out.println("\"" + r_home.getText() + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "Rscript.exe\" \""+installScriptWay+"\"");
            //execCommand("\"" + r_home.getText() + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "Rscript.exe\" \""+installScriptWay+"\"");

/*
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }*/
            //


           /* */

    }


    private boolean getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            OS = "windows";
            return true;
        }

        if (os.indexOf("mac") >= 0) {
            OS = "mac";
            return true;
        }
        if (os.indexOf("nix") >=0
                ||  os.indexOf("nux") >=0) {
            OS = "linux";
            return true;
        }

        arch = "x86";
        ProcessBuilder ps;
        if (OS == "windows")
            ps = new ProcessBuilder("java.exe","-version");
        else
            ps = new ProcessBuilder("java","-version");

        ps.redirectErrorStream(true);

        Process pr = null;
        try {
            pr = ps.start();


            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("Java HotSpot(TM)"))
                    if (line.contains("64"))
                        //System.out.println("64");
                        arch = "x64";
                //System.out.println(line);
            }
            pr.waitFor();
            //System.out.println("ok!");

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("arch: "+arch);
        //arch = System.getProperty("os.arch");



        return false;
    }

    private void openEvent(Stage primaryStage, TextField addressField) {
        directoryChooser.setTitle("Select Pre Data");
        File selectedFile = directoryChooser.showDialog(primaryStage);
        if (selectedFile != null) {
            addressField.setText(selectedFile.getPath());
        }
    }

    private void getEnvironmentVariables() {
        String envVar;
        switch (OS) {
            case "windows":
                envVar = System.getenv("R_HOME");
                if (envVar != null)
                    r_home.setText(envVar);

                envVar = System.getenv("R_LIB");
                if (envVar != null)
                    r_lib.setText(envVar);
                break;
            case "linux":
                envVar = Preferences.userRoot().get("R_LIB",null);
                if (envVar != null)
                    r_lib.setText(envVar);
                break;
            case "mac":
                envVar = new Properties().getProperty("R_LIB",null);
                if (envVar != null)
                    r_lib.setText(envVar);
                break;
        }
    }


    private boolean checkR_HOME() {
        if (OS == "linux" || OS == "mac") {
            askButton();
            r_homeCorrect = true;
            return true;
        }
        if (r_home.getText().length()>0) {
            if (new File(r_home.getText()+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"R.exe").exists()
            ||  new File(r_home.getText()+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"R").exists()) {
                r_homeCorrect = true;
                askButton();

                r_home.setStyle("-fx-base: #85FF85;");
                return true;
            }
            else {
                r_homeCorrect = false;
                r_home.setStyle("-fx-base: #FF8080;");
                askButton();
                checkR_LIB();
            }
        }

        return false;
    }

    private boolean checkR_LIB() {
        if (r_lib.getText().length()>0) {
            boolean checker = true;
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"rJava"+System.getProperty("file.separator")+"jri"+System.getProperty("file.separator")+"JRI.jar").exists()) {
                System.out.println("Can't find rJava");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"chron"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"chron").exists()) {
                System.out.println("Can't find chron");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"lattice"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"lattice").exists()) {
                System.out.println("Can't find lattice");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"mclust"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"mclust").exists()) {
                System.out.println("Can't find mclust");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"DBI"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"DBI").exists()) {
                System.out.println("Can't find DBI");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"biglm"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"biglm").exists()) {
                System.out.println("Can't find biglm");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"rMR"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"rMR").exists()) {
                System.out.println("Can't find rMR");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"shape"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"shape").exists()) {
                System.out.println("Can't find shape");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"testthat"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"testthat").exists()) {
                System.out.println("Can't find testthat");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"gsw"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"gsw").exists()) {
                System.out.println("Can't find gsw");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"oce"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"oce").exists()) {
                System.out.println("Can't find oce");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"seacarb"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"seacarb").exists()) {
                System.out.println("Can't find seacarb");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"marelac"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"marelac").exists()) {
                System.out.println("Can't find marelac");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"measurements"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"measurements").exists()) {
                System.out.println("Can't find measurements");
                checker = false;
            }
            if (!new File(r_lib.getText()+System.getProperty("file.separator")+"respirometry"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"respirometry").exists()) {
                System.out.println("Can't find respirometry");
                checker = false;
            }
            if (checker)
            {
                r_libCorrect = true;
                r_lib.setStyle("-fx-base: #85FF85;");
                installButton.changeColor(false,Constants.OBJECT_GREEN);
                askButton();
                return true;
            }
            else {
                r_libCorrect = false;
                if (new File(r_lib.getText()).isDirectory()
                &&  checkR_HOME()) {
                    r_lib.setStyle("-fx-base: #FFFF80;");
                    installButton.changeColor(true,Constants.OBJECT_YELLOW);
                }
                else {
                    r_lib.setStyle("-fx-base: #FF8080;");
                    installButton.changeColor(false,Constants.OBJECT_RED);
                }
                askButton();
            }
        }
        return false;
    }

    private void askButton() {
        if (r_homeCorrect && r_libCorrect)
            saveButton.changeColor(true, Constants.OBJECT_YELLOW);
        else
            saveButton.changeColor(false, Constants.OBJECT_GRAY);
    }

    private void execCommand(String command) throws Exception{
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
