package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.models.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.7.2014
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HarnessProductionVM {

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    ModulsManager modulsManager;

    @WireVariable
    GrundVarianteModuleManager grundVarianteModuleManager;

    @WireVariable
    GrundVarianteNameManager grundVarianteNameManager;

    @WireVariable
    DmeVarianteNameManager dmeVarianteNameManager;

    @WireVariable
    TuelleBugVarianteNameManager tuelleBugVarianteNameManager;

    @WireVariable
    SchrittModulRm9X1WrmManager schrittManager;

    @WireVariable
    VsModulyWrmManager vsModulyWrmManager;

    @WireVariable
    ModulClipListWrmManager modulClipListWrmManager;

    @WireVariable
    FoamManager foamManager;

    @WireVariable
    SicherungenRelais9X1WrmManager sicherungenRelais9X1WrmManager;

    @WireVariable
    VariantManager variantManager;

    @WireVariable
    ServletManager servletManager;

    @Wire
    Image imgSw;

    @Wire
    Image imgGr;

    @Wire
    Vlayout vlayoutGrund;

    private String prodNr = null;
    private String kabelsatzKz = null;
    private String prodNrInfo = null;
    private String kabelsatzKzInfo = null;
    private Lpab62 lpab62;
    private String user;
    private List<Lpab64> modulesInProdNr;
    private Integer wertigkeit;
    private Integer wertigkeitDmeSw;
    private Integer wertigkeitDmeGr;
    private Integer wertigkeitTtuelleBug;
    private Map<Integer,Set<String>> seiteAmap;
    private Map<Integer,Set<String>> seiteBmap;
    private Map<Vs,Integer> vsModulesMap;
    private Set<Integer> modulsIdSet;
    //private List<Variant> variantList;
    private GrundVarianteName gvn;
    private DmeVarianteName dvnSw;
    private DmeVarianteName dvnGr;
    private TuelleBugVarianteName tbvn;
    private Set<String> brana = new HashSet<>();
    private Set<String> step5A;
    private Set<String> step6A;
    private Set<String> step7A;
    private Set<String> step8A;
    private Set<String> step4B;
    private Set<String> step5B;
    private Set<String> step6B;
    private Set<String> step7B;
    private Set<String> step8B;
    private Set<Vs> vsModules;
    private SortedSet<ModulClipListWrm> clipModules;
    private List<Foam> foamModules;
    private List<SicherungenRelais9X1Wrm> sicherungenModules;
    private String clipModulesStr = "";
    private String hlreVariant;
    private String tbVariant;


    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    @NotifyChange({"prodNr","kabelsatzKz","gvn","dvnSw","dvnGr","tbvn","step5A","step6A","step7A","step8A","sicherungenModules",
                   "step4B","step5B","step6B","step7B","step8B","variantList","vsModules","vsModulesMap","clipModulesStr",
            "foamModules","prodNr","kabelsatzKz","prodNrInfo","kabelsatzKzInfo","hlreVariant", "brana","tbVariant"})
    public void submit()
    {
        if(!lpab62Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz).isEmpty()){
        brana.clear();
        seiteAmap = new HashMap<Integer, Set<String>>();
        seiteBmap = new HashMap<Integer, Set<String>>();
        vsModulesMap = new TreeMap<Vs,Integer>();
        vsModules = new TreeSet<Vs>();
        clipModules = new TreeSet<ModulClipListWrm>();
        clipModulesStr = "";
        foamModules = new ArrayList<Foam>();
        sicherungenModules = new ArrayList<SicherungenRelais9X1Wrm>();
        modulsIdSet = new HashSet<Integer>();
        modulesInProdNr = lpab64Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz);
        wertigkeit = 0;
        wertigkeitDmeGr = 0;
        wertigkeitDmeSw = 0;
        wertigkeitTtuelleBug = 0;

        Integer modulsId;
        lpab62 = lpab62Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz).get(0);
        prodNrInfo = lpab62.getProdNr();
        kabelsatzKzInfo = lpab62.getKabelsatzKz();
        for(Lpab64 item : modulesInProdNr){
            if(!modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).isEmpty()){
            modulsId = modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).get(0).getId();
           // System.out.println("ID modulu " + modulsId);
            modulsIdSet.add(modulsId);
            //---------GVM--------------------------------------------------------------------------------
            GrundVarianteModule gvm = null;
            if(!grundVarianteModuleManager.findByModulsId(modulsId).isEmpty()){
              gvm = grundVarianteModuleManager.findByModulsId(modulsId).get(0);
                //System.out.println("najdeny gvm: id " + modulsId);
            };

            if (gvm != null){wertigkeit = wertigkeit + gvm.getWertigkeit();
                             wertigkeitDmeGr = wertigkeitDmeGr + gvm.getWertigkeitDmeGr();
                             wertigkeitDmeSw = wertigkeitDmeSw + gvm.getWertigkeitDmeSw();
                             wertigkeitTtuelleBug = wertigkeitTtuelleBug + gvm.getWertigkeitTuelleBug();
                             //System.out.println("pripocitavam sw: " + gvm.getWertigkeitDmeSw() +" gr: "+ gvm.getWertigkeitDmeGr());
                             //System.out.println("pripocitanavm " + gvm.getWertigkeitDmeSw());
            }
           //--------GVM ---------------------------------------------------------------------------------
           //-----RM--------------------------------------------------------------------------------------
           for(int i = 1 ; i < 9 ; i++){
            if(!schrittManager.findByModulIdSchrittSeite(modulsId,i,"A").isEmpty()){
            String schrmA = schrittManager.findByModulIdSchrittSeite(modulsId,i,"A").get(0).getModuls().getSachNrBest();
            if(seiteAmap.containsKey(i)){
               Set tempSet = seiteAmap.get(i);
               tempSet.add(schrmA.trim());
               seiteAmap.put(i,tempSet);
            }
             else{Set tempSet = new HashSet();
                  tempSet.add(schrmA.trim());
                  seiteAmap.put(i,tempSet);
             }
            }
            if(!schrittManager.findByModulIdSchrittSeite(modulsId,i,"B").isEmpty()){
            String schrmB = schrittManager.findByModulIdSchrittSeite(modulsId,i,"B").get(0).getModuls().getSachNrBest();
                if(seiteBmap.containsKey(i)){
                    Set tempSet = seiteBmap.get(i);
                    tempSet.add(schrmB.trim());
                    seiteBmap.put(i,tempSet);
                }
                else{Set tempSet = new HashSet();
                    tempSet.add(schrmB.trim());
                    seiteBmap.put(i,tempSet);
                }
            }
           }
           //step4A = seiteAmap.get(4);
           step5A = seiteAmap.get(5);
           step6A = seiteAmap.get(6);
           step7A = seiteAmap.get(7);
           step8A = seiteAmap.get(8);
           step4B = seiteBmap.get(4);
           step5B = seiteBmap.get(5);
           step6B = seiteBmap.get(6);
           step7B = seiteBmap.get(7);
           step8B = seiteBmap.get(8);

          //-----RM----------------------------------------------------------------------------------------------
          //-----LETOVANIE---------------------------------------------------------------------------------------
          if(!vsModulyWrmManager.findByModulId(modulsId).isEmpty()){
            for(VsModulyWrm vsmodul : vsModulyWrmManager.findByModulId(modulsId)){
                Vs vs = vsmodul.getVs();
                if(!vsModules.contains(vs)) {vsModules.add(vs);}

                if (vsModulesMap.containsKey(vs)){
                  int tempCount = vsModulesMap.get(vsmodul.getVs());
                  tempCount++;
                  vsModulesMap.put(vs,tempCount);
                }
                else{vsModulesMap.put(vs,1);}
            };
          }
          //-----LETOVANIE---------------------------------------------------------------------------------------
          //-----KLIPY---------------------------------------------------------------------------------------
            if(!modulClipListWrmManager.findByModulId(modulsId).isEmpty()){
                clipModules.addAll(modulClipListWrmManager.findByModulId(modulsId));
            }
          //-----KLIPY---------------------------------------------------------------------------------------
          //-----ZAPENOVACKA---------------------------------------------------------------------------------------
            if(!foamManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).isEmpty()){
                foamModules.add(foamManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).get(0));
            }
          //-----ZAPENOVACKA---------------------------------------------------------------------------------------
          //-----OPTOAUTOMAT---------------------------------------------------------------------------------------
            if(!sicherungenRelais9X1WrmManager.findByModulId(modulsId).isEmpty()){
                sicherungenModules.addAll(sicherungenRelais9X1WrmManager.findByModulId(modulsId));
            }
          //-----OPTOAUTOMAT---------------------------------------------------------------------------------------
        }
        }
            System.out.println("modulyyyyyyyyyyyyyyyyyyy " + modulsIdSet);
        //variantList = variantManager.controlModulsSetInVariants(modulsIdSet);

        //Collections.sort(clipModules);
        Collections.sort(sicherungenModules);
        for(ModulClipListWrm modulClip : clipModules){
            if (clipModulesStr.isEmpty()) clipModulesStr = modulClip.getCodeClip().trim();
            else clipModulesStr = clipModulesStr + ", " + modulClip.getCodeClip().trim();
        }
        /*if(!grundVarianteNameManager.findBySumWertigkeit(wertigkeit).isEmpty()){
            gvn = grundVarianteNameManager.findBySumWertigkeit(wertigkeit).get(0);
            System.out.println("PRIDAVAM NA GRUND Z GRUNDVARIANTE NAME" + gvn.getVarianteName());
            brana.add("BRANA  "+gvn.getVarianteName()+"     "+gvn.getBeschreibung());
        } */
             //System.out.println("celkova vaha: sw "+wertigkeitDmeSw+ " gr "+wertigkeitDmeGr);
        if (wertigkeitDmeSw>0)dvnSw = dmeVarianteNameManager.findBySumWertigkeit(wertigkeitDmeSw).get(0);
        if (wertigkeitDmeGr>0)dvnGr = dmeVarianteNameManager.findBySumWertigkeit(wertigkeitDmeGr).get(0);
        //-----------------------
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa    "+wertigkeitDmeSw);
        if(wertigkeitDmeSw>0){
        byte[] byteSw = dvnSw.getBild();
        InputStream inSw = new ByteArrayInputStream(byteSw);
        try {
            BufferedImage bImageFromConvertSw = ImageIO.read(inSw);
            imgSw.setContent(bImageFromConvertSw);
            inSw.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }}
        if(wertigkeitDmeGr>0){
            byte[] byteGr = dvnGr.getBild();
            InputStream inGr = new ByteArrayInputStream(byteGr);
            try {
                BufferedImage bImageFromConvertGr = ImageIO.read(inGr);
                imgGr.setContent(bImageFromConvertGr);
                inGr.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }}

        //-----
