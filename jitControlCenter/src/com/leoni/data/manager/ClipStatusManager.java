package com.leoni.data.manager;

import com.leoni.data.models.ClipStatus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2015
 * Time: 8:57
 * To change this template use File | Settings | File Templates.
 */
public interface ClipStatusManager  extends GenericManager<ClipStatus>{
    public ClipStatus saveEditedClipStatus(ClipStatus clipStatus);
    public ClipStatus addNewClipStatus(String idSwitch, String idClip, boolean status, String boardTyp, String boardId);
    public List<ClipStatus> findByIdSwitch(String idSwitch);
    public List<ClipStatus> findBy(String idSwitch, String idClip, String boardTyp, String boardId);
}
