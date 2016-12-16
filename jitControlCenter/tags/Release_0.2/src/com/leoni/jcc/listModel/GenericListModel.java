package com.leoni.jcc.listModel;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.ext.Paginal;
import org.zkoss.zul.ext.Selectable;
import org.zkoss.zul.ext.Sortable;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 10.1.2013
 * Time: 10:17
 * To change this template use File | Settings | File Templates.
 */
public interface GenericListModel<E> extends ListModel<E>, Selectable<E>, Sortable<E>
    {
    public Paginal getPaginal();

    public void setPaginal(Paginal paginal);

    public E getElementAt(int i);

    public int getSize();

    public void sort(Comparator comparator, boolean b);

    public String getSortDirection(Comparator comparator);

    public void setFilterProperty(String property, String criterion, String value, boolean reloadData);

    public void setFilterProperty(String property, String criterion, String value);

    public void clearFilter();

    public void reload();
    }
