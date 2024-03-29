package com.fishrespbase;

import com.fishrespbase.components.Constants;
import com.fishrespbase.components.RLogger;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by R7Bishop on 23.01.2017.
 */
class TextConsole implements RMainLoopCallbacks
{
    public void rWriteConsole(Rengine re, String text, int oType) {
        System.out.print(text);
        if (text.contains("Error in convert.times(times., fmt) : format h:m:s may be incorrect"))
            AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING2LINES, Constants.DEFAULT_ALERTLIFETIME,"The selected date format and the date format of raw data are mismatched"));
    }

    public void rBusy(Rengine re, int which) {
        System.out.println("rBusy("+which+")");
    }

    public String rReadConsole(Rengine re, String prompt, int addToHistory) {
        System.out.print(prompt);
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String s=br.readLine();
            return (s==null||s.length()==0)?s:s+"\n";
        } catch (Exception e) {
            System.out.println("jriReadConsole exception: "+e.getMessage());
        }
        return null;
    }

    public void rShowMessage(Rengine re, String message) {
        System.out.println("rShowMessage \""+message+"\"");
    }

    public String rChooseFile(Rengine re, int newFile) {
        FileDialog fd = new FileDialog(new Frame(), (newFile==0)?"Select a file":"Select a new file", (newFile==0)?FileDialog.LOAD:FileDialog.SAVE);
        fd.show();
        String res=null;
        if (fd.getDirectory()!=null) res=fd.getDirectory();
        if (fd.getFile()!=null) res=(res==null)?fd.getFile():(res+fd.getFile());
        return res;
    }

    public void   rFlushConsole (Rengine re) {
    }

    public void   rLoadHistory  (Rengine re, String filename) {
    }

    public void   rSaveHistory  (Rengine re, String filename) {
    }
}
