package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.criterion.NotEqual;
import com.leoni.data.models.Workplace;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:28
 * To change this template use File | Settings | File Templates.
 */
@Service("workplaceManager")
public class WorkplaceManagerImpl extends GenericManagerImpl<Workplace> implements WorkplaceManager {

    public Workplace saveEditedWorkplace(Workplace workplace) {
        return saveOrUpdate(workplace);
    }

    public Workplace addNewWorkplace(String name, String bandName, Integer step, String side, String operation, String alias) {
        Workplace workplace = new Workplace();
        workplace.setBandName(bandName);
        workplace.setName(name);
        workplace.setSide(side);
        workplace.setStep(step);
        workplace.setAlias(alias);
        workplace.setOperation(operation);
        return create(workplace);
    }

    public List<Workplace> findBy(String bandNameSearch) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!bandNameSearch.equals("")){caList.add(new Like("bandName", "%"+bandNameSearch+"%"));}
        return find(caList);
    }

    @Override
    public List<Workplace> findStatusNotNull() {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new NotEqual("operation", ""));
        return find(caList);
    }
}
