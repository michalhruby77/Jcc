package com.leoni.viewModel;

import com.leoni.data.manager.CustomerManager;
import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.manager.LwadrKennManager;
import com.leoni.data.models.Customer;
import com.leoni.data.models.LwadrKenn;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.9.2014
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@PropertySource("classpath:jcc.properties")
public class TskGeneratorVM {

    @WireVariable
    private CustomerManager customerManager;

    @WireVariable
    private LwadrKennManager lwadrKennManager;

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    protected Properties adminProps;

    private String prodNr;
    private String selectedKs;
    private Set<String> kabelsatzList;
    private String result;
    private Customer selectedCustomer;
    private List<Customer> customerList;
    private String selectedKs2;
    private String[] groupList;
    private String selectedGroup;
    private List<Integer> prodDateList;
    private Integer selectedProdDate;
    private boolean generateProdNrVis = true;
    private boolean generateBatchVis = false;
    private String servletPrefix;

    @AfterCompose
    public void doAfterCompose()
    {
        kabelsatzList = new HashSet<String>();
        for (LwadrKenn item : lwadrKennManager.getAll()){
            kabelsatzList.add(item.getKabelsatzKz().trim());
        }
        servletPrefix = adminProps.getProperty("dataGenerator.Tsk");
    }

    @Command
    @NotifyChange({"kabelsatzList","generateProdNrVis","generateBatchVis"})
    public void generateProdNr(){
        generateProdNrVis = true;
        generateBatchVis = false;
    }

    @Command
    @NotifyChange({"customerList","groupList","kabelsatzList","prodDateList","generateProdNrVis","generateBatchVis"})
    public void generateBatch(){
           generateProdNrVis = false;
           generateBatchVis = true;
           customerList = customerManager.getAll();
           groupList = new String[]{"9X1"};
           //kabelsatzList = lwadrKennManager.getAll();

    }

    @Command
    @NotifyChange({"customerList","groupList","kabelsatzList","prodDateList","generateProdNrVis","generateBatchVis",
    "prodNr", "selectedKs", "selectedKs2", "selectedCustomer", "selectedGroup", "selectedProdDate", "result"})
    public void submit(){



        if(generateProdNrVis){
            System.out.println("prva moznost " + prodNr + " " + selectedKs);
            try {
                URL url = new URL(servletPrefix + "prod_nr="
                        + prodNr.trim() + "&kabelsatz_kz=" + selectedKs);
                HttpURLConnection urlConn = null;

                urlConn = (HttpURLConnection) url.openConnection();
                InputStream stream = urlConn.getInputStream();
                result = IOUtils.toString(stream, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(!generateProdNrVis){
            System.out.println("druha moznost " + selectedCustomer.getName() + " " + selectedGroup + " " +
                selectedKs2 + " " + selectedProdDate) ;
            try {
                URL url = new URL(servletPrefix + "kunden_nr="
                        + selectedCustomer.getNumber() + "&gruppe=" + selectedGroup.trim() + "&kabelsatz_kz=" +
                        selectedKs2 + "&liefer_datum=" + selectedProdDate);
                HttpURLConnection urlConn = null;

                urlConn = (HttpURLConnection) url.openConnection();
                InputStream stream = urlConn.getInputStream();
                result = IOUtils.toString(stream, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if(!result.startsWith("ERRROR")){
        generateProdNrVis = true;
        generateBatchVis = false;
        customerList = null;
        groupList = null;
        //kabelsatzList = lwadrKennManager.getAll();
        prodDateList = null;
        prodNr = null;
        selectedKs = null;
        selectedKs2 = null;
        selectedCustomer = null;
        selectedGroup = null;
        selectedProdDate =null;
        }
    }

    @Command
    @NotifyChange({"prodDateList"})
    public void setProdDateList(){
        if(selectedCustomer.getNumber()==323350){prodDateList = lpab62Manager.getPorscheDates();}
        if(selectedCustomer.getNumber()==323357){prodDateList = lpab62Manager.getOsnabruckDates();}
        Collections.sort(prodDateList);
    }

    public Set<String> getKabelsatzList() {
        return kabelsatzList;
    }

    public void setKabelsatzList(Set<String> kabelsatzList) {
        this.kabelsatzList = kabelsatzList;
    }

    public String getSelectedKs() {
        return selectedKs;
    }

    public void setSelectedKs(String selectedKs) {
        this.selectedKs = selectedKs;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public String getSelectedKs2() {
        return selectedKs2;
    }

    public void setSelectedKs2(String selectedKs2) {
        this.selectedKs2 = selectedKs2;
    }

    public String[] getGroupList() {
        return groupList;
    }

    public void setGroupList(String[] groupList) {
        this.groupList = groupList;
    }

    public String getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<Integer> getProdDateList() {
        return prodDateList;
    }

    public void setProdDateList(List<Integer> prodDateList) {
        this.prodDateList = prodDateList;
    }

    public Integer getSelectedProdDate() {
        return selectedProdDate;
    }

    public void setSelectedProdDate(Integer selectedProdDate) {
        this.selectedProdDate = selectedProdDate;
    }

    public boolean isGenerateProdNrVis() {
        return generateProdNrVis;
    }

    public void setGenerateProdNrVis(boolean generateProdNrVis) {
        this.generateProdNrVis = generateProdNrVis;
    }

    public boolean isGenerateBatchVis() {
        return generateBatchVis;
    }

    public void setGenerateBatchVis(boolean generateBatchVis) {
        this.generateBatchVis = generateBatchVis;
    }
}