//        Components.removeAllChildren(vlayoutGrund);
        //System.out.println("najdene varianty: ");
        /*for (Variant variant: variantList){
            //System.out.println(variant.getName() + " " + variant.getDescription());
            if (variant.getWorkplace().getName().contains("GRUND")) {
               //System.out.println("PRIDAVAM NA GRUND" + variant.getDescription());
                brana.add(variant.getDescription()+"     "+variant.getScanString());
            }
            if (variant.getWorkplace().getName().contains("HLRE")){hlreVariant=variant.getDescription()+"    "+variant.getScanString();};
            if (lpab62.getAusfuehrung().trim().equals("RL")&&
                variant.getWorkplace().getName().contains("F9X1RL_GRUND")){hlreVariant=variant.getDescription()+"    "+variant.getScanString();};
            if (variant.getWorkplace().getName().contains("GRUND")){hlreVariant=variant.getDescription()+"    "+variant.getScanString();};
            if (variant.getWorkplace().getName().contains("TUELLE")){tbVariant=variant.getDescription()+"    "+variant.getScanString();};
            if (variant.getWorkplace().getName().contains("RM5A")){step5A.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM6A")){step6A.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM7A")){step7A.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM8A")){step8A.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM4B")){step4B.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM5B")){step5B.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM6B")){step6B.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM7B")){step7B.add(variant.getDescription()+"    "+variant.getScanString());};
            if (variant.getWorkplace().getName().contains("RM8B")){step8B.add(variant.getDescription()+"    "+variant.getScanString());};

        } */
        List<Variant> variantToShow = null;
        variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"GRUND-BRANA");
        if(variantToShow != null) {
            for(Variant variant:variantToShow){
                                                brana.add(variant.getDescription()+"     "+variant.getScanString());
                                                }
            variantToShow = null;
        }
        variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"GRUND-HL-RE");
        /*if(variantToShow != null) {hlreVariant = variantToShow.getDescription()+"     "+variantToShow.getScanString();
                                    variantToShow = null;}
        variantToShow = variantManager.getSingleVariantFromMap(modulsIdSet,"F9X1RL_GRUND");*/
        if(variantToShow != null && lpab62.getAusfuehrung().trim().equals("RL")) {
            for(Variant variant:variantToShow){
                                              hlreVariant = variant.getDescription()+"     "+variant.getScanString();}
                variantToShow = null;
        }
        /*variantToShow = variantManager.getSingleVariantFromMap(modulsIdSet,"GRUND");
        if(variantToShow != null) {hlreVariant = variantToShow.getDescription()+"     "+variantToShow.getScanString();
                variantToShow = null;}  */
        variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"TUELLE");
        if(variantToShow != null) {
            for(Variant variant:variantToShow){
            tbVariant = variant.getDescription()+"     "+variant.getScanString();}
               variantToShow = null;}
        variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM5A");
        if(variantToShow != null && step5A != null) {
            for(Variant variant:variantToShow){
            step5A.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}
        variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM6A");
        if(variantToShow != null && step6A != null) {
            for(Variant variant:variantToShow){
            step6A.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}
        variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM7A");
        if(variantToShow != null && step7A != null) {
            for(Variant variant:variantToShow){
            step7A.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}
        variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM8A");
        if(variantToShow != null && step8A != null) {
            for(Variant variant:variantToShow){
            step8A.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}
            variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM5B");
            if(variantToShow != null && step5B != null) {
                for(Variant variant:variantToShow){
                    step5B.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}
            variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM6B");
            if(variantToShow != null && step6B != null) {
                for(Variant variant:variantToShow){
                    step6B.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}
            variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM7B");
            if(variantToShow != null && step7B != null) {
                for(Variant variant:variantToShow){
                    step7B.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}
            variantToShow = variantManager.getSingleListVariantFromMap(modulsIdSet,"RM8B");
            if(variantToShow != null && step8B != null) {
                for(Variant variant:variantToShow){
                    step8B.add(variant.getDescription()+"     "+variant.getScanString());}
                variantToShow = null;}



            //if(!tuelleBugVarianteNameManager.findBySumWertigkeit(wertigkeitTtuelleBug).isEmpty()){tbvn = tuelleBugVarianteNameManager.findBySumWertigkeit(wertigkeitTtuelleBug).get(0);
       //}
        if (wertigkeitDmeSw>0)System.out.println("dvnsw" + dvnSw.getVarianteName());
        if (wertigkeitDmeGr>0)System.out.println("dvngr" + dvnGr.getVarianteName());
        //if  (tbvn!=null)System.out.println("tuelle" + tbvn.getBeschreibung());
        /*System.out.println("RM" + seiteAmap + seiteBmap);
        System.out.println("LETOVANIE" + vsModulesMap);
        System.out.println("ZAPENOVACKA" + foamModules);
        System.out.println("OPTOAUTOMAT" + sicherungenModules);
        System.out.println("VARIANTY" + variantList);*/
        prodNr = null;
        kabelsatzKz = null;
        }else{Messagebox.show("Kablovka so zadanym prod. cislom a typom neexistuje!", "Chyba.", Messagebox.OK, Messagebox.ERROR);}
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
    }

    public TuelleBugVarianteName getTbvn() {
        return tbvn;
    }

    public void setTbvn(TuelleBugVarianteName tbvn) {
        this.tbvn = tbvn;
    }

    public DmeVarianteName getDvnGr() {
        return dvnGr;
    }

    public void setDvnGr(DmeVarianteName dvnGr) {
        this.dvnGr = dvnGr;
    }

    public DmeVarianteName getDvnSw() {
        return dvnSw;
    }

    public void setDvnSw(DmeVarianteName dvnSw) {
        this.dvnSw = dvnSw;
    }

    public GrundVarianteName getGvn() {
        return gvn;
    }

    public void setGvn(GrundVarianteName gvn) {
        this.gvn = gvn;
    }

    public Set<String> getStep8B() {
        return step8B;
    }

    public void setStep8B(Set<String> step8B) {
        this.step8B = step8B;
    }

    public Set<String> getStep5A() {
        return step5A;
    }

    public void setStep5A(Set<String> step5A) {
        this.step5A = step5A;
    }

    public Set<String> getStep6A() {
        return step6A;
    }

    public void setStep6A(Set<String> step6A) {
        this.step6A = step6A;
    }

    public Set<String> getStep7A() {
        return step7A;
    }

    public void setStep7A(Set<String> step7A) {
        this.step7A = step7A;
    }

    public Set<String> getStep8A() {
        return step8A;
    }

    public void setStep8A(Set<String> step8A) {
        this.step8A = step8A;
    }

    public Set<String> getStep4B() {
        return step4B;
    }

    public void setStep4B(Set<String> step4B) {
        this.step4B = step4B;
    }

    public Set<String> getStep5B() {
        return step5B;
    }

    public void setStep5B(Set<String> step5B) {
        this.step5B = step5B;
    }

    public Set<String> getStep6B() {
        return step6B;
    }

    public void setStep6B(Set<String> step6B) {
        this.step6B = step6B;
    }

    public Set<String> getStep7B() {
        return step7B;
    }

    public void setStep7B(Set<String> step7B) {
        this.step7B = step7B;
    }


    public List<SicherungenRelais9X1Wrm> getSicherungenModules() {
        return sicherungenModules;
    }

    public void setSicherungenModules(List<SicherungenRelais9X1Wrm> sicherungenModules) {
        this.sicherungenModules = sicherungenModules;
    }

    public List<Foam> getFoamModules() {
        return foamModules;
    }

    public void setFoamModules(List<Foam> foamModules) {
        this.foamModules = foamModules;
    }


    public Set<Vs> getVsModules() {
        return vsModules;
    }

    public void setVsModules(Set<Vs> vsModules) {
        this.vsModules = vsModules;
    }

    public Map<Vs, Integer> getVsModulesMap() {
        return vsModulesMap;
    }

    public void setVsModulesMap(Map<Vs, Integer> vsModulesMap) {
        this.vsModulesMap = vsModulesMap;
    }

    public String getClipModulesStr() {
        return clipModulesStr;
    }

    public void setClipModulesStr(String clipModulesStr) {
        this.clipModulesStr = clipModulesStr;
    }

    public String getProdNrInfo() {
        return prodNrInfo;
    }

    public void setProdNrInfo(String prodNrInfo) {
        this.prodNrInfo = prodNrInfo;
    }

    public String getKabelsatzKzInfo() {
        return kabelsatzKzInfo;
    }

    public void setKabelsatzKzInfo(String kabelsatzKzInfo) {
        this.kabelsatzKzInfo = kabelsatzKzInfo;
    }

    public String getHlreVariant() {
        return hlreVariant;
    }

    public void setHlreVariant(String hlreVariant) {
        this.hlreVariant = hlreVariant;
    }

    public Set<String> getBrana() {
        return brana;
    }

    public void setBrana(Set<String> brana) {
        this.brana = brana;
    }

    public String getTbVariant() {
        return tbVariant;
    }

    public void setTbVariant(String tbVariant) {
        this.tbVariant = tbVariant;
    }
}
