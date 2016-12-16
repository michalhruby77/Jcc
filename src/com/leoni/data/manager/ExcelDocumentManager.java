package com.leoni.data.manager;


import com.leoni.data.models.*;
import com.leoni.data.models.nonDBmodels.PrnrLpab64Obj;
import com.leoni.data.models.oldJIT.TempProdNr;
import org.zkoss.util.media.Media;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 20.8.2014
 * Time: 8:50
 * To change this template use File | Settings | File Templates.
 */

public interface ExcelDocumentManager {
public Set<String> getSachNrBest(Media media);
public String getProdNr(Media media);
public List<VsModulyWrm> getVsModulsFromExcel(Media media);
public List<VersandModul> getVersandModuls(Media media);
public File printVersandExport(VersandExport versandExport);
public Set<String> getAllColorsFromExcel();
public Map<String,String> getAllVsFromExcel();
//public List<VsModulyWrmNew> getAllVsModulsFromExcel();
public List<ModulClipListWrm> getModulsClipFromExcel(Media media);
public List<Moduls> getModulsFromExcel(Media media);
       File exportPrnrToFile(List<PrnrLpab64Obj> prnrLpab64ObjList);
       String getAusf(Media media);

}
