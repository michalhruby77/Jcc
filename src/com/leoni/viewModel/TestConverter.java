package com.leoni.viewModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.image.AImage;
import org.zkoss.zul.Image;

import java.io.IOException;

/**
 * Created by cigi1001 on 29. 11. 2016.
 */
public class TestConverter  implements Converter<AImage, byte[], Image> {

    private Log logger = LogFactory.getLog(TestConverter.class);

    @Override
    public AImage coerceToUi(byte[] beanProp, Image component, BindContext bindContext) {
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

    @Override
    public byte[] coerceToBean(AImage aImage, Image image, BindContext bindContext) {
        return new byte[0];
    }
}
