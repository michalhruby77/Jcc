package com.leoni.data.manager;

import com.leoni.data.models.BoardType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.8.2015
 * Time: 7:03
 * To change this template use File | Settings | File Templates.
 */
public interface BoardTypeManager  extends GenericManager<BoardType>{
    public List<BoardType> find(String name);
}
