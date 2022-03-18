package com.fishrespstarter;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * Created by R7Bishop on 21.03.2017.
 */
public class Main {
    private String OS;
    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {

        if (!getOS()) {
            System.out.println("OS error");
            return;
        }
        System.out.println("Hello "+OS);
        /*!!!!!!if (OS == "mac") {
            String adress = null;
            adress = System.getProperty("java.library.path");

            //System.out.println("Current directory: "+adress);
            if (adress==null) {
                System.out.println("Can't detect current directory");
                return;
            }/*
            FileWriter out = null;
            try {
                out = new FileWriter("/Users/admin/Desktop/BUILD/tmt.txt");
                out.write(adress);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*//*!!!!!!!!!!

            if (!checkSystemParameters(OS)) {
                System.out.println("WRONG PARAMETERS");
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec("open " + adress + "/../../../FishResp\\ configurator.app");
                    process.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //openJar("", adress + "/FishRespConfigurator.jar");
            }
            else {
             //   System.out.println("TRUE PARAM");
                System.out.println("Runs Base");
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec("open " + adress + "/FishRespBase.jar");
                    process.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //openJar("", adress+"/base.jar");
            }
        }!!!!!!!*/
        /*else*/
        //System.out.println(checkSystemParameters(OS));
            if (!checkSystemParameters(OS)) {
                System.out.println("Starting configurator");
                openJar("", "FishRespConfigurator.jar");
            }
            else {
                System.out.println("Starting base");
                openJar("", "FishRespBase.jar");
            }
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

        return false;
    }

    private static boolean checkSystemParameters(String OS) {
        String home = "", lib, env;
        if (OS == "windows") {
            home = System.getenv("R_HOME");
            System.out.print("Checking R_HOME path... ");
            if (home == null) {
                System.out.println("FALL");
                return false;
            }
            System.out.println("PASS");
            System.out.print("Checking R_HOME path R executables... ");
            if (!(new File(home+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"R.exe").exists())
            &&  !(new File(home+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"R").exists())) {
                System.out.println("FALL");
                return false;
            }
            System.out.println("PASS");
        }
        System.out.print("Checking R_LIB path... ");
        lib = System.getenv("R_LIB");
        if (OS != "windows") {
            if (lib == null) {
                lib = Preferences.userRoot().get("R_LIB", null);
          //      System.out.println(lib);
            }
        }
        //System.out.println(Preferences.userRoot().get("R_LIB", null));
        if (lib == null) {
            System.out.println("FALL");
            return false;
        }
        System.out.println("PASS");

        System.out.print("Checking libs... ");
        if (!(new File(lib+System.getProperty("file.separator")+"rJava"+System.getProperty("file.separator")+"jri"+System.getProperty("file.separator")+"JRI.jar").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"chron"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"chron").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"lattice"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"lattice").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"mclust"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"mclust").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"DBI"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"DBI").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"biglm"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"biglm").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"rMR"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"rMR").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"shape"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"shape").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"testthat"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"testthat").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"gsw"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"gsw").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"oce"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"oce").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"seacarb"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"seacarb").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"marelac"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"marelac").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"measurements"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"measurements").exists())
        ||  !(new File(lib+System.getProperty("file.separator")+"respirometry"+System.getProperty("file.separator")+"R"+System.getProperty("file.separator")+"respirometry").exists())) {
            System.out.println("FALL");
            return false;
        }
        System.out.println("PASS");
        if (OS == "windows") {
            System.out.print("Checking PATH var... ");
            env = System.getenv("PATH");
            if (env == null) {
                System.out.println("FALL");
                return false;
            }
            System.out.println("PASS");

            System.out.print("Checking PATH consists R_HOME & JRI vars... ");
            if (!env.contains(home) || !    env.contains(lib+System.getProperty("file.separator")+"rJava"+System.getProperty("file.separator")+"jri")) {
                System.out.println("FALL");
                return false;
            }
            System.out.println("PASS");
        }
        return true;
    }

    private  int openJar(String subparams, String filename) {
        try {
            Process process;

            process = Runtime.getRuntime().exec("javaw " + subparams + " -jar " + filename + "\"");

            process.waitFor();
            return process.exitValue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
