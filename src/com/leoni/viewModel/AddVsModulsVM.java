package com.leoni.viewModel;

import com.leoni.data.manager.ExcelDocumentManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.VsModulyWrmManager;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.VsModulyWrm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.12.2014
 * Time: 7:49
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddVsModulsVM {

    @WireVariable
    private VsModulyWrmManager vsModulyWrmManager;

    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private ExcelDocumentManager excelDocumentManager;

    private List<CustomRowVsModul> customRowList = new ArrayList<>();
    private String user;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    public void getModulsFromExcel(/*@BindingParam("event") UploadEvent event,*/@BindingParam("rows") Rows rows)
    {
        //System.out.println("pridavam");
        Media media = new AMedia("","","","");
        //Media media = event.getMedia();
        //List<VsModulyWrm> vsModulyWrmList = excelDocumentManager.getVsModulsFromExcel(media);
        //addFilledRows(rows,vsModulyWrmList);
    }

    private void addFilledRows(Rows rows, List<VsModulyWrm> vsModulsList){
        Components.removeAllChildren(rows);
        customRowList.clear();
        for(VsModulyWrm vsModul : vsModulsList){
            //System.out.println(vsModul.getModuls());
            CustomRowVsModul row = new CustomRowVsModul(vsModul.getModuls().getSachNrLieferant(),vsModul.getVodic(),vsModul.getPrierez(),vsModul.getPopis());
            rows.appendChild(row);
            //System.out.println("pridavam vsko");
            customRowList.add(row);
        }
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view)
    {
      List<VsModulyWrm> vsModulyWrmList = new ArrayList<>();
      for (CustomRowVsModul item : customRowList){
          Moduls modul = modulsManager.findBySachNrLieferant(item.getModuls().trim()).get(0);
          VsModulyWrm vsModulyWrm = new VsModulyWrm();
          vsModulyWrm.setModuls(modul);
          vsModulyWrm.setVodic(item.getVodic().trim());
          vsModulyWrm.setPrierez(Double.valueOf(item.gtPrierez()));
          String popis = item.getPopis().trim();
          vsModulyWrm.setPopis(popis);
          String id_vsString="";
          Integer id_vs = null;
          if(popis!=null&&popis.startsWith("Y")){
              id_vsString="10"+popis.substring(1,4);
          }
          if(popis!=null&&popis.startsWith("YW")){
              id_vsString="20"+popis.substring(2,5);
          }
          if(popis!=null){id_vs= Integer.valueOf(id_vsString);}
          //vsModulyWrm.setId_vs(id_vs);
          vsModulyWrmList.add(vsModulyWrm);
      }
      //vsModulyWrmManager.addVsModulList(vsModulyWrmList,user);
      view.detach();
    }
}
