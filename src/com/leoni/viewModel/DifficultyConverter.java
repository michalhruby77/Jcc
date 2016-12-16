package com.leoni.viewModel;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zul.Label;


/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 14.10.2015
 * Time: 9:51
 * To change this template use File | Settings | File Templates.
 */
public class DifficultyConverter  implements Converter<String, Integer, Label> {


    @Override
    public String coerceToUi(Integer diff, Label label, BindContext bindContext) {
        if (diff != null){
            if (diff == 1) {return "Lahky";}
            if (diff == 2) {return "Tazky";}
        }
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer coerceToBean(String s, Label label, BindContext bindContext) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
