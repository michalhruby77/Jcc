package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.models.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.6.2014
 * Time: 9:40
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindHarnessVM {
    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private LwadrKennManager lwadrKennManager;

    @WireVariable
    private UsersManager usersManager;

    @WireVariable
    private ProdNrLogManager prodNrLogManager;

    private String harnessScan = "";
    private Lpab62 harnessLpab62=null;
    private List<Lpab64> harnessLpab64List=null;
    private String selectedKs;
    private String tempProdNr;
    private String tempKabelsatz;
    private Set<String> kabelsatzList;
    private String userName;
    private Users user;
    private boolean updateStaPruefElektr = false;
    private boolean deleteStaPruefElektr = false;
    private boolean updateStaPruefRelais = false;
    private boolean deleteStaPruefRelais = false;
    private boolean updateStaSicherung = false;
    private boolean deleteStaSicherung = false;
    private boolean updateStaClip = false;
    private boolean deleteStaClip = false;
    private boolean updateStaTuelle = false;
    private boolean deleteStaTuelle = false;
    private boolean updateStaWa = false;
    private boolean deleteStaWa = false;
    private boolean updateStaArchiv = false;
    private boolean deleteStaArchiv = false;
    private boolean updateStaSchraub = false;
    private boolean deleteStaSchraub = false;
    private boolean updateStaSchaumen = false;
    private boolean deleteStaSchaumen = false;
    private boolean updateStaLoetung = false;
    private boolean deleteStaLoetung = false;
    private boolean updateStaBandEinlauf = false;
    private boolean deleteStaBandEinlauf = false;
    private boolean updateStaBandAuslauf = false;
    private boolean deleteStaBandAuslauf = false;
    private boolean updateStaEsdSchraubBegin = false;
    private boolean deleteStaEsdSchraubBegin = false;
    private boolean updateStaEsdSchraub = false;
    private boolean deleteStaEsdSchraub = false;


    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        kabelsatzList = new HashSet<String>();
        for (LwadrKenn item : lwadrKennManager.getAll()){
            kabelsatzList.add(item.getKabelsatzKz().trim());
        }
        userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = usersManager.getUser(userName.trim());


    }

    @Command
    @NotifyChange({"harnessScan","selectedKs","harnessLpab62","harnessLpab64List",
            "updateStaPruefElektr","deleteStaPruefElektr","updateStaPruefRelais","deleteStaPruefRelais","updateStaSicherung","deleteStaSicherung",
            "updateStaClip","deleteStaClip","updateStaTuelle","deleteStaTuelle","updateStaWa","deleteStaWa","updateStaArchiv","deleteStaArchiv",
            "updateStaSchraub","deleteStaSchraub","updateStaSchaumen","deleteStaSchaumen","updateStaLoetung","deleteStaLoetung","updateStaBandEinlauf",
            "deleteStaBandEinlauf","updateStaBandAuslauf","deleteStaBandAuslauf","updateStaEsdSchraubBegin","deleteStaEsdSchraubBegin","updateStaEsdSchraub",
            "deleteStaEsdSchraub"})
    public void submit()
    {
      /*String substring = harnessScan.trim().substring(0, 3);
      String prodNr = harnessScan.trim().substring(3);
      if(substring.toLowerCase().equals("20s")||substring.toLowerCase().equals("25s")||substring.toLowerCase().equals("41s")||
              substring.toLowerCase().equals("42s")||substring.toLowerCase().equals("45s")||substring.toLowerCase().equals("46s"))
      {
        char kskz = '0';
        if (substring.toLowerCase().equals("20s")) kskz = 'F';
        if (substring.toLowerCase().equals("25s")) kskz = 'C';
        if (substring.toLowerCase().equals("41s")) kskz = 'T';
        if (substring.toLowerCase().equals("42s")) kskz = 'U';
        if (substring.toLowerCase().equals("45s")) kskz = 'E';
        if (substring.toLowerCase().equals("46s")) kskz = 'R';
        harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(prodNr, String.valueOf(kskz)).get(0);
        harnessLpab64List = lpab64Manager.findByProdnrAndKabelsatz(prodNr, String.valueOf(kskz));
      }
        else Messagebox.show("Kablovka bola zle naskenovana!", "Error", Messagebox.OK, Messagebox.ERROR);
       */
        if(!lpab62Manager.findByProdnrAndKabelsatz(harnessScan.trim(), String.valueOf(selectedKs)).isEmpty()){
        harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(harnessScan.trim(), String.valueOf(selectedKs)).get(0);
        harnessLpab64List = lpab64Manager.findByProdnrAndKabelsatz(harnessScan.trim(), String.valueOf(selectedKs));
            Collections.sort(harnessLpab64List, new Comparator<Lpab64>() {
                public int compare(Lpab64 mod1 , Lpab64 mod2) {
                    return mod1.getSachNrBest().compareTo(mod2.getSachNrBest());
                }
            });
        tempProdNr = harnessScan.trim();
        tempKabelsatz = String.valueOf(selectedKs);
        selectedKs = null;
        harnessScan = null;
            for(Roles role : user.getUserRoles()){
                String permissionsString = role.getPermissions();
                String[] permissions = new String[]{};
                if(permissionsString!=null)
                {permissions = permissionsString.split(",");}
                for (String permission : permissions){
                    if(permission.trim().equals("updateStaPruefElektr"))updateStaPruefElektr = true;
                    if(permission.trim().equals("deleteStaPruefElektr"))deleteStaPruefElektr = true;
                    if(permission.trim().equals("updateStaPruefRelais"))updateStaPruefRelais = true;
                    if(permission.trim().equals("deleteStaPruefRelais"))deleteStaPruefRelais = true;
                    if(permission.trim().equals("updateStaSicherung"))updateStaSicherung = true;
                    if(permission.trim().equals("deleteStaSicherung"))deleteStaSicherung = true;
                    if(permission.trim().equals("updateStaClip"))updateStaClip = true;
                    if(permission.trim().equals("deleteStaClip"))deleteStaClip = true;
                    if(permission.trim().equals("updateStaTuelle"))updateStaTuelle = true;
                    if(permission.trim().equals("deleteStaTuelle"))deleteStaTuelle = true;
                    if(permission.trim().equals("updateStaWa"))updateStaWa = true;
                    if(permission.trim().equals("deleteStaWa"))deleteStaWa = true;
                    if(permission.trim().equals("updateStaArchiv"))updateStaArchiv = true;
                    if(permission.trim().equals("deleteStaArchiv"))deleteStaArchiv = true;
                    if(permission.trim().equals("updateStaSchraub"))updateStaSchraub = true;
                    if(permission.trim().equals("deleteStaSchraub"))deleteStaSchraub = true;
                    if(permission.trim().equals("updateStaSchaumen"))updateStaSchaumen = true;
                    if(permission.trim().equals("deleteStaSchaumen"))deleteStaSchaumen = true;
                    if(permission.trim().equals("updateStaLoetung"))updateStaLoetung = true;
                    if(permission.trim().equals("deleteStaLoetung"))deleteStaLoetung = true;
                    if(permission.trim().equals("updateStaBandEinlauf"))updateStaBandEinlauf = true;
                    if(permission.trim().equals("deleteStaBandEinlauf"))deleteStaBandEinlauf = true;
                    if(permission.trim().equals("updateStaBandAuslauf"))updateStaBandAuslauf = true;
                    if(permission.trim().equals("deleteStaBandAuslauf"))deleteStaBandAuslauf = true;
                    if(permission.trim().equals("updateStaEsdSchraubBegin"))updateStaEsdSchraubBegin = true;
                    if(permission.trim().equals("deleteStaEsdSchraubBegin"))deleteStaEsdSchraubBegin = true;
                    if(permission.trim().equals("updateStaEsdSchraub"))updateStaEsdSchraub = true;
                    if(permission.trim().equals("deleteStaEsdSchraub"))deleteStaEsdSchraub = true;
                }
            }
        }
        else Messagebox.show("Zaznam nebol najdeny!", "Error", Messagebox.OK, Messagebox.ERROR);
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaPruefElektr(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaPruefElektr(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"PRUEF ELEKTR DELETE","PRUEF ELEKTR DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaPruefRelais(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaPruefRelais(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"PRUEF RELAIS DELETE","PRUEF RELAIS DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaSicherung(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaSicherung(null);
                            harnessLpab62.setStaPruefRelais(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"SICHERUNG DELETE","SICHERUNG DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaClip(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaClip(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"STA CLIP DELETE","STA CLIP DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaTuelle(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaTuelle(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"STA TUELLE DELETE","STA TUELLE DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaWa(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaWa(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"STA WA DELETE","STA WA DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaArchiv(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaArchiv(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"STA ARCHIV DELETE","STA ARCHIV DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaSchraub(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaSchraub(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"STA SCHRAUB DELETE","STA SCHRAUB DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaSchaumen(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaSchaumen(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"SCHAUMEN DELETE","SCHAUMEN DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaLoetung(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaLoetung(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"LOETUNG DELETE","LOETUNG DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaBandEinlauf(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaBandEinlauf(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"EINLAUF DELETE","EINLAUF DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaBandAuslauf(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaBandAuslauf(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"AUSLAUF DELETE","AUSLAUF DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaEsdSchraub(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaEsdSchraub(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"ESD SCHRAUB DELETE","ESD SCHRAUB DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"harnessLpab62"})
    public void deleteStaEsdSchraubBegin(){
        Messagebox.show("Ste si isti?", "Zmazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            harnessLpab62.setStaEsdSchraubBegin(null);
                            lpab62Manager.saveOrUpdate(harnessLpab62);
                            prodNrLogManager.saveProdNrLog(harnessLpab62.getProdNr(),harnessLpab62.getKabelsatzKz(),"jcc",userName,"SCREW ESD DELBEGIN","ESD SCHRAUB BEGIN DELETED");
                            harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
                            BindUtils.postNotifyChange(null, null, FindHarnessVM.this, "harnessLpab62");
                        }
                    }
                }
        );
    }

    @Command
    public void updateStaPruefElektr() {
        showModal("StaPruefElektr");
    }

    @Command
    public void updateStaPruefRelais() {
        showModal("StaPruefRelais");
    }

    @Command
    public void updateStaSicherung() {
        showModal("StaSicherung");
    }

    @Command
    public void updateStaClip() {
        showModal("StaClip");
    }

    @Command
    public void updateStaTuelle() {
        showModal("StaTuelle");
    }

    @Command
    public void updateStaWa() {
        showModal("StaWa");
    }

    @Command
    public void updateStaArchiv() {
        showModal("StaArchiv");
    }

    @Command
    public void updateStaSchraub() {
        showModal("StaSchraub");
    }

    @Command
    public void updateStaSchaumen() {
        showModal("StaSchaumen");
    }

    @Command
    public void updateStaLoetung() {
        showModal("StaLoetung");
    }

    @Command
    public void updateStaBandEinlauf() {
        showModal("StaBandEinlauf");
    }

    @Command
    public void updateStaBandAuslauf() {
        showModal("StaBandAuslauf");
    }

    @Command
    public void updateStaEsdSchraubBegin() {
        showModal("StaEsdSchraubBegin");
    }

    @Command
    public void updateStaEsdSchraub() {
        showModal("StaEsdSchraub");
    }


    public void showModal(String status) {
        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("prodNr", tempProdNr);
        arguments.put("kabelsatz", tempKabelsatz);
        arguments.put("status", status);
        Window window = (Window) Executions.createComponents(
                "findHarness/updateHarness.zul", null, arguments);
        window.doModal();
    }

    @NotifyChange ({"harnessLpab62"})
    @GlobalCommand
    public void refreshLpab62(){
        harnessLpab62 = lpab62Manager.findByProdnrAndKabelsatz(tempProdNr,tempKabelsatz).get(0);
    }

    public List<Lpab64> getHarnessLpab64List() {
        return harnessLpab64List;
    }

    public void setHarnessLpab64List(List<Lpab64> harnessLpab64List) {
        this.harnessLpab64List = harnessLpab64List;
    }

    public Lpab62 getHarnessLpab62() {
        return harnessLpab62;
    }

    public void setHarnessLpab62(Lpab62 harnessLpab62) {
        this.harnessLpab62 = harnessLpab62;
    }

    public String getHarnessScan() {
        return harnessScan;
    }

    public void setHarnessScan(String harnessScan) {
        this.harnessScan = harnessScan;
    }

    public Set<String> getKabelsatzList() {
        return kabelsatzList;
    }

    public void setKabelsatzList(Set<String> kabelsatzList) {
        this.kabelsatzList = kabelsatzList;
    }

    public String getSelectedKs() {
        return selectedKs;
    }

    public void setSelectedKs(String selectedKs) {
        this.selectedKs = selectedKs;
    }

    public boolean isUpdateStaPruefElektr() {
        return updateStaPruefElektr;
    }

    public void setUpdateStaPruefElektr(boolean updateStaPruefElektr) {
        this.updateStaPruefElektr = updateStaPruefElektr;
    }

    public boolean isDeleteStaPruefElektr() {
        return deleteStaPruefElektr;
    }

    public void setDeleteStaPruefElektr(boolean deleteStaPruefElektr) {
        this.deleteStaPruefElektr = deleteStaPruefElektr;
    }

    public boolean isUpdateStaPruefRelais() {
        return updateStaPruefRelais;
    }

    public void setUpdateStaPruefRelais(boolean updateStaPruefRelais) {
        this.updateStaPruefRelais = updateStaPruefRelais;
    }

    public boolean isDeleteStaPruefRelais() {
        return deleteStaPruefRelais;
    }

    public void setDeleteStaPruefRelais(boolean deleteStaPruefRelais) {
        this.deleteStaPruefRelais = deleteStaPruefRelais;
    }

    public boolean isUpdateStaSicherung() {
        return updateStaSicherung;
    }

    public void setUpdateStaSicherung(boolean updateStaSicherung) {
        this.updateStaSicherung = updateStaSicherung;
    }

    public boolean isDeleteStaSicherung() {
        return deleteStaSicherung;
    }

    public void setDeleteStaSicherung(boolean deleteStaSicherung) {
        this.deleteStaSicherung = deleteStaSicherung;
    }

    public boolean isUpdateStaClip() {
        return updateStaClip;
    }

    public void setUpdateStaClip(boolean updateStaClip) {
        this.updateStaClip = updateStaClip;
    }

    public boolean isDeleteStaClip() {
        return deleteStaClip;
    }

    public void setDeleteStaClip(boolean deleteStaClip) {
        this.deleteStaClip = deleteStaClip;
    }

    public boolean isUpdateStaTuelle() {
        return updateStaTuelle;
    }

    public void setUpdateStaTuelle(boolean updateStaTuelle) {
        this.updateStaTuelle = updateStaTuelle;
    }

    public boolean isDeleteStaTuelle() {
        return deleteStaTuelle;
    }

    public void setDeleteStaTuelle(boolean deleteStaTuelle) {
        this.deleteStaTuelle = deleteStaTuelle;
    }

    public boolean isUpdateStaWa() {
        return updateStaWa;
    }

    public void setUpdateStaWa(boolean updateStaWa) {
        this.updateStaWa = updateStaWa;
    }

    public boolean isDeleteStaWa() {
        return deleteStaWa;
    }

    public void setDeleteStaWa(boolean deleteStaWa) {
        this.deleteStaWa = deleteStaWa;
    }

    public boolean isUpdateStaArchiv() {
        return updateStaArchiv;
    }

    public void setUpdateStaArchiv(boolean updateStaArchiv) {
        this.updateStaArchiv = updateStaArchiv;
    }

    public boolean isDeleteStaArchiv() {
        return deleteStaArchiv;
    }

    public void setDeleteStaArchiv(boolean deleteStaArchiv) {
        this.deleteStaArchiv = deleteStaArchiv;
    }

    public boolean isUpdateStaSchraub() {
        return updateStaSchraub;
    }

    public void setUpdateStaSchraub(boolean updateStaSchraub) {
        this.updateStaSchraub = updateStaSchraub;
    }

    public boolean isDeleteStaSchraub() {
        return deleteStaSchraub;
    }

    public void setDeleteStaSchraub(boolean deleteStaSchraub) {
        this.deleteStaSchraub = deleteStaSchraub;
    }

    public boolean isUpdateStaSchaumen() {
        return updateStaSchaumen;
    }

    public void setUpdateStaSchaumen(boolean updateStaSchaumen) {
        this.updateStaSchaumen = updateStaSchaumen;
    }

    public boolean isDeleteStaSchaumen() {
        return deleteStaSchaumen;
    }

    public void setDeleteStaSchaumen(boolean deleteStaSchaumen) {
        this.deleteStaSchaumen = deleteStaSchaumen;
    }

    public boolean isUpdateStaLoetung() {
        return updateStaLoetung;
    }

    public void setUpdateStaLoetung(boolean updateStaLoetung) {
        this.updateStaLoetung = updateStaLoetung;
    }

    public boolean isDeleteStaLoetung() {
        return deleteStaLoetung;
    }

    public void setDeleteStaLoetung(boolean deleteStaLoetung) {
        this.deleteStaLoetung = deleteStaLoetung;
    }

    public boolean isUpdateStaBandEinlauf() {
        return updateStaBandEinlauf;
    }

    public void setUpdateStaBandEinlauf(boolean updateStaBandEinlauf) {
        this.updateStaBandEinlauf = updateStaBandEinlauf;
    }

    public boolean isDeleteStaBandEinlauf() {
        return deleteStaBandEinlauf;
    }

    public void setDeleteStaBandEinlauf(boolean deleteStaBandEinlauf) {
        this.deleteStaBandEinlauf = deleteStaBandEinlauf;
    }

    public boolean isUpdateStaBandAuslauf() {
        return updateStaBandAuslauf;
    }

    public void setUpdateStaBandAuslauf(boolean updateStaBandAuslauf) {
        this.updateStaBandAuslauf = updateStaBandAuslauf;
    }

    public boolean isDeleteStaBandAuslauf() {
        return deleteStaBandAuslauf;
    }

    public void setDeleteStaBandAuslauf(boolean deleteStaBandAuslauf) {
        this.deleteStaBandAuslauf = deleteStaBandAuslauf;
    }

    public boolean isUpdateStaEsdSchraubBegin() {
        return updateStaEsdSchraubBegin;
    }

    public void setUpdateStaEsdSchraubBegin(boolean updateStaEsdSchraubBegin) {
        this.updateStaEsdSchraubBegin = updateStaEsdSchraubBegin;
    }

    public boolean isDeleteStaEsdSchraubBegin() {
        return deleteStaEsdSchraubBegin;
    }

    public void setDeleteStaEsdSchraubBegin(boolean deleteStaEsdSchraubBegin) {
        this.deleteStaEsdSchraubBegin = deleteStaEsdSchraubBegin;
    }

    public boolean isUpdateStaEsdSchraub() {
        return updateStaEsdSchraub;
    }

    public void setUpdateStaEsdSchraub(boolean updateStaEsdSchraub) {
        this.updateStaEsdSchraub = updateStaEsdSchraub;
    }

    public boolean isDeleteStaEsdSchraub() {
        return deleteStaEsdSchraub;
    }

    public void setDeleteStaEsdSchraub(boolean deleteStaEsdSchraub) {
        this.deleteStaEsdSchraub = deleteStaEsdSchraub;
    }
}
