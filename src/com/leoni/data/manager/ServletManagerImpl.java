package com.leoni.data.manager;

import com.leoni.data.models.DmeVarianteName;
import com.leoni.data.models.GrundVarianteModule;
import com.leoni.data.models.Lpab64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.8.2014
 * Time: 9:03
 * To change this template use File | Settings | File Templates.
 */
@Service("servletManager")
public class ServletManagerImpl implements ServletManager{

    @Autowired
    @WireVariable
    private Lpab64Manager lpab64Manager;

    @Autowired
    @WireVariable
    private ModulsManager modulsManager;

    @Autowired
    @WireVariable
    private GrundVarianteModuleManager grundVarianteModuleManager;

    @Autowired
    @WireVariable
    private DmeVarianteNameManager dmeVarianteNameManager;


    public byte[] getDmeSwImg(String prodNr, String kabelsatzKz) {
        List<Lpab64> modulesInProdNr;
        Set<Integer> modulsIdSet = new HashSet<Integer>();
        modulesInProdNr = lpab64Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz);
        Integer modulsId;
        BufferedImage bImageFromConvertSw = null;
        Integer wertigkeitDmeSw = 0;
        DmeVarianteName dvnSw = null;
        for(Lpab64 item : modulesInProdNr){
            if(!modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).isEmpty()){
                modulsId = modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).get(0).getId();
                modulsIdSet.add(modulsId);
                GrundVarianteModule gvm = null;
                if(!grundVarianteModuleManager.findByModulsId(modulsId).isEmpty()){
                    gvm = grundVarianteModuleManager.findByModulsId(modulsId).get(0);
                    System.out.println("najdeny gvm: id " + modulsId);
                };

                if (gvm != null){
                    wertigkeitDmeSw = wertigkeitDmeSw + gvm.getWertigkeitDmeSw();
                    System.out.println("pripocitanavm " + gvm.getWertigkeitDmeSw());
                }
            }
        }
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa    "+wertigkeitDmeSw);
        if (wertigkeitDmeSw>0)dvnSw = dmeVarianteNameManager.findBySumWertigkeit(wertigkeitDmeSw).get(0);
        byte[] byteSw = null;
        if(dvnSw!=null){
            byteSw = dvnSw.getBild();
            }
        return byteSw;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte[] getDmeGrImg(String prodNr, String kabelsatzKz) {
        List<Lpab64> modulesInProdNr;
        Set<Integer> modulsIdSet = new HashSet<Integer>();
        modulesInProdNr = lpab64Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz);
        Integer modulsId;
        Integer wertigkeitDmeGr = 0;
        DmeVarianteName dvnGr = null;
        for(Lpab64 item : modulesInProdNr){
            if(!modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).isEmpty()){
                modulsId = modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).get(0).getId();
                modulsIdSet.add(modulsId);
                GrundVarianteModule gvm = null;
                if(!grundVarianteModuleManager.findByModulsId(modulsId).isEmpty()){
                    gvm = grundVarianteModuleManager.findByModulsId(modulsId).get(0);
                    System.out.println("najdeny gvm: id " + modulsId);
                };

                if (gvm != null){
                    wertigkeitDmeGr = wertigkeitDmeGr + gvm.getWertigkeitDmeGr();
                    System.out.println("pripocitanavm " + gvm.getWertigkeitDmeGr());
                }
            }
        }
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa    "+wertigkeitDmeSw);
        if (wertigkeitDmeGr>0)dvnGr = dmeVarianteNameManager.findBySumWertigkeit(wertigkeitDmeGr).get(0);
        byte[] byteGr = null;
        if(dvnGr!=null){
            byteGr = dvnGr.getBild();
        }
        return byteGr;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte[] getDmeGrImg(List<String> modulesInProdNr) {
        Set<Integer> modulsIdSet = new HashSet<Integer>();
        Integer modulsId;
        Integer wertigkeitDmeGr = 0;
        DmeVarianteName dvnGr = null;
        for (String item : modulesInProdNr){
            if(!modulsManager.findBySachNrLieferant(item.trim()).isEmpty()){
                modulsId = modulsManager.findBySachNrLieferant(item.trim()).get(0).getId();
                modulsIdSet.add(modulsId);
                GrundVarianteModule gvm = null;
                if(!grundVarianteModuleManager.findByModulsId(modulsId).isEmpty()){
                    gvm = grundVarianteModuleManager.findByModulsId(modulsId).get(0);
                    System.out.println("najdeny gvm: id " + modulsId);
                };

                if (gvm != null){
                    wertigkeitDmeGr = wertigkeitDmeGr + gvm.getWertigkeitDmeGr();
                    System.out.println("pripocitanavm " + gvm.getWertigkeitDmeGr());
                }
            }
        }

        if (wertigkeitDmeGr>0)dvnGr = dmeVarianteNameManager.findBySumWertigkeit(wertigkeitDmeGr).get(0);
        byte[] byteGr = null;
        if(dvnGr!=null){
            byteGr = dvnGr.getBild();
        }
        return byteGr;
    }

    public byte[] getDmeSwImg(List<String> modulesInProdNr) {
        Set<Integer> modulsIdSet = new HashSet<Integer>();
        Integer modulsId;
        Integer wertigkeitDmeSw = 0;
        DmeVarianteName dvnSw = null;
        for (String item : modulesInProdNr){
            if(!modulsManager.findBySachNrLieferant(item.trim()).isEmpty()){
                modulsId = modulsManager.findBySachNrLieferant(item.trim()).get(0).getId();
                modulsIdSet.add(modulsId);
                GrundVarianteModule gvm = null;
                if(!grundVarianteModuleManager.findByModulsId(modulsId).isEmpty()){
                    gvm = grundVarianteModuleManager.findByModulsId(modulsId).get(0);
                    System.out.println("najdeny gvm: id " + modulsId);
                };

                if (gvm != null){
                    wertigkeitDmeSw = wertigkeitDmeSw + gvm.getWertigkeitDmeSw();
                    System.out.println("pripocitanavm " + gvm.getWertigkeitDmeGr());
                }
            }
        }

        if (wertigkeitDmeSw>0)dvnSw = dmeVarianteNameManager.findBySumWertigkeit(wertigkeitDmeSw).get(0);
        byte[] byteSw = null;
        if(dvnSw!=null){
            byteSw = dvnSw.getBild();
        }
        return byteSw;
    }
}
