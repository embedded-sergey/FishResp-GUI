package com.fishrespbase.components;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by R7Bishop on 01.12.2017.
 */
public class RLogger {
    private static String commandLog = "";
    private static SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static void log(String command) {
        Date d = new Date();
        commandLog += format1.format(d) + '\t' + command + '\n';
    }

    public static void separator() {
        commandLog += "################\n\n";
    }

    public static void writeToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.write(commandLog);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flush() {
        Date d = new Date();
        commandLog += format1.format(d) + "\tReset\n";
        separator();
    }
}
