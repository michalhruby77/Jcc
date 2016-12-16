package com.leoni.jcc.listModel;

import com.leoni.data.manager.GenericManager;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */

@Service ("sicherungenRelaisListModel")
@Scope ("request")
public class SicherungenRelaisModelImpl extends GenericListModelImpl<SicherungenRelais9X1Wrm> implements SicherungenRelaisModel
    {
    @Autowired
    public SicherungenRelaisModelImpl(GenericManager sicherungenRelais9X1WrmManager)
        {
        super(sicherungenRelais9X1WrmManager);
        }
    }
