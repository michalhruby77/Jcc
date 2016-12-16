package com.leoni.controllers.Servlet;


import com.leoni.data.manager.*;
import com.leoni.data.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.8.2014
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ServletGetData {
    @Autowired
    private ServletManager servletManager;

    @Autowired
    private Lpab62Manager lpab62Manager;

    @Autowired
    private Lpab64Manager lpab64Manager;

    @Autowired
    private ModulsManager modulsManager;

    @Autowired
    private VariantManager variantManager;

    @Autowired
    private ProdNrLogManager prodNrLogManager;


    @RequestMapping(value = "/getDmeSwImgFromProdNr", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getDmeSwImg(@RequestParam("prodNr") String prodNr, @RequestParam("ksKz") String ksKz) {
        return servletManager.getDmeSwImg(prodNr,ksKz);
    }

    @RequestMapping(value = "/getDmeGrImgFromProdNr", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getDmeGrImg(@RequestParam("prodNr") String prodNr, @RequestParam("ksKz") String ksKz) {
        return servletManager.getDmeGrImg(prodNr,ksKz);
    }

    @RequestMapping(value = "/getDmeSwImgFromModuls", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getDmeSwImg(@RequestParam("moduls") String moduls) {
        List<String> splittedModuls = Arrays.asList(moduls.split(" "));
        return servletManager.getDmeSwImg(splittedModuls);
    }

    @RequestMapping(value = "/getDmeGrImgFromModuls", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getDmeGrImg(@RequestParam("moduls") String moduls) {
        List<String> splittedModuls = Arrays.asList(moduls.split(" "));
        return servletManager.getDmeGrImg(splittedModuls);
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String sendMessage() {

        return "data:Testing 1,2,3 ";
    }

    @RequestMapping(value = "/getVariant")
    @ResponseBody
    public String sendVariants(@RequestParam("prodNr") String prodNr, @RequestParam("ksKz") String ksKz,  @RequestParam("workplace") String workplace) {
        List<Lpab64>  modulesInProdNr = lpab64Manager.findByProdnrAndKabelsatz(prodNr,ksKz);
        if (modulesInProdNr.isEmpty()) return "noProdnrFound";
        Set<Integer> modulsIdSet = new HashSet<Integer>();
        int modulsId;
        for(Lpab64 item : modulesInProdNr){
            if(!modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).isEmpty()){
                modulsId = modulsManager.findBySachNrLieferant(item.getSachNrLieferant().trim()).get(0).getId();
                modulsIdSet.add(modulsId);
                };
        }
        List<Variant> variantList = variantManager.getSingleListVariantFromMap(modulsIdSet,workplace.trim());
        String variantsString = "";
        System.out.println(variantList);
        for(Variant variant : variantList){
        variantsString = variant.getName().trim() + "&&" + variant.getDescription().trim() + "&&"
                + variant.getScanString().trim() + "&&" + variant.getScanRequired() + "||";}
        if (variantsString.trim().equals(""))variantsString = "noVariantFound";
        return variantsString;
    }

    @RequestMapping(value = "/get981or991")
    @ResponseBody
    public String send981or991(@RequestParam("prodNr") String prodNr, @RequestParam("ksKz") String ksKz) {
        if(!(lpab62Manager.findByProdnrAndKabelsatz(prodNr.trim(), ksKz.trim())).isEmpty()){

           String prodGroup = "";
           String grundModul = "";
           List<Lpab64> modulsList = lpab64Manager.findByProdnrAndKabelsatz(prodNr,ksKz);
               for(Lpab64 item : modulsList){
                   if(item.getGrundmodul_kz().trim().equals("J"))grundModul = item.getSachNrBest().trim();
               }
           if(!grundModul.trim().equals("")){
               if(grundModul.startsWith("991")||grundModul.startsWith("9P3")||grundModul.startsWith("9P4")) prodGroup="991";
               else if(grundModul.startsWith("981")||grundModul.startsWith("9P9")||grundModul.startsWith("9P0")) prodGroup="981";
               else prodGroup = "noGroup";
               System.out.println(prodGroup);
               return prodGroup;}
           else return "noGrund";
        }
        else return "noProdNr";
    }

    @RequestMapping(value = "/setAntennaStatus")
    @ResponseBody
    public String setAntennaStatus(@RequestParam("prodNr") String prodNr, @RequestParam("ksKz") String ksKz) {
        String dateStr = "";
        if(!lpab62Manager.findByProdnrAndKabelsatz(prodNr.trim(), ksKz.trim()).isEmpty()){
            Lpab62  lpab62 = lpab62Manager.findByProdnrAndKabelsatz(prodNr.trim(), ksKz.trim()).get(0);
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = new Date();
            dateStr = df.format(date);
            lpab62.setStaAntenna(dateStr);
            lpab62Manager.saveOrUpdate(lpab62);
            prodNrLogManager.saveProdNrLog(lpab62.getProdNr(),lpab62.getKabelsatzKz(),"jcc","00000","ANTENNA SET","ANTENNA SET");
        }
         return dateStr;
    }

}
