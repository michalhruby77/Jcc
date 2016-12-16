package com.leoni.data.manager;

import com.leoni.data.criterion.*;
import com.leoni.data.models.Harness;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SettingsStorage;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
//import com.leoni.data.models.Harness;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.12.2013
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
@Service ("bandManager")
public class BandManagerImpl extends GenericManagerImpl<Harness> implements BandManager{

    @Autowired
    SettingsStorageManager settingsStorageManager;

    public List<Harness> findByBrettType(String brettType) {
        //CriteriaAppender[] criteriaAppenders = null;
        if (brettType.equals("F991RL")) {
        CriteriaAppender[] criteriaAppenders = {new Or(new Equal("bretttype", brettType),new Equal("bretttype", "F99FRL"))};
        return find(Arrays.asList(criteriaAppenders));
        }
        else if (brettType.equals("F981RL")) {
            CriteriaAppender[] criteriaAppenders = {new Or(new Equal("bretttype", brettType),new Equal("bretttype", "F98FRL"))};
            return find(Arrays.asList(criteriaAppenders));
        }
        else return null;
    }

    @Override
    public List<Harness> findByBrettType2(String brettType) {
        CriteriaAppender[] criteriaAppenders = {new Equal("bretttype", brettType)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    public List<Harness> findEmptyByBrettType(String brettType) {
        CriteriaAppender[] criteriaAppenders = {new Equal("bretttype", brettType), new IsNull("prod_nr")};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<Harness> findByProdNr(String prodNr) {
        CriteriaAppender[] criteriaAppenders = {new Equal("prod_nr", prodNr)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<Harness> findByBrettTypeRL() {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Or(new Or(new Equal("bretttype", "F991RL"),new Equal("bretttype", "F981RL")),
                new Or(new Equal("bretttype", "F99FRL"),new Equal("bretttype", "F98FRL"))));
        caList.add(new Equal("band_name", "F991RL"));
        caList.add(new NotEqual("prod_nr",""));
        return find(caList);
    }

    public List<Harness> findByBrettTypeRLALL() {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Or(new Or(new Equal("bretttype", "F991RL"),new Equal("bretttype", "F981RL")),
                new Or(new Equal("bretttype", "F99FRL"),new Equal("bretttype", "F98FRL"))));
        caList.add(new Equal("band_name", "F991RL"));
        //caList.add(new NotEqual("prod_nr",""));
        return find(caList);
    }


    public List<Harness> disableAll991() {
      List<Harness> tempAll991RL = findByBrettType("F991RL");
      for (Harness item : tempAll991RL){
            item.setLock(true);
            updateHarness(item);
            }
        List<Harness> tempAllRL = findByBrettTypeRL();
        Collections.sort(tempAllRL);
        settingsStorageManager.saveRLbrettsOrder(tempAllRL);
        /*PrintWriter writer = null;
        String path=BandManagerImpl.class.getClassLoader().getResource("times.txt").toString();
        System.out.println(path);
        path = path.replaceAll("file:/", "/");
        System.out.println(path);
        try {
            writer = new PrintWriter(path, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        int i=0;
        for (Harness item : tempAllRL){
           // if(!item.getProd_nr().equals(""))
        writer.println(i+","+item.getBretttype().trim()+","+item.getBrettid().trim());
        i++;
        }
        writer.close();*/
    return tempAllRL;
    }

    public List<Harness> disableAll981() {
        List<Harness> tempAll981RL = findByBrettType("F981RL");
        for (Harness item : tempAll981RL){
            item.setLock(true);
            updateHarness(item);
        }
        List<Harness> tempAllRL = findByBrettTypeRL();
        Collections.sort(tempAllRL);
        settingsStorageManager.saveRLbrettsOrder(tempAllRL);
        /*PrintWriter writer = null;
        String path=BandManagerImpl.class.getClassLoader().getResource("times.txt").toString();
        //System.out.println(path);
        path = path.replaceAll("file:/", "/");
        try {
            writer = new PrintWriter(path, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        int i=0;
        for (Harness item : tempAllRL){
           // if(!item.getProd_nr().equals(""))
            writer.println(i+","+item.getBretttype().trim()+","+item.getBrettid().trim());
        i++;
        }
        writer.close();*/
        return tempAllRL;
    }

    public List<Harness> enableAll991() {
       // List<String> linesList = getLinesList();
        List<Harness> tempAll991RL = findByBrettType("F991RL");
        for (Harness item : tempAll991RL){
            item.setLock(false);
            updateHarness(item);
        }
        List<Harness> tempAllRL = findByBrettTypeRL();
        Collections.sort(tempAllRL);
        for (Harness item : tempAllRL){
            if (item.getBretttype().trim().equals("F991RL") || item.getBretttype().trim().equals("F99FRL"))
            {
            //---------------------------------------

            Harness previousHarness = null;
            Harness nextHarness = null;
            String brettid = item.getBrettid().trim();
            String previousHarnessLine=getPreviousHarnessFromDb("F991RL", brettid);
            String nextHarnessLine=getNextHarnessFromDb("F991RL", brettid);
            String previousBandName="";
            String previousBrettid="";
            String nextBandName="";
            String nextBrettid="";


            if (!previousHarnessLine.equals("")){
                                   String[] words = previousHarnessLine.split(",");
                                   previousBandName=words[0];
                                   previousBrettid=words[1];
                                   CriteriaAppender[] criteriaAppenders = {new Equal("bretttype", previousBandName),new Equal("brettid", previousBrettid)};
                                   previousHarness = find(Arrays.asList(criteriaAppenders)).get(0);}
            if (!nextHarnessLine.equals("")){
                    String[] words2 = nextHarnessLine.split(",");
                    nextBandName=words2[0];
                    nextBrettid=words2[1];
                    CriteriaAppender[] criteriaAppenders = {new Equal("bretttype", nextBandName),new Equal("brettid", nextBrettid)};
                    nextHarness = find(Arrays.asList(criteriaAppenders)).get(0);}
            if(previousHarness!=null&&nextHarness!=null){
                long timeNext = nextHarness.getBusytime().getTime();
                long timePrevious = previousHarness.getBusytime().getTime();
                long difference = timeNext - timePrevious;
                if (difference > 60000) {Date newDate=new Date(/*timeNext - (1 * 120000)*/timePrevious+difference/2);
                                         item.setBusytime(newDate);}
                else System.out.println("ROZDIEL CASOV MEDZI PREDOSLOU A DALSOU KABLOVKOU MENEJ AKO MINUTA??????????????????????????????????????????????");
                }
            else{
               //if(previousHarness!=null){System.out.println("predosla: "+previousHarness.getProd_nr());}
               //if(nextHarness!=null){System.out.println("nasledujucaa: "+nextHarness.getProd_nr());}
               if(previousHarness==null){long time = nextHarness.getBusytime().getTime();
                                         Date newDate=new Date(time - (1 * 120000));
                                         item.setBusytime(newDate);
               }
               if(nextHarness==null){long time = previousHarness.getBusytime().getTime();
                                     Date newDate=new Date(time + (1 * 120000));
                                     item.setBusytime(newDate);

               }
            }
            //item.setLock(false);
            updateHarness(item);}

        //--------------------------------------------
        }

        tempAllRL = findByBrettTypeRL();
        Collections.sort(tempAllRL);
        return tempAllRL;
    }

    public List<Harness> enableAll981() {
       // List<String> linesList = getLinesList();
        List<Harness> tempAll981RL = findByBrettType("F981RL");
        for (Harness item : tempAll981RL){
            item.setLock(false);
            updateHarness(item);
        }
        List<Harness> tempAllRL = findByBrettTypeRL();
        Collections.sort(tempAllRL);
        for (Harness item : tempAllRL){
            if (item.getBretttype().trim().equals("F981RL") || item.getBretttype().trim().equals("F98FRL"))
            {
            //------------------------------
                Harness previousHarness = null;
                Harness nextHarness = null;
                String brettid = item.getBrettid().trim();
                String previousHarnessLine=getPreviousHarnessFromDb("F981RL", brettid);
                String nextHarnessLine=getNextHarnessFromDb("F981RL", brettid);
                String previousBandName="";
                String previousBrettid="";
                String nextBandName="";
                String nextBrettid="";
                if (!previousHarnessLine.equals("")){
                    String[] words = previousHarnessLine.split(",");
                    previousBandName=words[0];
                    previousBrettid=words[1];
                    System.out.println("tato kablovka "+item.getBretttype()+item.getBrettid());
                    System.out.println("pred kablovka "+previousBandName+previousBrettid);
                    CriteriaAppender[] criteriaAppenders = {new Equal("bretttype", previousBandName),new Equal("brettid", previousBrettid)};
                    previousHarness = find(Arrays.asList(criteriaAppenders)).get(0);}

                System.out.println("predosla: "+previousHarness);
                if (!nextHarnessLine.equals("")){
                    String[] words2 = nextHarnessLine.split(",");
                    nextBandName=words2[0];
                    nextBrettid=words2[1];
                    System.out.println("tato kablovka "+item.getBretttype()+item.getBrettid());
                    System.out.println("next kablovka "+nextBandName+nextBrettid);
                    CriteriaAppender[] criteriaAppenders = {new Equal("bretttype", nextBandName),new Equal("brettid", nextBrettid)};
                    nextHarness = find(Arrays.asList(criteriaAppenders)).get(0);}
                System.out.println("nex: "+nextHarness);
                if(previousHarness!=null&&nextHarness!=null){
                    long timeNext = nextHarness.getBusytime().getTime();
                    long timePrevious = previousHarness.getBusytime().getTime();
                    long difference = timeNext - timePrevious;
                    if (difference > 60000) {Date newDate=new Date(/*timeNext - (1 * 60000)*/timePrevious+difference/2);
                        item.setBusytime(newDate);}
                    else System.out.println("ROZDIEL CASOV MEDZI PREDOSLOU A DALSOU KABLOVKOU MENEJ AKO MINUTA??????????????????????????????????????????????");
                }
                else{
                    if(previousHarness==null){long time = nextHarness.getBusytime().getTime();
                        Date newDate=new Date(time - (1 * 60000));
                        item.setBusytime(newDate);
                    }
                    if(nextHarness==null){long time = previousHarness.getBusytime().getTime();
                        Date newDate=new Date(time + (1 * 60000));
                        item.setBusytime(newDate);

                    }
                }
               // item.setLock(false);
                updateHarness(item);}
            //------------------------------
        }

        tempAllRL = findByBrettTypeRL();
        Collections.sort(tempAllRL);
        return tempAllRL;
    }

    /*public List<String> getLinesList(){
        BufferedReader br = null;
        List<String> linesList = new ArrayList<String>();
        try {
            String sCurrentLine;
            String path=BandManagerImpl.class.getClassLoader().getResource("times.txt").toString();
            path = path.replaceAll("file:/", "/");
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                linesList.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return linesList;
    } */

    public List<String> getLinesListDb(){
        List<SettingsStorage> storageList = settingsStorageManager.findRLbrettsorder();
        List<String> linesList = new ArrayList<String>();
        for (SettingsStorage ss : storageList){
            if (ss.getValue() != null && !ss.getValue().trim().equals("")){
                linesList.add(ss.getValue().trim());
            }
        }
        return linesList;
    }

   /* public String getPreviousHarnessFromText(String bretttype, String brettid){
        String brettType1 = null;
        String brettType2 = null;
        if (bretttype.equals("F991RL")){
            brettType1 = "F991RL";
            brettType2 = "F99FRL";
        }

        if (bretttype.equals("F981RL")){
            brettType1 = "F981RL";
            brettType2 = "F98FRL";
        }

        List<String> linesList = getLinesList();
        String previousHarness="";
        for (int i=0;i<linesList.size();i++){
            String[] words = linesList.get(i).split(",");
            if ((words[1].equals(brettType1)||words[1].equals(brettType2))&&words[2].equals(brettid)&&(i!=0)){
                String[] wordsPrevious = linesList.get(i-1).split(",");
                previousHarness=wordsPrevious[1]+","+wordsPrevious[2];
            }
        }
       return previousHarness;
    }

    public String getNextHarnessFromText(String band_name, String brettid){

        String brettType1 = null;
        String brettType2 = null;
        if (band_name.equals("F991RL")){
            brettType1 = "F991RL";
            brettType2 = "F99FRL";
        }

        if (band_name.equals("F981RL")){
            brettType1 = "F981RL";
            brettType2 = "F98FRL";
        }

        List<String> linesList = getLinesList();
        String nextHarness="";
        for (int i=0;i<linesList.size();i++){
            String[] words = linesList.get(i).split(",");
            if ((words[1].equals(brettType1) || (words[1].equals(brettType2)))&&(words[2].equals(brettid))&&(i!=linesList.size()-1)){
                String[] wordsNext = linesList.get(i+1).split(",");
                nextHarness=wordsNext[1]+","+wordsNext[2];
            }
        }
        return nextHarness;
    }      */

    public String getPreviousHarnessFromDb(String bretttype, String brettid){
        String brettType1 = null;
        String brettType2 = null;
        if (bretttype.equals("F991RL")){
            brettType1 = "F991RL";
            brettType2 = "F99FRL";
        }

        if (bretttype.equals("F981RL")){
            brettType1 = "F981RL";
            brettType2 = "F98FRL";
        }

        List<String> linesList = getLinesListDb();
        String previousHarness="";
        for (int i=0;i<linesList.size();i++){
            String[] words = linesList.get(i).split(",");
            if ((words[1].equals(brettType1)||words[1].equals(brettType2))&&words[2].equals(brettid)&&(i!=0)){
                String[] wordsPrevious = linesList.get(i-1).split(",");
                previousHarness=wordsPrevious[1]+","+wordsPrevious[2];
            }
        }
        return previousHarness;
    }

    public String getNextHarnessFromDb(String band_name, String brettid){

        String brettType1 = null;
        String brettType2 = null;
        if (band_name.equals("F991RL")){
            brettType1 = "F991RL";
            brettType2 = "F99FRL";
        }

        if (band_name.equals("F981RL")){
            brettType1 = "F981RL";
            brettType2 = "F98FRL";
        }

        List<String> linesList = getLinesListDb();
        String nextHarness="";
        for (int i=0;i<linesList.size();i++){
            String[] words = linesList.get(i).split(",");
            if ((words[1].equals(brettType1) || (words[1].equals(brettType2)))&&(words[2].equals(brettid))&&(i!=linesList.size()-1)){
                String[] wordsNext = linesList.get(i+1).split(",");
                nextHarness=wordsNext[1]+","+wordsNext[2];
            }
        }
        return nextHarness;
    }

    public boolean isFirstBrettIdOk(String band_nameToEnable){

        List<Harness> tempAllRL;


        List<String> linesList = getLinesListDb();
        String firstBrettIdfromTxt="";
        String firstBrettIdfromList="";

        if(band_nameToEnable.equals("F991RL")){
        //----
            tempAllRL = findByBrettType("F981RL");
            Collections.sort(tempAllRL);

                firstBrettIdfromList = tempAllRL.get(0).getBrettid();

            System.out.println("list "+firstBrettIdfromList);
        //----
        boolean isFirst = true;
        for (int i=0;i<2;i++){
            String[] words = linesList.get(i).split(",");
            if (isFirst&&(words[1].equals("F981RL")||(words[1].equals("F98FRL")))){
                //String[] wordsNext = linesLis.split(",");
                firstBrettIdfromTxt=words[2];
                isFirst = false;
            }
        }
            System.out.println("textak "+firstBrettIdfromTxt);
        }
        else{
            //----
            tempAllRL = findByBrettType("F991RL");
            Collections.sort(tempAllRL);

                firstBrettIdfromList = tempAllRL.get(0).getBrettid();


            System.out.println("list "+firstBrettIdfromList);
            //----
            boolean isFirst = true;
            for (int i=0;i<2;i++){
                String[] words = linesList.get(i).split(",");
                if (isFirst&&((words[1].equals("F991RL"))||(words[1].equals("F99FRL")))){
                    //String[] wordsNext = linesLis.split(",");
                    firstBrettIdfromTxt=words[2];
                    isFirst = false;
                }
            }
            System.out.println("textak "+firstBrettIdfromTxt);
        }
        System.out.println("textaaaaaaaaaaaaaaaaaaaaaaaaak "+firstBrettIdfromTxt+ " ++++  "+firstBrettIdfromList);
        if(firstBrettIdfromList.trim().equals(firstBrettIdfromTxt.trim())) return true;

        else return false;
    }

    public boolean is991enabled(){
        boolean is991enabled = false;
        List<Harness> tempAll991RL = findByBrettType("F991RL");
        for (Harness item : tempAll991RL){
            if (item.getLock()==false) is991enabled = true;
        }
        return is991enabled;
    }

    public boolean is981enabled(){
        boolean is981enabled = false;
        List<Harness> tempAll981RL = findByBrettType("F981RL");
        for (Harness item : tempAll981RL){
            if (item.getLock()==false) is981enabled = true;
        }
        return is981enabled;
    }
    public String getBrettTypeAndIdOnClip(String band_nameToEnable){
        String firstBrettIdfromTxt = "";
        List<String> linesList = getLinesListDb();
        System.out.println(linesList);
        if(band_nameToEnable.equals("F991RL")){
            for (int i=0;i<2;i++){
                String[] words = linesList.get(i).split(",");
                if ((words[1].equals("F981RL"))||(words[1].equals("F98FRL"))){

                    //String[] wordsNext = linesLis.split(",");
                    firstBrettIdfromTxt = "981 id: "+words[2];

                }
            }
        }
        else{
            for (int i=0;i<2;i++){
                String[] words = linesList.get(i).split(",");
                if ((words[1].equals("F991RL"))||(words[1].equals("F99FRL"))){
                    //String[] wordsNext = linesLis.split(",");
                    firstBrettIdfromTxt = "991 id: "+words[2];

                }
            }
        }
    return firstBrettIdfromTxt;
    }

    @Override
    public List<Harness> findByBandName(String bandName) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("band_name", bandName.trim()));
        return find(caList);
    }

    @Override
    public List<Harness> findByBandNameOnBand(String bandName) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("band_name", bandName.trim()));
        caList.add(new IsNotNull("prod_nr"));
        return find(caList);
    }


    public Harness updateHarness(Harness harness) {
        return saveOrUpdate(harness);
    }


}
