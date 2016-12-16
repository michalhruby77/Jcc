package com.leoni.data.manager;

import com.leoni.data.models.ClipPosition;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2015
 * Time: 8:56
 * To change this template use File | Settings | File Templates.
 */
public interface ClipPositionManager  extends GenericManager<ClipPosition>{
    public ClipPosition saveEditedClipPosition(ClipPosition clipPosition);
    public ClipPosition addNewClipPosition(String idClip, String boardTyp, String xAxis, String yAxis);
    public List<ClipPosition> findBy(String idClip, String boardTyp, String position);
    public List<String> getDistinctBoardType();
    public List<String> getDistinctPosition();
}
