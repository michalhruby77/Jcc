package com.leoni.data.manager.oldJIT;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.oldJIT.LieferTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.10.2014
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
@Service("lieferTableManager")
public class LieferTableManagerImpl extends GenericManagerOldImpl<LieferTable> implements LieferTableManager {
    @Override
    public LieferTable searchSendung(String lieferscheinNr) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("lieferscheinNr", lieferscheinNr));
        caList.add(new Equal ("dfueSatzart","713"));
        List<LieferTable> lieferTableList = find(caList);
        if (!lieferTableList.isEmpty()) return lieferTableList.get(0);
        else return null;
    }
}
