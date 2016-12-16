package com.leoni.jcc.presence.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 11.2.2013
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class XmlHandler
    {

    public static Workplaces unmarshall(String in)
        {
        ByteArrayInputStream bais = new ByteArrayInputStream(in.getBytes());
        InputStreamReader isr = null;
        Workplaces w = null;
        try
            {
            isr = new InputStreamReader(bais, "UTF-8");
            Unmarshaller unmarshaller = JAXBContext.newInstance(Workplaces.class).createUnmarshaller();
            w = (Workplaces) unmarshaller.unmarshal(isr);
            }
        catch (JAXBException e)
            {
            e.printStackTrace();
            }
        catch (UnsupportedEncodingException e)
            {
            e.printStackTrace();
            }
        return w;
        }

    public static String marshall(Workplaces w)
        {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
            {
            OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
            Marshaller marshaller = JAXBContext.newInstance(Workplaces.class).createMarshaller();
            marshaller.marshal(w, osw);
            }
        catch (JAXBException e)
            {
            e.printStackTrace();
            }
        catch (UnsupportedEncodingException e)
            {
            e.printStackTrace();
            }
        return baos.toString();
        }
    }
