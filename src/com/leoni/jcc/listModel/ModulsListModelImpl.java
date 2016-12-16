package com.leoni.jcc.listModel;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 10.1.2013
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */

@Service ("modulsListModel")
@Scope("request")
public class ModulsListModelImpl extends GenericListModelImpl<Moduls> implements ModulsListModel
    {
    @Autowired
    public ModulsListModelImpl(ModulsManager modulsManager)
        {
        super(modulsManager);
        }
    }
