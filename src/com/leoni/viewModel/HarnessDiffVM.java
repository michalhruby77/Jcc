package com.leoni.viewModel;

import com.leoni.data.manager.CustomerManager;
import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.LwadrKennManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.dab.PrnrManager;
import com.leoni.data.models.Customer;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.LwadrKenn;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.dab.Prnr;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.SmartNotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 14.10.2015
 * Time: 7:59
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HarnessDiffVM {

    @WireVariable
    private CustomerManager customerManager;

    @WireVariable
    private LwadrKennManager lwadrKennManager;

    @WireVariable
    private PrnrManager prnrManager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private ModulsManager modulsManager;


    private String selectedKs;
    private Set<String> kabelsatzList;
    private String prodDate;
    private Customer selectedCustomer;
    private List<Customer> customerList;
    private Integer counter;
    private List<String> updatedProdNrs;

    @AfterCompose
    public void doAfterCompose()
    {
        selectedKs = "F";
        kabelsatzList = new HashSet<String>();
        for (LwadrKenn item : lwadrKennManager.getAll()){
            kabelsatzList.add(item.getKabelsatzKz().trim());
        }
        customerList = customerManager.getAll();

    }

    @Command
    @SmartNotifyChange({"selectedCustomer", "selectedKs","counter","updatedProdNrs","prodDate"})
    public void submit(){
       counter = 0;
       updatedProdNrs = new ArrayList();
       List<Prnr> prnrList = prnrManager.getPrnrList(Integer.valueOf(prodDate), selectedKs, selectedCustomer.getNumber().toString());
       System.out.println(prnrList);
       for(Prnr prnr : prnrList){
          List<Lpab64> lpab64List = lpab64Manager.findByProdnrAndKabelsatz(prnr.getProdNr(), selectedKs);
          if (modulsManager.containsDiffModul(lpab64List)) {
              prnr.setDifficulty(2);
              counter++;
              updatedProdNrs.add(prnr.getProdNr());
              prnrManager.saveOrUpdate(prnr);
              }
       }
       prodDate = null;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
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

    public List<String> getUpdatedProdNrs() {
        return updatedProdNrs;
    }

    public void setUpdatedProdNrs(List<String> updatedProdNrs) {
        this.updatedProdNrs = updatedProdNrs;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}
