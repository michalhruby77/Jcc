package com.leoni.data.manager;

import com.leoni.data.criterion.*;
import com.leoni.data.models.Lpab62;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.4.2014
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
@Service("lpab62Manager")
public class Lpab62ManagerImpl extends GenericManagerImpl<Lpab62> implements Lpab62Manager{

    public Lpab62 deleteBegin(Lpab62 harnessToDeleteBeginEsdStatus) {
        //NASTAVIT BEGIN ESD NA NULL
        harnessToDeleteBeginEsdStatus.setStaEsdSchraubBegin(null);
        return saveOrUpdate(harnessToDeleteBeginEsdStatus);
    }

    public List<Lpab62> findByProdnrAndKabelsatz(String prodNr, String kabelsatz) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("prodNr", prodNr));
        caList.add(new Equal("kabelsatzKz",kabelsatz));
        return find(caList);
    }

    public List<Lpab62> findByProdnrAndCustomer(String prodNr, String customerNumber) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("prodNr", prodNr));
        caList.add(new Equal("kundenNr",customerNumber));
        return find(caList);
    }

    public List<Integer> getPorscheDates() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String liefFrom = dateFormat.format(date);
        return (List<Integer>) getHibernateTemplate().find("select distinct lieferDatum from Lpab62 where kundenNr = '323350' and lieferDatum > "+ liefFrom);
        //return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Integer> getOsnabruckDates() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String liefFrom = dateFormat.format(date);
        return (List<Integer>) getHibernateTemplate().find("select distinct lieferDatum from Lpab62 where kundenNr = '323357' and lieferDatum > "+ liefFrom);
    }


    public Long getAllHarnessesForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCountAll("lieferDatum", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getEinlaufForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staBandEinlauf", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getAuslaufForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staBandAuslauf", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getBdoseForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staSchraub", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getFoamForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staSchaumen", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getElektroForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staPruefElektr", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getPRForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staPruefRelais", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getEsdScrewForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staEsdSchraub", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public Long getPhotoWaForDate(String date, String ausfuefhrung, String kskz, String kundenNr, String prodGruppe) {
        return getCount("staWa", date,ausfuefhrung,kskz,kundenNr,prodGruppe);
    }

    public int addHarness(String prodNr, Integer customerNumber, String prodGruppe, String ausfuehrung, Integer lieferDatum, String reihenfNr) {
        int numberOfRecords = 0;
        Lpab62 newCar = new Lpab62();
        newCar.setProdNr(prodNr);
        newCar.setKundenNr(String.valueOf(customerNumber));
        newCar.setProdgruppe(prodGruppe.trim());
        newCar.setAusfuehrung(ausfuehrung.trim());
        newCar.setLieferDatum(lieferDatum);
        newCar.setProdReihenfNr(reihenfNr.trim());
        newCar.setOrderSource("M");
        newCar.setKabelsatzKz("F");
        create(newCar);
        numberOfRecords++;
        newCar.setKabelsatzKz("C");
        create(newCar);
        numberOfRecords++;
        newCar.setAusfuehrung("XL");
        newCar.setKabelsatzKz("T");
        create(newCar);
        numberOfRecords++;
        newCar.setKabelsatzKz("U");
        create(newCar);
        numberOfRecords++;
        if (customerNumber==323350){
        newCar.setKabelsatzKz("E");
        create(newCar);
        numberOfRecords++;
        newCar.setKabelsatzKz("R");
        create(newCar);
        numberOfRecords++;}
        return numberOfRecords;

    }

    public int addHarnessWithSelectedKs(String prodNr, Integer customerNumber, String prodGruppe, String ausfuehrung,
                                        Integer lieferDatum, String reihenfNr, String ksKz) {
        int numberOfRecords = 0;
        Lpab62 newCar = new Lpab62();
        newCar.setProdNr(prodNr);
        newCar.setKundenNr(String.valueOf(customerNumber));
        newCar.setProdgruppe(prodGruppe.trim());
        newCar.setAusfuehrung(ausfuehrung.trim());
        newCar.setLieferDatum(lieferDatum);
        newCar.setProdReihenfNr(reihenfNr.trim());
        newCar.setKabelsatzKz(ksKz.trim());
        newCar.setOrderSource("M");
        create(newCar);
        numberOfRecords++;
        return numberOfRecords;
    }


    public List<Lpab62> getCustomHarnesses() {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("orderSource", "M"));
        return find(caList);
    }

    public List<Lpab62> searchCustomHarnesses(String prodNrSearch, String kabelsatzKzSearch, String kundenNrSearch) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!prodNrSearch.trim().equals("")){caList.add(new Equal("prodNr", prodNrSearch.trim()));}
        if(!kabelsatzKzSearch.equals("")){caList.add(new Equal("kabelsatzKz", kabelsatzKzSearch));}
        if(!kundenNrSearch.equals("")){caList.add(new Equal("kundenNr", kundenNrSearch));}
        caList.add(new Equal("orderSource", "M"));
        return find(caList);
    }
}
