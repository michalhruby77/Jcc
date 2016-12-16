package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Harness;
import com.leoni.data.models.SettingsStorage;
import org.springframework.stereotype.Service;
import org.zkoss.zul.Textbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 15.10.2015
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
@Service("settingsStorageManager")
public class SettingsStorageManagerImpl  extends GenericManagerImpl<SettingsStorage> implements SettingsStorageManager{


    @Override
    public void insertAbrufControl(List<Textbox> prodnrList, List<Textbox> descList) {
        List<SettingsStorage> settingsStorageList = findAbrufControl();
        for (int i = 0; i < settingsStorageList.size(); i++){
            settingsStorageList.get(i).setValue(prodnrList.get(i).getValue());
            settingsStorageList.get(i).setChangeDate(new Date());
            settingsStorageList.get(i).setDescription(descList.get(i).getValue());
            saveOrUpdate(settingsStorageList.get(i));
        }
    }

    @Override
    public List<SettingsStorage> findAbrufControl() {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("parameter","abrufControl"));
        List<SettingsStorage> ssList = find(caList);
        Collections.sort(ssList);
        return ssList;
    }


    @Override
    public void saveRLbrettsOrder(List<Harness> harnessList) {
        List<SettingsStorage> settingsStorageList = findRLbrettsorder();
        for (int i = 0; i < harnessList.size(); i++){
           SettingsStorage ss = settingsStorageList.get(i);
           ss.setValue(i+","+harnessList.get(i).getBretttype().trim()+
                   ","+harnessList.get(i).getBrettid().trim());
           saveOrUpdate(ss);
        }
    }

    @Override
    public List<SettingsStorage> findRLbrettsorder() {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("parameter","bandRL"));
        List<SettingsStorage> ssList = find(caList);
        Collections.sort(ssList);
        return ssList;
    }

}
