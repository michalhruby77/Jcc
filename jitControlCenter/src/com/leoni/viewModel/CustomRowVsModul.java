package com.leoni.viewModel;

import com.leoni.data.models.VsModulyWrm;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.12.2014
 * Time: 8:33
 * To change this template use File | Settings | File Templates.
 */
public class CustomRowVsModul extends Row {

    private VsModulyWrm vsModul;
    private Textbox sachNrLiefTb;
    private Textbox vodicTb;
    private Textbox prierezTb;
    private Textbox popisTb;

    CustomRowVsModul(String sachNrLief, String vodic, Double prierez, String popis){
        sachNrLiefTb = new Textbox(sachNrLief);
        vodicTb = new Textbox(vodic);
        prierezTb = new Textbox(String.valueOf(prierez));
        popisTb = new Textbox(popis);
        appendChild(sachNrLiefTb);
        appendChild(vodicTb);
        appendChild(prierezTb);
        appendChild(popisTb);
    }

    public String getModuls()
    {return sachNrLiefTb.getValue();}

    public String getVodic()
        {return vodicTb.getValue();}

    public String gtPrierez()
    {return prierezTb.getValue();}

    public String getPopis()
    {return popisTb.getValue();}
}
