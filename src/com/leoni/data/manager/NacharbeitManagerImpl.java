package com.leoni.data.manager;

import com.leoni.data.criterion.Between;
import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Or;
import com.leoni.data.models.Nacharbeit;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.1.2015
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
@Service("nacharbeitManager")
public class NacharbeitManagerImpl  extends GenericManagerImpl<Nacharbeit> implements NacharbeitManager{
    @Override
    public List<Nacharbeit> findByProdNrAndKs(String prodNr, String kabelSatz, String modus) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("prodNr", prodNr.trim()));
        caList.add(new Equal("kabelsatzKz", kabelSatz.trim()));
        if (!modus.trim().equals("*"))caList.add(new Equal("mode", modus.trim().toUpperCase()));
        else caList.add(new Or(new Equal("mode","NACHARBEIT"),new Equal("mode","SPERRE")));
        return find(caList);
    }

    @Override
    public List<Nacharbeit> findByDate(String dateFrom, String dateTo, String kabelSatz, String modus) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Between("logdate", dateFrom.trim(),dateTo.trim()));
        caList.add(new Equal("kabelsatzKz", kabelSatz.trim()));
        if (!modus.trim().equals("*"))caList.add(new Equal("mode", modus.trim().toUpperCase()));
        else caList.add(new Or(new Equal("mode","NACHARBEIT"),new Equal("mode","SPERRE")));
        return find(caList);
    }

    @Override
    public File exportToFile(List<Nacharbeit> nacharbeitList) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Prod. Nr.; Kabelsatz;Grupa;Ausfuehrung;Datum;Cas;Tester;Kod chyby; Popis chyby; Cas opravy \n");
            for (Nacharbeit item : nacharbeitList){
                if(!item.getFehlKod1().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod1()+
                        ";"+item.getFehlerText1()+";"+item.getFehlTime1()+"\n");
                if(!item.getFehlKod2().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod2()+
                        ";"+item.getFehlerText2()+";"+item.getFehlTime2()+"\n");
                if(!item.getFehlKod3().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod3()+
                        ";"+item.getFehlerText3()+";"+item.getFehlTime3()+"\n");
                if(!item.getFehlKod4().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod4()+
                        ";"+item.getFehlerText4()+";"+item.getFehlTime4()+"\n");
                if(!item.getFehlKod5().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod5()+
                        ";"+item.getFehlerText5()+";"+item.getFehlTime5()+"\n");
                if(!item.getFehlKod6().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod6()+
                        ";"+item.getFehlerText6()+";"+item.getFehlTime6()+"\n");
                if(!item.getFehlKod7().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod7()+
                        ";"+item.getFehlerText7()+";"+item.getFehlTime7()+"\n");
                if(!item.getFehlKod8().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod8()+
                        ";"+item.getFehlerText8()+";"+item.getFehlTime8()+"\n");
                if(!item.getFehlKod9().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod9()+
                        ";"+item.getFehlerText9()+";"+item.getFehlTime9()+"\n");
                if(!item.getFehlKod10().trim().equals(""))bw.write(item.getProdNr()+";"+item.getKabelsatzKz()+";"+
                        item.getProdGruppe()+";"+item.getAusfuhrung()+";"+item.getLogdate()+
                        ";"+item.getLogtime()+";"+item.getTesterId()+";"+item.getFehlKod10()+
                        ";"+item.getFehlerText10()+";"+item.getFehlTime10()+"\n");

            }
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public List<Nacharbeit> getAll610() {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("fehlKod1", "110"));
        caList.add(new Equal("tableId", "NACHARBEIT"));
        ;caList.add(new Between("logdate", "20140301","20150129"));
        return find(caList);
    }

    @Override
    public List<Nacharbeit> findByDateStatusAndSperre(String dateFrom, String dateTo, String timeFrom, String timeTo, String ksKz) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Between("logdate", dateFrom.trim(),dateTo.trim()));
        caList.add(new Between("logtime", timeFrom.trim(),timeTo.trim()));
        caList.add(new Equal("kabelsatzKz", ksKz));
        caList.add(new Equal("mode", "SPERRE"));
        return find(caList);
    }
}
