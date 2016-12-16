package com.leoni.jcc.component;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Like;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListSubModel;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 13.12.2012
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */

@Service ("modulAutoCompleteModel")
@Scope ("prototype")
public class ModulAutoCompleteModel extends AbstractListModel<Moduls> implements ListSubModel<Moduls>, ListModel<Moduls>
    {
    @Autowired
    ModulsManager modulsManager;
    Like like;

    List<CriteriaAppender> criteriaAppenderList = new ArrayList<CriteriaAppender>();
    private String column;
    private String value = "";

    public ModulAutoCompleteModel()
        {
        like = new Like(column, value);
        criteriaAppenderList.add(like);
        }

    public int getSize()
        {
        return 0;
        }

    public void addListDataListener(ListDataListener listDataListener)
        {
        super.addListDataListener(listDataListener);
        }

    public void removeListDataListener(ListDataListener listDataListener)
        {
        super.removeListDataListener(listDataListener);
        }

    public Moduls getElementAt(int i)
        {
        return null;
        }

    public void reload()
        {
        fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, 0);
        }

    public ListModel<Moduls> getSubModel(Object o, int i)
        {
        like.setValue("%" + o.toString() + "%");
        like.setColumn(column);
        List<Moduls> modulsList = modulsManager.find(criteriaAppenderList, 0, 10, column, true);
//        List<String> sachList = new ArrayList<String>();
//        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(Moduls.class, column);
//        sachList.clear();

//        for (Moduls m : modulsList)
//            {
//            String s = "";
//            try
//                {
//                s = (String) pd.getReadMethod().invoke(m);
//                }
//            catch (IllegalAccessException e)
//                {
//                e.printStackTrace();
//                }
//            catch (InvocationTargetException e)
//                {
//                e.printStackTrace();
//                }
//            sachList.add(s);
//            }
        return new SimpleListModel<Moduls>(modulsList);
        }

    public String getColumn()
        {
        return column;
        }

    public void setColumn(String column)
        {
        this.column = column;
        }

    }
