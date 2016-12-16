package com.leoni.viewModel;

import com.leoni.data.manager.WorkplaceManager;
import com.leoni.data.models.Workplace;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:46
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class WorkplaceViewModel {
    @WireVariable
    private WorkplaceManager workplaceManager;

    private List<Workplace> workplaceList = new ArrayList<Workplace>();
    private Workplace selectedWorkplace;
    private String name;
    private String bandName;
    private Integer step;
    private String side;
    private Workplace workplaceToDelete;
    private String bandNameSearch="";
    private String operation;
    private String alias;

    private List<String> bandNameList = Arrays.asList(
            " ",
            "F991RL",
            "F981LL",
            "F991LL"
    );

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        workplaceList=workplaceManager.getAll();
    }

    @Command
    @NotifyChange ({"workplaceList","selectedWorkplace"})
    public void search()
    {    if (bandNameSearch.equals("")){
        workplaceList=workplaceManager.getAll();}
    else workplaceList = workplaceManager.findBy(bandNameSearch);
        selectedWorkplace = null;
    }

    @Command
    @NotifyChange({"workplaceList"})
    public void deleteWorkplace(@BindingParam("workplace") Workplace myWorkplace)
    {
        workplaceToDelete=myWorkplace;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            workplaceManager.delete(workplaceToDelete);
                            workplaceList = workplaceManager.getAll();
                            BindUtils.postNotifyChange(null, null, WorkplaceViewModel.this, "workplaceList");
                        }
                    }
                }
        );


    }

    @Command
    @NotifyChange({"selectedWorkplace"})
    public void saveWorkplace(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")Workplace editedWorkplace) {
        workplaceManager.saveEditedWorkplace(editedWorkplace);
        workplaceList=workplaceManager.getAll();
        selectedWorkplace = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"workplaceList","name","bandName","step","side","operation","alias"})
    public void generateNewWorkplace() {
        if(name!=null&&bandName!=null){
        workplaceManager.addNewWorkplace(name,bandName,step,side,operation,alias);
        workplaceList=workplaceManager.getAll();
        name=null;
        bandName=null;
        step=null;
        side=null;
        operation=null;
        alias=null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    } else{Messagebox.show("Nezadali ste name alebo band name", "Error", Messagebox.OK, Messagebox.ERROR);}

    }

    public List<Workplace> getWorkplaceList() {
        return workplaceList;
    }

    public void setWorkplaceList(List<Workplace> workplaceList) {
        this.workplaceList = workplaceList;
    }

    public Workplace getSelectedWorkplace() {
        return selectedWorkplace;
    }

    public void setSelectedWorkplace(Workplace selectedWorkplace) {
        this.selectedWorkplace = selectedWorkplace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Workplace getWorkplaceToDelete() {
        return workplaceToDelete;
    }

    public void setWorkplaceToDelete(Workplace workplaceToDelete) {
        this.workplaceToDelete = workplaceToDelete;
    }

    public String getBandNameSearch() {
        return bandNameSearch;
    }

    public void setBandNameSearch(String bandNameSearch) {
        this.bandNameSearch = bandNameSearch;
    }

    public List<String> getBandNameList() {
        return bandNameList;
    }

    public void setBandNameList(List<String> bandNameList) {
        this.bandNameList = bandNameList;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
