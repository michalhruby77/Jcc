package com.leoni.viewModel;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 24.8.2015
 * Time: 7:43
 * To change this template use File | Settings | File Templates.
 */
public class DifficultyCbRenderer implements ComboitemRenderer{

        @Override
        public void render(Comboitem comboitem, Object o, int i) throws Exception {
            Integer difficulty = (Integer) o;

            if (difficulty.equals(1)){
            comboitem.setLabel("Lahka");
            comboitem.setValue("Lahka");}
            if (difficulty.equals(2)){
                comboitem.setLabel("Tazka");
                comboitem.setValue("Tazka");}
           /* if (difficulty.equals(3)){
                comboitem.setLabel("Tazka");
                comboitem.setValue("Tazka");}*/
        }

}
