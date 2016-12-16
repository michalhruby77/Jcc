package com.leoni.viewModel;

import com.leoni.data.manager.ColorManager;
import com.leoni.data.models.Color;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:46
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ColorViewModel {
    @WireVariable
    private ColorManager colorManager;

    private List<Color> colorList = new ArrayList<Color>();
    private Color selectedColor;
    private String name;
    private String rgbValue;
    private Color colorToDelete;
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        colorList=colorManager.getAll();
    }

    @Command
    @NotifyChange({"colorList"})
    public void deleteColor(@BindingParam("color") Color myColor)
    {
        colorToDelete=myColor;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            colorManager.delete(colorToDelete);
                            colorList = colorManager.getAll();
                            BindUtils.postNotifyChange(null, null, ColorViewModel.this, "colorList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedColor"})
    public void saveColor(@ContextParam(ContextType.VIEW) Component comp,
                         @BindingParam("selectedData")Color editedColor) {
        colorManager.saveEditedColor(editedColor);
        colorList=colorManager.getAll();
        selectedColor = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"colorList","name","rgbValue"})
    public void generateNewColor() {
                            colorManager.addNewColor(name,rgbValue);
                            colorList=colorManager.getAll();
                            name=null;
                            rgbValue=null;
                            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
                }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRgbValue() {
        return rgbValue;
    }

    public void setRgbValue(String rgbValue) {
        this.rgbValue = rgbValue;
    }

    public Color getColorToDelete() {
        return colorToDelete;
    }

    public void setColorToDelete(Color colorToDelete) {
        this.colorToDelete = colorToDelete;
    }
}
