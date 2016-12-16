package com.leoni.jcc.listModel;

import com.leoni.data.criterion.BasicCriteriaAppender;
import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.CriteriaAppenderFactory;
import com.leoni.data.manager.GenericManager;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Paginal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 26.11.2012
 * Time: 8:13
 * To change this template use File | Settings | File Templates.
 */

@Scope ("request")
public class GenericListModelImpl<E> extends AbstractListModel<E> implements GenericListModel<E>
    {
//    @Autowired
//    private ModulsManager modulsManager;

    protected GenericManager genericManager;

    private List<CriteriaAppender> criteriaAppenderList = new ArrayList<CriteriaAppender>();
    private Paginal paginal = null;
    private String orderColumn = "";
    private boolean orderDirection = true;

    private List<E> cache;

    public GenericListModelImpl(GenericManager genericManager)
        {
        super();
        this.genericManager = genericManager;
        }

    public Paginal getPaginal()
        {
        return paginal;
        }

    public void setPaginal(Paginal paginal)
        {
        this.paginal = paginal;

        paginal.addEventListener("onPaging", new EventListener<Event>()
        {
        public void onEvent(Event event) throws Exception
            {
            loadData();
            fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
            }
        });
        }

    @SuppressWarnings("unchecked")
    protected void loadData()
        {
        if (paginal == null)
            {
            cache = genericManager.find(criteriaAppenderList);
            }
        else if ("".equals(orderColumn))
            {
            cache = genericManager.find(criteriaAppenderList, paginal.getActivePage() * paginal.getPageSize(), paginal.getPageSize());
            }
        else
            {
            cache = genericManager.find(criteriaAppenderList, paginal.getActivePage() * paginal.getPageSize(), paginal.getPageSize(), orderColumn, orderDirection);
            }

        if (paginal != null)
            {
            paginal.setTotalSize(genericManager.count(criteriaAppenderList));
            }
        }

    public E getElementAt(int i)
        {
        return cache.get(i);
        }

    public int getSize()
        {
        if (cache == null || cache.size() == 0)
            {
            loadData();
            }
        return cache.size();
        }

    public void sort(Comparator comparator, boolean b)
        {
        if (comparator instanceof FieldComparator)
            {
            orderColumn = ((FieldComparator) comparator).getRawOrderBy();
            orderDirection = b;
            loadData();
            fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
            }
        }

    public String getSortDirection(Comparator comparator)
        {
        return "".equals(orderColumn) ? "natural" : orderDirection ? "ascending" : "descending";
        }

    /**
     * Change property to given criterion and value and reload data if it is requested.
     * @param property - Property for change.
     * @param criterion - New Crioterion.
     * @param value - New value.
     * @param reloadData - If true reload data from database.
     */

    public void setFilterProperty(String property, String criterion, String value, boolean reloadData)
        {
        boolean containsProperty = false;

        // Update CriteriaAppender if it is in List
        for (CriteriaAppender c : criteriaAppenderList)
            {
            if (c instanceof BasicCriteriaAppender)
                {
                BasicCriteriaAppender bca = (BasicCriteriaAppender) c;
                if (bca.getColumn().equals(property))
                    {
                    if (!"".equals(value))
                        {
                        bca.setValue(value);
                        }
                    else
                        {
                        criteriaAppenderList.remove(c);
                        }
                    containsProperty = true;
                    break;
                    }
                }
            }

        // Or append new CriteriaAppender
        if (!containsProperty && value != null && !"".equals(value))
            {
            criteriaAppenderList.add(CriteriaAppenderFactory.getCriteriaAppender(property, criterion, value));
            }
        if (reloadData)
            {
            loadData();
            fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
            }
        }

    /**
     * Change property to given criterion and value and reload data.
     *
     * @param property - Property for change.
     * @param criterion - New Crioterion.
     * @param value - New value.
     */

    public void setFilterProperty(String property, String criterion, String value)
        {
        setFilterProperty(property, criterion, value, true);
        }

    public void clearFilter()
        {
        criteriaAppenderList.clear();
        loadData();
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
        }

    public void reload()
        {
        loadData();
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
        }
    }
