package com.leoni.jcc.sicherungenRelais;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 23.1.2013
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Service ("sichrungRelaisValidator")
public class SicherungenRelaisValidator extends AbstractValidator
    {

    private static  String VMSG_WRONG_MODUL = "Vložený nesprávny modul.";
    private static  String VMSG_PLEASE_INSERT_VALUE = "Prosím vložte hodnotu.";

    @WireVariable
    @Autowired
    private ModulsManager modulsManager;

    private Map<String, Property> beanProps;

    public void validate(ValidationContext validationContext)
        {
        beanProps = validationContext.getProperties(validationContext.getProperty().getBase());
        checkNull(validationContext, "box");
        checkNull(validationContext, "platz");
        checkNull(validationContext, "wert");

        String sachNrBest = (String) validationContext.getProperties("moduls.sachNrBest")[0].getValue();
        String sachNrLieferant = (String) validationContext.getProperties("moduls.sachNrLieferant")[0].getValue();

        checkModul(validationContext, sachNrBest, sachNrLieferant);
        }

    private void checkNull(ValidationContext ctx, String propertyName)
        {
        if (beanProps.get(propertyName).getValue() == null)
            {
            addInvalidMessage(ctx, propertyName, VMSG_PLEASE_INSERT_VALUE);
            }
        }

    private Moduls checkModul(ValidationContext ctx, String sachNrBest, String sachNrLieferant)
        {
        List<Moduls> modulsList = modulsManager.findBySachNrBestAndSachNrLieferant(sachNrBest, sachNrLieferant);
        if (modulsList.size() != 1)
            {
            addInvalidMessage(ctx, "sachNrBest", VMSG_WRONG_MODUL);
            addInvalidMessage(ctx, "sachNrLieferant", VMSG_WRONG_MODUL);
            }
        return null;
        }
    }
