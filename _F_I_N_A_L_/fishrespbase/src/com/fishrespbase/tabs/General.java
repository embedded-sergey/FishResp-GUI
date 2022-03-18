package com.fishrespbase.tabs;


import com.fishrespbase.Main;
import com.fishrespbase.components.BaseTab;
import com.fishrespbase.components.Constants;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.tabs.componets.for_general.ExtensionPane;
import com.fishrespbase.tabs.componets.for_general.GlobalSettingsPane;
import com.fishrespbase.tabs.componets.for_general.IndividualInfoPane;
import com.fishrespbase.tabs.componets.for_general.InformationModulePane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * Created by R7Bishop on 19.02.2017.
 */
public class General extends BaseTab { //General Tab
    public static General tab = null;

    /*GlobalSettingsPane*/
    private GlobalSettingsPane globalSettingsPane;

    private int selectBGRTests;
    private boolean globalData;
    /*GlobalSettingsPane*/

    /*IndividualInfoPane*/
    private IndividualInfoPane individualInfoPane;

    private boolean tableReady;
    /*IndividualInfoPane*/

    /*InformationModulePane*/
    private InformationModulePane informationModulePane;
    /*InformationModulePane*/

    /*ExtensionPane*/
    private ExtensionPane extensionPane;
    /*ExtensionPane*/


    public General(){
        super("General", Constants.OBJECT_YELLOW);

        selectBGRTests = 0;
        tableReady = false;
        globalData = false;

       /* if (Main.OS == "linux")
            globalSettingsPane = new GlobalSettingsPane(5,5,420,227);
        else
            globalSettingsPane = new GlobalSettingsPane(5,5,420,240);
        if (Main.OS == "linux")
            individualInfoPane = new IndividualInfoPane(5,237,420,388);
        else
            individualInfoPane = new IndividualInfoPane(5,250,420,403);*/

        globalSettingsPane = new GlobalSettingsPane(DPIkiller.kill(5),DPIkiller.kill(5),DPIkiller.kill(420),DPIkiller.kill(240));
        individualInfoPane = new IndividualInfoPane(DPIkiller.kill(5),DPIkiller.kill(250),DPIkiller.kill(420),DPIkiller.kill(403));
        informationModulePane = new InformationModulePane(DPIkiller.kill(430),DPIkiller.kill(5),DPIkiller.kill(-1),DPIkiller.kill(-1));
        extensionPane = new ExtensionPane(DPIkiller.kill(430),DPIkiller.kill(583),DPIkiller.kill(-1),DPIkiller.kill(70));

        mainPane.getChildren().addAll(this.globalSettingsPane, this.individualInfoPane, this.informationModulePane, this.extensionPane);
    }

    @Override
    public void resetTab(){
        individualInfoPane.clear();
        this.changeTabColor(Constants.OBJECT_YELLOW);
        tableReady = false;
    }

    public void changedParam(){
        globalData = false;

        globalSettingsPane.reset();
        individualInfoPane.reset();

        changeTabColor(Constants.OBJECT_YELLOW);

        BGR.tab.resetTab();

        Traits.trait1.resetTab();
        Traits.trait2.resetTab();
        Results.tab.resetTab();
    }

    public boolean isTableReady() {
        return tableReady;
    }

    public void setTableReady(boolean tableReady) {
        this.tableReady = tableReady;

        BGR.tab.resetTab();

        Traits.trait1.resetTab();
        Traits.trait2.resetTab();
        Results.tab.resetTab();
    }

    public GlobalSettingsPane getGlobalSettingsPane() {
        return globalSettingsPane;
    }

    public void setGlobalSettingsPane(GlobalSettingsPane globalSettingsPane) {
        this.globalSettingsPane = globalSettingsPane;
    }

    public boolean isGlobalData() {
        return globalData;
    }

    public void setGlobalData(boolean globalData) {
        this.globalData = globalData;
    }

    public int getSelectBGRTests() {
        return selectBGRTests;
    }

    public void setSelectBGRTests(int selectBGRTests) {
        this.selectBGRTests = selectBGRTests;
    }

    public General getTab() {
        return tab;
    }

    public void setTab(General tab) {
        this.tab = tab;
    }

    public IndividualInfoPane getIndividualInfoPane() {
        return individualInfoPane;
    }

    public InformationModulePane getInformationModulePane() {
        return informationModulePane;
    }

    public ExtensionPane getExtensionPane() {
        return extensionPane;
    }
}

