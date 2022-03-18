package com.fishrespbase.components;

/**
 * Created by R7Bishop on 29.07.2017.
 */
public final class Saver {
    private static String defaultChamberNumber = " ";
    private static String defaultLoggerSoftware = " ";
    private static String lastInputDirectory = "";
    private static boolean trait2Enabled = false;
    private static String dateFormat = "";

    public static String getDefaultChamberNumber() {
        return defaultChamberNumber;
    }

    public static void setDefaultChamberNumber(String defaultChamberNumber) {
        Saver.defaultChamberNumber = defaultChamberNumber;
    }

    public static String getDefaultLoggerSoftware() {
        return defaultLoggerSoftware;
    }

    public static void setDefaultLoggerSoftware(String defaultLoggerSoftware) {
        Saver.defaultLoggerSoftware = defaultLoggerSoftware;
    }

    public static String getLastInputDirectory() {
        return lastInputDirectory;
    }

    public static void setLastInputDirectory(String lastInputDirectory) {
        Saver.lastInputDirectory = lastInputDirectory;
    }

    public static boolean isTrait2Enabled() {
        return trait2Enabled;
    }

    public static void setTrait2Enabled(boolean trait2Enabled) {
        Saver.trait2Enabled = trait2Enabled;
    }

    public static void setDateFormat(String format) {
        Saver.dateFormat = format;
    }

    public static String getDateFormat() {
        return  dateFormat;
    }

}
