package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.models.Moduls;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 21.11.2012
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public interface GenericManager<T>
    {
    public int count(List<CriteriaAppender> criteriaAppenderList);

    public List<T> getAll();

    public List<T> getObjectsByModulsId(int id);

    public List<T> getObjectsByLiefNr(String liefNr);

    public List<T> find (CriteriaAppender criteriaAppender);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList, int offset, int limit);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList, int offset, int limit, String orderColumn, boolean orderDirection);

    public List<T> findByForModuls(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch);

    public List<T> findBy(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch);

    public T create(T t);

    public void delete(T id);

    public T saveOrUpdate(T t);

    public Long getCount(String attribute, String date, String ausfuehrung, String kskz, String kundenNr, String prodGruppe);

    public Long getCountAll(String attribute, String date, String ausfuehrung, String kskz, String kundenNr, String prodGruppe);
    }
