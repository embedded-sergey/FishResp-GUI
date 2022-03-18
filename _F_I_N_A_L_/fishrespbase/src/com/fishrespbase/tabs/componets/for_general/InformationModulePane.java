package com.fishrespbase.tabs.componets.for_general;

import com.fishrespbase.Main;
import com.fishrespbase.components.BaseTitledPane;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.components.modalWindows.ModalWindowManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;

import java.io.File;
import java.net.URI;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class InformationModulePane extends BaseTitledPane {

    //private WebEngine webEngine;
    public static WebView browser;

    public InformationModulePane(double x, double y, double w, double h) {
        super("Information module", x, y, w, h);
        this.setMinHeight(h);
        this.prefWidthProperty().bind(Main.baseScene.widthProperty().subtract(DPIkiller.kill(435)));
        this.prefHeightProperty().bind(Main.baseScene.heightProperty().subtract(DPIkiller.kill(123)));
    }

    @Override
    protected void createContent() {
        browser = new WebView();
        browser.relocate(0,0);
        browser.prefWidthProperty().bind(Main.baseScene.widthProperty().subtract(DPIkiller.kill(437)));
        browser.prefHeightProperty().bind(Main.baseScene.heightProperty().subtract(DPIkiller.kill(149)));

        //System.out.println(System.getProperty("user.dir"));
        WebEngine webEngine = browser.getEngine();
        webEngine.setUserStyleSheetLocation(getClass().getClassLoader().getResource("com/fishrespbase/css/webEngine.css").toString());
        try {
            if (Main.OS == "mac")
                webEngine.load(new File(System.getenv("PDIR") + File.separator + "tutorial.html").toURI().toURL().toString());
            else
                webEngine.load(new File(System.getProperty("user.dir") + File.separator + "tutorial.html").toURI().toURL().toString());
        }
        catch (Exception e) {
            System.out.println("Tutorial opening exception: "+e);
        }

        /*WebViewHyperlinkListener eventPrintingListener = event -> {
            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                try {
                    Main.openWebPage(event.getURL().toURI());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            //webEngine.getLoadWorker().cancel();
            //System.out.println(WebViews.hyperlinkEventToString(event));
            return false;
        };
        WebViews.addHyperlinkListener(browser, eventPrintingListener);
        */
/*
        JSObject jsobj = (JSObject) webEngine.executeScript("window");
        jsobj.setMember("java", new MyBridge());
*/


        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> o, Worker.State old, final Worker.State state) {


                Document doc = webEngine.getDocument();
                if (doc != null) {
                    NodeList nodeList = doc.getElementsByTagName("a");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        EventTarget tmp = ((EventTarget) nodeList.item(i));
                        if (nodeList.item(i).toString().contains("modalWindow")) {
                            int index = i;
                            //System.out.println(nodeList.item(i).toString().substring(nodeList.item(i).toString().indexOf("modalWindow")+11));
                            ((EventTarget) nodeList.item(i)).addEventListener("click", evt -> ModalWindowManager.createModal(nodeList.item(index).toString().substring(nodeList.item(index).toString().indexOf("modalWindow")+12)), false);
                        }
                        else
                            ((EventTarget) nodeList.item(i)).addEventListener("click", evt -> /*System.out.println("TEST "+tmp.toString())*/ Main.openWebPage(URI.create(tmp.toString())), false);
                    }

                }
            }
        });

        /*

        Document doc = webEngine.getDocument();
        System.out.println(doc);
        NodeList nodeList = doc.getElementsByTagName("a");
        for (int i = 0; i < nodeList.getLength(); i++) {
            ((EventTarget) nodeList.item(i)).addEventListener("click", evt -> {
                System.out.println("TEST");
            }, false);
        }*/
        contentPane.getChildren().addAll(browser);
    }

    public void reset() {

    }
}
