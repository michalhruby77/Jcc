package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.models.Color;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.Variant;
import com.leoni.data.models.Workplace;
import org.hibernate.criterion.DetachedCriteria;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public interface VariantManager  extends GenericManager<Variant>   {
    public Variant saveEditedVariant(Variant variant, String user);
    public Variant addNewVariant(Workplace workplace, Color color,String name, String description, String scanString,
                                 boolean scanRequired, Integer modulsCount, Set<Moduls> modulsSet, String xmlModuls, String typ, String user);
    public List<Variant> getAll();
    public List<Variant> findBySachNrLieferant(String sachNrLieferant, String workplaceSearch, String descriptionSearch, String nameSearch, String scanStringSearch, String typSearch);
    public List<Variant> getSelected(List<CriteriaAppender> criteriaAppenderList);
    public File exportToFile(List<Variant> variantList);
    public Map<String,List<Variant>> getVariantMap(Set<Integer> modulsSet);
    public List<Variant> getSingleListVariantFromMap(Set<Integer> modulsSet,String key);
    public List<String> getAllTypes();
    public List<Variant> getAllByType(String type, String group, String ausfuehrung);
    public List<Variant> findVariantsWithModul(Moduls modul);
}
