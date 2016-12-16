package com.leoni.viewModel;

import com.leoni.jcc.listModel.GenericListModelImpl;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 9.11.2012
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModulsEditorViewModel
    {
    @WireVariable
    private ModulsManager modulsManager;


    @Wire ("#modulsGrid")
    private Grid modulsGrid;
    private List<Moduls> modulsList = new ArrayList<Moduls>();
    private Moduls modulsFilter;
    private GenericListModelImpl myListModel;

    @Init
    public void init(@ContextParam (ContextType.VIEW) Component view)
        {
        Selectors.wireComponents(view, this, false);
//        modulsGrid.addEventListener("onPaging", new EventListener()
//        {
//        @NotifyChange ({"modulsList"})
//        public void onEvent(Event event) throws Exception
//            {
//            Paginal paginal = ((Grid) event.getTarget()).getPaginal();
//            System.out.println("total size: " + paginal.getTotalSize());
//            System.out.println("page increment: " + paginal.getPageIncrement());
//            System.out.println("active page: " + paginal.getActivePage());
//            System.out.println("page count: "+ paginal.getPageCount());
//            System.out.println("page size: " + paginal.getPageSize());
//            System.out.println();
//
//            modulsList = modulsManager.find(modulsFilter, modulsGrid.getPaginal().getPageSize() * modulsGrid.getPaginal().getActivePage(), modulsGrid.getPaginal().getPageSize());
//            paginal.setTotalSize(modulsManager.count(modulsFilter));
//            }
//        });

//        modulsFilter = new Moduls();
//        modulsGrid.getPagingChild().setAutohide(false);
//        modulsGrid.getPaginal().setDetailed(true);
//        modulsList = modulsManager.find(modulsFilter, 0, modulsGrid.getPaginal().getPageSize());
//        modulsGrid.getPaginal().setTotalSize(200);
//        System.out.println(modulsGrid.getPaginal().getTotalSize());

//        modulsGrid.getPaginal().setPageSize(5);
//        modulsGrid.getPagingChild().invalidate();
//        modulsGrid.invalidate();
        }

    @Command
    @NotifyChange ({"modulsList"})
    public void changeFilter()
        {
//        modulsList = modulsManager.find(modulsFilter, 0, modulsGrid.getPaginal().getPageSize());
//        modulsGrid.getPaginal().setTotalSize(modulsManager.count(modulsFilter));
//        modulsGrid.getPaginal().setActivePage(0);
        }

    public List<Moduls> getModulsList()
        {
        return modulsList;
        }

    public void setModulsList(List<Moduls> modulsList)
        {
        this.modulsList = modulsList;
        }

    public ModulsManager getModulsManager()
        {
        return modulsManager;
        }

    public void setModulsManager(ModulsManager modulsManager)
        {
        this.modulsManager = modulsManager;
        }

    public Moduls getModulsFilter()
        {
        return modulsFilter;
        }

    public void setModulsFilter(Moduls modulsFilter)
        {
        this.modulsFilter = modulsFilter;
        }

    }
