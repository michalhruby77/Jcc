package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.ClipStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2015
 * Time: 8:57
 * To change this template use File | Settings | File Templates.
 */
@Service("clipStatusManager")
public class ClipStatusManagerImpl extends GenericManagerImpl<ClipStatus> implements ClipStatusManager {
    @Override
    public ClipStatus saveEditedClipStatus(ClipStatus clipStatus) {
        return saveOrUpdate(clipStatus);
    }

    @Override
    public ClipStatus addNewClipStatus(String idSwitch, String idClip, boolean status, String boardTyp, String boardId) {
        ClipStatus clipStatus = new ClipStatus();
        clipStatus.setIdSwitch(idSwitch);
        clipStatus.setIdClip(idClip);
        clipStatus.setStatus(status);
        clipStatus.setBoardTyp(boardTyp);
        clipStatus.setBoardId(boardId);
        return create(clipStatus);
    }

    @Override
    public List<ClipStatus> findByIdSwitch(String idSwitch) {
        CriteriaAppender[] criteriaAppenders = {new Equal("idSwitch", idSwitch)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    public List<ClipStatus> findBy(String idSwitch, String idClip, String boardTyp, String boardId) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!idSwitch.equals("")){caList.add(new Equal("idSwitch", idSwitch.toUpperCase().trim()));}
        if(!idClip.equals("")){caList.add(new Like("idClip", "%"+idClip.trim()+"%"));}
        if(!boardTyp.equals("")){caList.add(new Like("boardTyp", "%"+boardTyp.trim()+"%"));}
        if(!boardId.equals("")){caList.add(new Like("boardId", "%"+boardId.trim()+"%"));}
        return find(caList);
    }
}
