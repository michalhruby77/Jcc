package com.leoni.viewModel;

import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.11.2014
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DataGeneratorVM {

    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private String timeFrom;
    private String timeTo;
    private String result;
    private String dateStringFrom;
    private String dateStringTo;
    private String servletPrefix;

    @WireVariable
    protected Properties adminProps;


    @Init
    public void init(){
        servletPrefix = adminProps.getProperty("dataGenerator.StationsServlet");
    }

    @Command
    @NotifyChange({"result","timeFrom","timeTo","dateFrom","dateTo"})
    public void submit()
    {   if(timeFrom!=null&&timeTo!=null){

            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            dateStringFrom = df.format(dateFrom);
            dateStringTo = df.format(dateTo);
            System.out.println(servletPrefix+"datefrom= "+dateStringFrom+timeFrom.trim()+" dateto= "+dateStringTo+timeTo.trim());
            try {
                URL url = new URL(servletPrefix + "datefrom=" +
                        dateStringFrom+timeFrom.trim()+"&dateto="+dateStringTo+timeTo.trim());
                HttpURLConnection urlConn = null;
                urlConn = (HttpURLConnection) url.openConnection();
                InputStream stream = urlConn.getInputStream();
                result = IOUtils.toString(stream, "UTF-8");
                timeFrom=null;
                timeTo=null;
                dateFrom= new Date();
                dateTo= new Date();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        else{ Messagebox.show("Zle vyplnene udaje!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }


    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDateStringFrom() {
        return dateStringFrom;
    }

    public void setDateStringFrom(String dateStringFrom) {
        this.dateStringFrom = dateStringFrom;
    }

    public String getDateStringTo() {
        return dateStringTo;
    }

    public void setDateStringTo(String dateStringTo) {
        this.dateStringTo = dateStringTo;
    }
}
