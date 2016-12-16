package com.leoni.viewModel;

import com.leoni.data.models.VersandModul;
import org.zkoss.zul.*;

//import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.3.2015
 * Time: 8:01
 * To change this template use File | Settings | File Templates.
 */
public class VersandModulsRenderer implements ListitemRenderer {
    @Override
    public void render(Listitem listitem, Object o, int i) throws Exception {
        VersandModul vm = (VersandModul) o;
        listitem.setValue(vm);
        if (vm.getStatus()==20) {listitem.setStyle("background-color: #FFFF00");}
        if (vm.getStatus()==30) {listitem.setStyle("background-color: #FF9900");}
        if (vm.getStatus()==40) {listitem.setStyle("background-color: #99FF66");}
        //Label sachNrBestLab = new Label(vm.getSachNrBest());
        //sachNrBestLab.setStyle("font-weight:bold; font-size:16pt");
        Listcell sachNrBestLc = new Listcell(vm.getSachNrBest());
        sachNrBestLc.setStyle("font-weight:bold");
        sachNrBestLc.setParent(listitem);
        new Listcell(vm.getSachNrLieferant()).setParent(listitem);
        new Listcell(String.valueOf(vm.getAuftragNr())).setParent(listitem);
        Listcell piecesLc = new Listcell(String.valueOf(vm.getPieces()));
        piecesLc.setStyle("font-weight:bold");
        piecesLc.setParent(listitem);
        new Listcell(String.valueOf(vm.getCount())).setParent(listitem);
        new Listcell(vm.getTime()).setParent(listitem);
        new Listcell(vm.getLadungName()).setParent(listitem);
        new Listcell(vm.getLadungPcs()).setParent(listitem);
        new Listcell(vm.getPaletteName()).setParent(listitem);
        new Listcell(vm.getPalettePcs()).setParent(listitem);
        new Listcell(vm.getDeckelName()).setParent(listitem);
        new Listcell(vm.getDeckelPcs()).setParent(listitem);
        new Listcell(String.valueOf(vm.getMj())).setParent(listitem);
        Checkbox cBox = new Checkbox();
        cBox.setChecked(vm.getScan());
        cBox.setDisabled(true);
        Listcell lc = new Listcell();
        lc.appendChild(cBox);
        lc.setParent(listitem);
        new Listcell(vm.getPersnr()).setParent(listitem);
        new Listcell(String.valueOf(vm.getStatus())).setParent(listitem);
    }
}
