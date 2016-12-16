package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.ClipPosition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2015
 * Time: 8:57
 * To change this template use File | Settings | File Templates.
 */
@Service("clipPositionManager")
public class ClipPositionManagerImpl  extends GenericManagerImpl<ClipPosition> implements ClipPositionManager{
    @Override
    public ClipPosition saveEditedClipPosition(ClipPosition clipPosition) {
        return saveOrUpdate(clipPosition);
    }

    @Override
    public ClipPosition addNewClipPosition(String idClip, String boardTyp, String xAxis, String yAxis) {
        ClipPosition clipPosition = new ClipPosition();
        clipPosition.setIdClip(idClip);
        clipPosition.setBoardTyp(boardTyp);
        clipPosition.setxAxis(xAxis);
        clipPosition.setyAxis(yAxis);
        return create(clipPosition);
    }

    @Override
    public List<ClipPosition> findBy(String idClip, String boardTyp) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!idClip.equals("")){caList.add(new Like("idClip", "%"+idClip.trim()+"%"));}
        if(!boardTyp.equals("")){caList.add(new Like("boardTyp", "%"+boardTyp.trim()+"%"));}
        return find(caList);
    }
}
