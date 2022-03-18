package com.fishrespbase.components.modalWindows;

import com.fishrespbase.components.modalWindows.windows.*;

/**
 * Created by R7Bishop on 08.11.2017.
 */
public class ModalWindowManager {
    private static RMRWindow rMR = new RMRWindow();
    private static RespirometryWindow respirometry = new RespirometryWindow();
    private static PreSens preSens = new PreSens();
    private static PyroScience pyroScience = new PyroScience();
    private static CleanWindowQC cleanWindowQC = new CleanWindowQC();

    public static void createModal(String name) {
        if (name.equals("rMR")) {
            rMR.showWindow();
        }
        else
        if (name.equals("respirometry")) {
            respirometry.showWindow();
        }
        else
        if (name.equals("PreSens")) {
            preSens.showWindow();
        }
        else
        if (name.equals("PyroScience")) {
            pyroScience.showWindow();
        }
        else
        if (name.equals("CleanWindowQC")) {
            cleanWindowQC.showWindow();
        }
        else {
           System.out.println("UNKNOWN MODAL WINDOW!");
        }
    }

    public static void closeModal(String name) {
        if (name.equals("rMR")) {
            rMR.close();
        }
        else
        if (name.equals("respirometry")) {
            respirometry.close();
        }
        else
        if (name.equals("PreSens")) {
            preSens.close();
        }
        else
        if (name.equals("PyroScience")) {
            pyroScience.close();
        }
        else
        if (name.equals("CleanWindowQC")) {
            cleanWindowQC.close();
        }
        else {
            System.out.println("UNKNOWN MODAL WINDOW!");
        }
    }
}
