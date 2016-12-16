package com.leoni.data.manager.dab;

import com.leoni.data.models.dab.Prnr;
import com.leoni.data.models.nonDBmodels.PrnrLpab64Obj;
import com.leoni.data.models.nonDBmodels.ReportResultObj;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2015
 * Time: 9:33
 * To change this template use File | Settings | File Templates.
 */
public interface PrnrManager {

    public List<ReportResultObj> getPrnrByProdNrList(List<String> prodnrList);
    public List<Prnr> getPrnrList(Integer prodDate, String ksKz, String customerNr);
    public List<Prnr> getPrnr(String prodNr, String ksKz);
    public Prnr saveOrUpdate(Prnr prnr);
    public List<Prnr> getPrnrList( String ksKz, String datum);
    List<Prnr> getPrnr(Set<String> containerNameSet);
    List<PrnrLpab64Obj> setContainer(List<PrnrLpab64Obj> prnrLpab64ObjList, Set<String> containerSet);
    int lockHarness(List<PrnrLpab64Obj> prnrLpab64ObjList, Set<String> containerSet);
}
