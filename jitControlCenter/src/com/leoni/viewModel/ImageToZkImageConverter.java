package com.leoni.viewModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.image.AImage;
import org.zkoss.zul.Image;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 17.12.2014
 * Time: 8:54
 * To change this template use File | Settings | File Templates.
 */
public class ImageToZkImageConverter implements Converter<AImage, byte[], Image> {

    private Log logger = LogFactory.getLog(ImageToZkImageConverter.class);

    @Override
    public byte[] coerceToBean(AImage compAttr, Image component, BindContext ctx) {
        logger.debug("Converting the image");
        return compAttr.getByteData();
    }

    @Override
    public AImage coerceToUi(byte[] beanProp, Image component, BindContext ctx) {
        try {
            if (beanProp != null && beanProp.length > 0) {
                AImage im = new AImage("", beanProp);
                component.setContent(im);
                return im;
            }
            logger.debug("Return null => image is empty");
            return null;
        } catch (IOException e) {
            logger.error("Error occured, returning null", e);
            return null;
        }
    }
}
