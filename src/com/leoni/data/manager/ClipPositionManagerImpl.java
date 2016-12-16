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
        clipPosition.setXAxis(xAxis);
        clipPosition.setYAxis(yAxis);
        return create(clipPosition);
    }

    @Override
    public List<ClipPosition> findBy(String idClip, String boardTyp,  String position) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!idClip.equals("")){caList.add(new Like("idClip", "%"+idClip.trim()+"%"));}
        if(boardTyp!=null&&!boardTyp.equals("")){caList.add(new Like("boardTyp", "%"+boardTyp.trim()+"%"));}
        if(position!=null&&!position.equals("")){caList.add(new Like("xAxis", "%"+position.trim()+"%"));}
        return find(caList);
    }

    @Override
    public List<String> getDistinctBoardType() {
        return (List<String>) getHibernateTemplate().find("select distinct boardTyp from ClipPosition");
    }

    @Override
    public List<String> getDistinctPosition() {
        return (List<String>) getHibernateTemplate().find("select distinct xAxis from ClipPosition");
    }
}
