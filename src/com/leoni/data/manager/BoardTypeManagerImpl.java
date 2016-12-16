package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.BoardType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.8.2015
 * Time: 7:03
 * To change this template use File | Settings | File Templates.
 */
@Service("boardTypeManager")
public class BoardTypeManagerImpl  extends GenericManagerImpl<BoardType> implements BoardTypeManager{
    @Override
    public List<BoardType> find(String name) {
        CriteriaAppender[] criteriaAppenders = {new Equal("name", name)};
        return find(Arrays.asList(criteriaAppenders));
    }
}
