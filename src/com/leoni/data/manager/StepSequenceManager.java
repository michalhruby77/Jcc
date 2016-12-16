package com.leoni.data.manager;

import com.leoni.data.models.BandType;
import com.leoni.data.models.WorkplaceStepsSequence;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.8.2015
 * Time: 7:06
 * To change this template use File | Settings | File Templates.
 */
public interface StepSequenceManager  extends GenericManager<WorkplaceStepsSequence> {

    public List<WorkplaceStepsSequence> getBandTypeStepSequence(BandType bandType);

}
