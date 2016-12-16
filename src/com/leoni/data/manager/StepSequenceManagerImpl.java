package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.BandType;
import com.leoni.data.models.WorkplaceStepsSequence;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.8.2015
 * Time: 7:06
 * To change this template use File | Settings | File Templates.
 */
@Service("stepSequenceManager")
public class StepSequenceManagerImpl  extends GenericManagerImpl<WorkplaceStepsSequence> implements StepSequenceManager{
    @Override
    public List<WorkplaceStepsSequence> getBandTypeStepSequence(BandType bandType) {
        CriteriaAppender[] criteriaAppenders = {new Equal("workplace.band", bandType)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        criteria.createAlias("workplace", "workplace");

        return super.appendCriteria(criteria, criteriaAppenders);
    }

}
