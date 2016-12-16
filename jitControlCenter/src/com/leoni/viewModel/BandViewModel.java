package com.leoni.viewModel;

import com.leoni.data.manager.BandManager;
import com.leoni.data.manager.ModulsManager;

import com.leoni.data.manager.ModulsManagerImpl;
import com.leoni.data.models.Harness;
import com.leoni.data.models.Moduls;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.12.2013
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BandViewModel {

    @WireVariable
    private BandManager bandManager;



    private List<Harness> harnessList = new ArrayList<Harness>();
    private String brettType="";
    private String prodNr="";
    private String brettOnClips;
    private Harness selectedHarness;
    private boolean showRL=true;
    private boolean showDisable981;
    private boolean showDisable991;
    private boolean showEnable981;
    private boolean showEnable991;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        harnessList=bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
        showDisable981 = bandManager.is981enabled()&&bandManager.is991enabled();
        showDisable991 = bandManager.is981enabled()&&bandManager.is991enabled();
        showEnable981 = !bandManager.is981enabled();
        showEnable991 = !bandManager.is991enabled();
        if(showDisable981) brettOnClips = "";
        else{
            if(showEnable981){brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F981RL");}
            else{brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F991RL");}
        }
    }

    @Command
    @NotifyChange ({"harnessList"})
    public void refreshBand(){
        harnessList=bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
    }

    @Command
    @NotifyChange ({"harnessList"})
    public void searchByBrettType()
    {
        if (brettType.equals("")){harnessList=bandManager.getAll();}
        else harnessList = bandManager.findByBrettType(brettType);
    };

    @Command
    @NotifyChange ({"harnessList"})
    public void searchByProdNr()
    {
        if (prodNr.equals("")){harnessList=bandManager.getAll();}
        else harnessList = bandManager.findByProdNr(prodNr);
    };

    @Command
    @NotifyChange ({"harnessList"})
    public void disableHarness(@BindingParam("harness") Harness myHarness)
    {
       myHarness.setLock(true);
       selectedHarness = bandManager.updateHarness(myHarness);
        BindUtils.postNotifyChange(null, null, BandViewModel.this, "selectedHarness");
        if (prodNr.equals("")&&brettType.equals("")){harnessList=bandManager.getAll();}
        else { if (!brettType.equals("")){harnessList=bandManager.findByBrettType(brettType);}
               if (!prodNr.equals("")){harnessList=bandManager.findByProdNr(prodNr);}

        }
        BindUtils.postNotifyChange(null, null, BandViewModel.this, "harnessList");
    };

    @Command
    @NotifyChange ({"harnessList"})
    public void enableHarness(@BindingParam("harness") Harness myHarness)
    {
        myHarness.setLock(false);
        selectedHarness = bandManager.updateHarness(myHarness);
        BindUtils.postNotifyChange(null, null, BandViewModel.this, "selectedHarness");
        if (prodNr.equals("")&&brettType.equals("")){harnessList=bandManager.getAll();}
        else {if (!brettType.equals("")){harnessList=bandManager.findByBrettType(brettType);}
            if (!prodNr.equals("")){harnessList=bandManager.findByProdNr(prodNr);}

        }
        BindUtils.postNotifyChange(null, null, BandViewModel.this, "harnessList");
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void disableAll981RL()
    {
       bandManager.disableAll981();
       harnessList = bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
       brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F981RL");
       showDisable981 = false;
       showDisable991 = false;
       showEnable981 = true;
       showEnable991 = false;
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void disableAll991RL()
    {
       bandManager.disableAll991();
       harnessList = bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
        brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F991RL");
        showDisable981 = false;
        showDisable991 = false;
        showEnable981 = false;
        showEnable991 = true;
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void enableAll981RL()
    {
        if(bandManager.isFirstBrettIdOk("F981RL")){
        bandManager.enableAll981();
        harnessList = bandManager.findByBrettTypeRLALL();
            Collections.sort(harnessList);
            brettOnClips = "";
            showDisable981 = true;
            showDisable991 = true;
            showEnable981 = false;
            showEnable991 = false;
        }
        else{Messagebox.show("Dosky niesu v rovnakom stave ako pred prechodom!", "Error", Messagebox.OK, Messagebox.ERROR);}
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void enableAll991RL()
    {
        if(bandManager.isFirstBrettIdOk("F991RL")){
        bandManager.enableAll991();
        harnessList = bandManager.findByBrettTypeRLALL();
            Collections.sort(harnessList);
            brettOnClips = "";
            showDisable981 = true;
            showDisable991 = true;
            showEnable981 = false;
            showEnable991 = false;
        }
        else{
            Messagebox.show("Dosky niesu v rovnakom stave ako pred prechodom!", "Error", Messagebox.OK, Messagebox.ERROR);}
    };

    @Command
    @NotifyChange ({"harnessList","showRL"})
    public void showRL()
    {
        harnessList = bandManager.findByBrettTypeRL();
        showRL=false;
    };

    @Command
    @NotifyChange ({"harnessList","showRL"})
    public void showAll()
    {
        harnessList = bandManager.getAll();
        showRL=true;
    };

    public void setHarnessList(List<Harness> harnessList) {
        this.harnessList = harnessList;
    }
    public List<Harness> getHarnessList() {
        return harnessList;
    }

    public String getBrettType() {
        return brettType;
    }

    public void setBrettType(String brettType) {
        this.brettType = brettType;
    }

    public Harness getSelectedHarness() {
        return selectedHarness;
    }

    public void setSelectedHarness(Harness selectedHarness) {
        this.selectedHarness = selectedHarness;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public boolean getShowRL() {
        return showRL;
    }

    public void setShowRL(boolean showRL) {
        this.showRL = showRL;
    }

    public boolean isShowDisable981() {
        return showDisable981;
    }

    public void setShowDisable981(boolean showDisable981) {
        this.showDisable981 = showDisable981;
    }

    public boolean isShowDisable991() {
        return showDisable991;
    }

    public void setShowDisable991(boolean showDisable991) {
        this.showDisable991 = showDisable991;
    }

    public boolean isShowEnable981() {
        return showEnable981;
    }

    public void setShowEnable981(boolean showEnable981) {
        this.showEnable981 = showEnable981;
    }

    public boolean isShowEnable991() {
        return showEnable991;
    }

    public void setShowEnable991(boolean showEnable991) {
        this.showEnable991 = showEnable991;
    }

    public String getBrettOnClips() {
        return brettOnClips;
    }

    public void setBrettOnClips(String brettOnClips) {
        this.brettOnClips = brettOnClips;
    }
}
