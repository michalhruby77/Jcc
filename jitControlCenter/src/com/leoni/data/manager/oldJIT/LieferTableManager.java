package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.oldJIT.LieferTable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.10.2014
 * Time: 12:30
 * To change this template use File | Settings | File Templates.
 */
public interface LieferTableManager  extends GenericManagerOld<LieferTable> {
    public LieferTable searchSendung(String lieferscheinNr);
}
