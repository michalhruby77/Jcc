package com.leoni.data.manager;

import com.leoni.data.models.Harness;
import com.leoni.data.models.SettingsStorage;
import org.zkoss.zul.Textbox;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 15.10.2015
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */
public interface SettingsStorageManager extends GenericManager<SettingsStorage>{
    public List<SettingsStorage> findAbrufControl();
    public void insertAbrufControl(List<Textbox> prodnrList, List<Textbox> descList);
    public void saveRLbrettsOrder(List<Harness> harnessList);
    public List<SettingsStorage> findRLbrettsorder();
}
