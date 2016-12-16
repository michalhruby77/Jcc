package com.leoni.data.manager;

import com.leoni.data.models.Customer;
import com.leoni.data.models.ProdGroup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.9.2014
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
@Service("groupDeriveManager")
public class GroupDeriveManagerImpl extends GenericManagerImpl<ProdGroup> implements GroupDeriveManager{
    public List<ProdGroup> deriveProdGroup(String sachNrBest) {
        List<ProdGroup> prodGroupListCandidates = new ArrayList<ProdGroup>();
        List<ProdGroup> prodGroupList = getAll();
        for (ProdGroup item : prodGroupList){
            Pattern pattern = Pattern.compile(item.getSachNrBest().trim());
            Matcher matcher = pattern.matcher(sachNrBest.trim());

            if(matcher.find()){prodGroupListCandidates.add(item);}
        }
    return prodGroupListCandidates;
    }

    @Override
    public List<ProdGroup> deriveProdGroup(String sachNrBest, Customer customer) {
        List<ProdGroup> prodGroupListCandidates = new ArrayList<ProdGroup>();
        List<ProdGroup> prodGroupList = getAll();
        for (ProdGroup item : prodGroupList){
            Pattern pattern = Pattern.compile(item.getSachNrBest().trim());
            Matcher matcher = pattern.matcher(sachNrBest.trim());
           /* System.out.println("Prod. group: " + item.getSachNrBest().trim());
            System.out.println("Modul: " + sachNrBest);
            System.out.println("Sedi? " + matcher.find());*/
            if(matcher.find() && item.getKundenNr().trim().equals(customer.getNumber().toString())){prodGroupListCandidates.add(item);}
        }
        return prodGroupListCandidates;
    }
}
