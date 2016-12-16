package com.leoni.viewModel;

import com.leoni.data.manager.ColorManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.VariantManager;
import com.leoni.data.manager.WorkplaceManager;
import com.leoni.data.models.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.4.2014
 * Time: 15:20
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VariantCopyViewModel {

    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private VariantManager variantManager;

    @WireVariable
    private ColorManager colorManager;

    @WireVariable
    private WorkplaceManager workplaceManager;

    private String name;
    private String description;
    private String scanString;
    private Variant myVariant;
    private String xmlFile;
    private Boolean scanReq;
    private Color color;
    private Workplace workplace;
    private String typ;
    private List<Workplace> workplaceList = new ArrayList<Workplace>();
    private List<Color> colorList = new ArrayList<Color>();
    private Map<Integer,Set<Integer>> modulsMap = new HashMap<Integer, Set<Integer>>();
    private List<String> modulsList;
    private List<Textbox> textboxList = new ArrayList<Textbox>();
    private List<MyHlayout> myHlayoutList = new ArrayList<MyHlayout>();
    Map<Integer,Set<Moduls>> mp = new HashMap<Integer, Set<Moduls>>();
    String user;

    @AfterCompose
    public void doAfterCompose(@BindingParam("vlayout") Vlayout vlayout,
                               @BindingParam("hlayout") Hlayout hlayout,
                               @ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myVariant") Variant myObject) throws Exception {
        //myVariant=myObject;
        //-----
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        workplaceList = workplaceManager.getAll();
        colorList = colorManager.getAll();
        myVariant = myObject;
        name = myObject.getName();
        description = myObject.getDescription();
        scanString = myObject.getScanString();
        scanReq = myObject.getScanRequired();
        color = myObject.getColor();
        workplace = myObject.getWorkplace();
        xmlFile = myObject.getXmlModuls();
        typ = myObject.getTyp();
        //-----
        /*xmlFile = myObject.getXmlModuls();
        name = myObject.getName();
        scanString = myObject.getScanString();
        scanReq = myObject.getScanRequired();*/
        modulsMap = XMLBuilder.buildMapFromXML(xmlFile);
        modulsList =  modulsManager.getAllModulsLief();
        for (Map.Entry<Integer, Set<Integer>> entry : modulsMap.entrySet())
        {
            MyHlayout myHlayout = new MyHlayout();
            vlayout.appendChild(myHlayout);
            myHlayoutList.add(myHlayout);

            //plusSet.add(tempLabel);*/
            Set<Moduls> modulsSet = new HashSet<Moduls>();
            for(Integer item : entry.getValue()){
                modulsSet.add(modulsManager.findById(item).get(0));
            }
            myHlayout.addTextboxSet(modulsSet);
            Label tempLabel = new Label(" + ");
            vlayout.appendChild(tempLabel);
        }

        String[] partsScan = scanString.split("\\|");
        for(String item:partsScan){
            Textbox textbox = new Textbox(item.trim());
            hlayout.appendChild(textbox);
            textboxList.add(textbox);
        }

    }

    @Command
    //@NotifyChange({"modul21Vis", "modul31Vis", "modul41Vis", "modul51Vis", "modul61Vis"})
    public void addTextBoxVert(@ContextParam(ContextType.VIEW) Component view,@BindingParam("vlayout") Vlayout vlayout) {
        if(myHlayoutList.isEmpty()||!myHlayoutList.get(myHlayoutList.size()-1).getSelectedModuls().isEmpty()){
            MyHlayout myHlayout = new MyHlayout();
            vlayout.appendChild(myHlayout);
            myHlayoutList.add(myHlayout);
            Label tempLabel = new Label(" + ");
            vlayout.appendChild(tempLabel);
            //plusSet.add(tempLabel);
        }
        else{ Messagebox.show("Vyplnte predosly modul!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    public void addTextBoxHor(@ContextParam(ContextType.VIEW) Component view,@BindingParam("hlayout") Hlayout hlayout) {
        Textbox textbox = new Textbox();
        hlayout.appendChild(textbox);
        textboxList.add(textbox);
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        Set<Moduls> modulsSet = new HashSet<Moduls>();
        int counterHlayouts = 0;
        boolean hasWrongModulLiefnr=false;
        for (MyHlayout myHlayout : myHlayoutList){
            Set<Moduls> modulsInHlayoutSet = new HashSet<Moduls>();
            for(String itemLief : myHlayout.getSelectedModuls()){
                if(modulsManager.findBySachNrLieferant(itemLief)!=null){
                    modulsSet.add(modulsManager.findBySachNrLieferant(itemLief).get(0));
                    modulsInHlayoutSet.add(modulsManager.findBySachNrLieferant(itemLief).get(0));
                }
                else hasWrongModulLiefnr = true;
            }
            mp.put(counterHlayouts,modulsInHlayoutSet);
            counterHlayouts++;
        }

        boolean isFirst = true;
        scanString = "";
        for (Textbox textbox : textboxList){
            if(textbox!=null&&!textbox.getValue().trim().equals("")){
                if (isFirst){scanString = scanString + textbox.getValue().trim();
                    isFirst = false;
                }
                else {scanString = scanString + "|" +textbox.getValue().trim();}
            }
        }
        //String description = XMLBuilder.buildDesc(mp);
        String xmlModuls = XMLBuilder.buildXML(mp);
        myVariant.setId(null);
        myVariant.setName(name);
        myVariant.setScanString(scanString);
        myVariant.setModulsSet(modulsSet);
        myVariant.setDescription(description);
        myVariant.setXmlModuls(xmlModuls);
        myVariant.setScanRequired(scanReq);
        myVariant.setColor(color);
        myVariant.setWorkplace(workplace);
        myVariant.setTyp(typ);
        int modCount=0;
        for(MyHlayout myHlayout: myHlayoutList){
            //if(myHlayout.getFirstCb().getSelectedItem()!=null&&!modulsManager.findBySachNrLieferant((String)myHlayout.getFirstCb().getSelectedItem().getValue()).isEmpty())
            if(myHlayout.hasAtLeast1SelectedModul())
                modCount++;
        }
        myVariant.setModulsCount(modCount);
        variantManager.saveEditedVariant(myVariant,user);
        BindUtils.postGlobalCommand(null, null, "refresh", null);
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        view.detach();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScanString() {
        return scanString;
    }

    public void setScanString(String scanString) {
        this.scanString = scanString;
    }

    public Boolean getScanReq() {
        return scanReq;
    }

    public void setScanReq(Boolean scanReq) {
        this.scanReq = scanReq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public List<Workplace> getWorkplaceList() {
        return workplaceList;
    }

    public void setWorkplaceList(List<Workplace> workplaceList) {
        this.workplaceList = workplaceList;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
