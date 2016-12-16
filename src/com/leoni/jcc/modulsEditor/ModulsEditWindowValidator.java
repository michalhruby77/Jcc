package com.leoni.jcc.modulsEditor;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 12.12.2012
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class ModulsEditWindowValidator extends AbstractValidator
    {
    private Map<String, Property> beanProps;

    private static String PLEASE_SELECT_VALUE = "Prosim vyberte hodnotu.";

    public void validate(ValidationContext validationContext)
        {
        beanProps = validationContext.getProperties(validationContext.getProperty().getBase());
        checkNull(validationContext, "prodGruppe");
        checkNull(validationContext, "kabelsatzKz");
        checkNull(validationContext, "ausfuehrung");
        }

    private void checkNull(ValidationContext ctx, String propertyName)
        {
        if (beanProps.get(propertyName).getValue() == null)
            {
            addInvalidMessage(ctx, propertyName, PLEASE_SELECT_VALUE);
            }
        }
    }
