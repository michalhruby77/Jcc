package com.leoni.jcc.presence;

import com.leoni.jcc.presence.xml.Place;
import com.leoni.jcc.presence.xml.Workplace;
import org.joda.time.Interval;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 6.2.2013
 * Time: 8:02
 * To change this template use File | Settings | File Templates.
 */
public class ComponentFactory
    {
    private static List<String> placeList = Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14", "A15", "A16", "A17", "A18", "A19", "A20", "A21", "A22", "A23", "A24", "A25", "A26", "A27", "A28", "A29", "A30", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14", "B15", "B16", "B17", "B18", "B19", "B20", "B21", "B22", "B23", "B24", "B25", "B26", "B27", "B28", "B29", "B30");
    private static String timeRadioFormat = "HH:mm";
    private static String pcIconResource = "/resource/icon/pc.png";

    public static Radio getShiftTimeIntervalButton(Interval timeInterval)
        {
        Radio r = new Radio(timeInterval.getStart().toString(timeRadioFormat) + " - " + timeInterval.getEnd().toString(timeRadioFormat));
        r.setAttribute(PresenceVM.RADIO_INTERVAL_ATTRIBUTE_NAME, timeInterval.toString());
        return r;
        }

    public static Caption getCaption(String label)
        {
        return getCaption(label, null);
        }

    public static Caption getCaption(String label, String iconSrc)
        {
        Caption caption = new Caption(label,iconSrc);
        caption.setStyle("font-size: 20px; font-weight: bold");
//        caption.setSclass("block");
        return caption;
        }

    public static Groupbox getGroupbox(Caption caption)
        {
        Groupbox gb = new Groupbox();
        gb.appendChild(caption);
        gb.setMold("3d");
        return gb;
        }

    public static Textbox getTextbox(String value)
        {
        Textbox textbox = new Textbox(value);
        textbox.setCols(30);
        return textbox;
        }

    public static Listbox getListbox(List<String> values, String selected)
        {
        Listbox l = new Listbox();
        for (String v : values)
            {
            l.appendItem(v, v);
            if (selected.equals(v))
                {
                l.setSelectedIndex(l.getItemCount() - 1);
                }
            }
        l.setMold("select");

        Listitem li = new Listitem("");
        return l;
        }

    public static enum SEPARATOR_TYPE
        {
            HORIZONTAL,
            VERTICAL
        }

    public static Separator getSeparator(SEPARATOR_TYPE separatorType)
        {
        return new Separator(separatorType.toString().toLowerCase());
        }

    public static Button getButton(String value)
        {
        return new Button(value);
        }

    public static Hlayout getPlaceEditRow(Workplace workplace, Place place)
        {
        Hlayout hlayout = new Hlayout();
        PlaceChangedListener placeChangedListener = new PlaceChangedListener(place);
        Listbox listbox = getListbox(placeList, place.getId());
        listbox.addEventListener("onSelect", placeChangedListener);
        hlayout.appendChild(getSeparator(SEPARATOR_TYPE.VERTICAL));
        hlayout.appendChild(listbox);
        hlayout.appendChild(getSeparator(SEPARATOR_TYPE.VERTICAL));
        Textbox textbox = getTextbox(place.getName());
        textbox.addEventListener("onChange", placeChangedListener);
        hlayout.appendChild(textbox);
        hlayout.appendChild(getSeparator(SEPARATOR_TYPE.VERTICAL));
        Button button = getButton("-");
        button.addEventListener("onClick", new RemovePlaceListener(workplace, place));
        hlayout.appendChild(button);
        hlayout.appendChild(getSeparator(SEPARATOR_TYPE.VERTICAL));
        return hlayout;
        }

    public static Groupbox getWorkplaceGroupbox(Workplace workplace)
        {
        Groupbox groupbox;
        if (workplace.getIcon() != null && "T".equals(workplace.getIcon().toUpperCase()))
            {
            groupbox = getGroupbox(getCaption(workplace.getId() + " - " + workplace.getName(), pcIconResource));
            }
        else
            {
            groupbox = getGroupbox(getCaption(workplace.getId() + " - " + workplace.getName()));
            }
        Vlayout vlayout = new Vlayout();
        for (Place p : workplace.getPlaceList())
            {
            vlayout.appendChild(getPlaceEditRow(workplace, p));
            vlayout.appendChild(getSeparator(SEPARATOR_TYPE.HORIZONTAL));
            }
        groupbox.appendChild(vlayout);
        Vbox buttonBox = new Vbox();
        buttonBox.setWidth("100%");
        buttonBox.setAlign("stretch");
        buttonBox.setPack("center");
        buttonBox.setWidth("100%");
        Button button = getButton("+");
        button.addEventListener("onClick", new AddPlaceListener(workplace));
        buttonBox.appendChild(button);
        groupbox.appendChild(buttonBox);
        return groupbox;
        }
    }

class PlaceChangedListener implements EventListener<Event>
    {
    Place p;

    PlaceChangedListener(Place p)
        {
        this.p = p;
        }

    public void onEvent(Event event) throws Exception
        {
        if (event.getTarget() instanceof Textbox)
            {
            p.setName(((Textbox) event.getTarget()).getValue());
            }
        else if (event.getTarget() instanceof Listbox)
            {
            p.setId(((Listbox) event.getTarget()).getSelectedItem().getValue().toString());
            }
        }
    }

class AddPlaceListener implements EventListener<Event>
    {
    private Workplace workplace;

    AddPlaceListener(Workplace workplace)
        {
        this.workplace = workplace;
        }

    public void onEvent(Event event) throws Exception
        {
        Button button = (Button) event.getTarget();
        Vlayout vlayout = (Vlayout) button.getParent().getPreviousSibling();
        Place place = new Place("", "");
        vlayout.appendChild(ComponentFactory.getPlaceEditRow(workplace, place));
        vlayout.appendChild(ComponentFactory.getSeparator(ComponentFactory.SEPARATOR_TYPE.HORIZONTAL));
        workplace.getPlaceList().add(place);
        }
    }

class RemovePlaceListener implements EventListener<Event>
    {
    private Workplace workplace;
    private Place place;

    RemovePlaceListener(Workplace workplace, Place place)
        {
        this.workplace = workplace;
        this.place = place;
        }

    public void onEvent(Event event) throws Exception
        {
        Button button = (Button) event.getTarget();
        Hlayout inputHlayout = (Hlayout) event.getTarget().getParent();
        Separator separatorHlayout = (Separator) inputHlayout.getNextSibling();
        Vlayout vlayout = (Vlayout) inputHlayout.getParent();

        button.removeEventListener("onClick", this);
        vlayout.removeChild(inputHlayout);
        vlayout.removeChild(separatorHlayout);
        workplace.getPlaceList().remove(place);
        }
    }
