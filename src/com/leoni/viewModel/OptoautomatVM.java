package com.leoni.viewModel;

import com.leoni.data.dto.RelaisBoxDTO;
import com.leoni.data.dto.RelaisZoneDTO;
import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.manager.StoffManager;
import com.leoni.data.models.Stoff;
import com.leoni.util.StringParser;
import org.apache.commons.io.IOUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by cigi1001 on 27. 11. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class OptoautomatVM {

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private StoffManager stoffManager;

    @WireVariable
    protected Properties adminProps;

    private String harnessScan = "20s1274670";//nullaaa;
    private String harness;
    private String servletPrefix;
    private Map<String, byte[]> vorneMap = new HashMap<>();
    private Map<String, byte[]> hintenMap = new HashMap<>();
    private Map<String, byte[]> linksMap = new HashMap<>();
    private Map<String, byte[]> rechtsMap = new HashMap<>();
    private byte[] test;

    @AfterCompose
    public void doAfterCompose()
    {
        /*File imgPath = new File("C:/test.jpg");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        test = data.getData();
*/
        servletPrefix = adminProps.getProperty("optoautomat.servletUrl");
    }


    @Command
    @NotifyChange({"harnessScan","relaisBoxDTO", "vorneMap", "hintenMap", "linksMap", "rechtsMap", "harness", "vorneMapObrazok"})
    public void submit() throws IOException {

      if(harnessScan != null && !harnessScan.isEmpty() && harnessScan.toLowerCase().startsWith("20s"))
      {



        String result = null;
        String substring = harnessScan.trim().substring(0, 3);
        String prodNr = harnessScan.trim().substring(3);
        String kskz = "";
        if (substring.toLowerCase().equals("20s")) kskz = "F";

          if (!lpab62Manager.findByProdnrAndKabelsatz(prodNr, kskz).isEmpty() || true){
              harness = prodNr + " " + kskz;
              /*try {
                  URL url = new URL(servletPrefix + "prod_nr="
                          + harnessScan.trim());
                  HttpURLConnection urlConn = null;

                  urlConn = (HttpURLConnection) url.openConnection();
                  InputStream stream = urlConn.getInputStream();
                  result = IOUtils.toString(stream, "UTF-8");
              } catch (IOException e) {
                  Messagebox.show("Servlet nedostupny!", "Error", Messagebox.OK, Messagebox.ERROR);
                  e.printStackTrace();
              }*/

              try {
                  byte[] encoded = Files.readAllBytes(Paths.get("C:/miso/test.txt"));
                  result = new String(encoded, "UTF-8");
              } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              getData(StringParser.parseXML(result));
              harnessScan = "";
          }
          else Messagebox.show("Kablovka neexistuje v databaze (Lpab62)!", "Error", Messagebox.OK, Messagebox.ERROR);

      }
        else Messagebox.show("Kablovka bola zle naskenovana!", "Error", Messagebox.OK, Messagebox.ERROR);
    }

    public void getData(RelaisBoxDTO relaisBoxDTO){
        for (RelaisZoneDTO zone : relaisBoxDTO.getRelaisZoneDTOList()){
            if (zone.getNazov().equals("relais_treager1_vorne")){
                for (String key : zone.getPositionMap().keySet()) {
                    String stoffNr = zone.getPositionMap().get(key);
                    if (stoffNr != null) {
                        List<Stoff> stoffList = stoffManager.findByStoffNr(stoffNr);
                        if (!stoffList.isEmpty()){
                            vorneMap.put(key,stoffList.get(0).getObrazok());
                        }
                    }
                }
            }
            if (zone.getNazov().equals("relais_treager2_hinten")){
                for (String key : zone.getPositionMap().keySet()) {
                    String stoffNr = zone.getPositionMap().get(key);
                    if (stoffNr != null) {
                        List<Stoff> stoffList = stoffManager.findByStoffNr(stoffNr);
                        if (!stoffList.isEmpty()){
                            hintenMap.put(key,stoffList.get(0).getObrazok());
                        }
                    }
                }
            }
            if (zone.getNazov().equals("sicherungs_dose_links")){
                for (String key : zone.getPositionMap().keySet()) {
                    String stoffNr = zone.getPositionMap().get(key);
                    if (stoffNr != null) {
                        List<Stoff> stoffList = stoffManager.findByStoffNr(stoffNr);
                        if (!stoffList.isEmpty()){
                            linksMap.put(key,stoffList.get(0).getObrazok());
                        }
                    }
                }
            }
            if (zone.getNazov().equals("sicherungs_dose_rechts")){
                for (String key : zone.getPositionMap().keySet()) {
                    String stoffNr = zone.getPositionMap().get(key);
                    if (stoffNr != null) {
                        List<Stoff> stoffList = stoffManager.findByStoffNr(stoffNr);
                        if (!stoffList.isEmpty()){
                            rechtsMap.put(key,stoffList.get(0).getObrazok());
                        }
                    }
                }
            }
        }
    }

    public String getHarnessScan() {
        return harnessScan;
    }

    public void setHarnessScan(String harnessScan) {
        this.harnessScan = harnessScan;
    }

    public Map<String, byte[]> getVorneMap() {
        return vorneMap;
    }

    public void setVorneMap(Map<String, byte[]> vorneMap) {
        this.vorneMap = vorneMap;
    }

    public Map<String, byte[]> getHintenMap() {
        return hintenMap;
    }

    public void setHintenMap(Map<String, byte[]> hintenMap) {
        this.hintenMap = hintenMap;
    }

    public Map<String, byte[]> getLinksMap() {
        return linksMap;
    }

    public void setLinksMap(Map<String, byte[]> linksMap) {
        this.linksMap = linksMap;
    }

    public Map<String, byte[]> getRechtsMap() {
        return rechtsMap;
    }

    public void setRechtsMap(Map<String, byte[]> rechtsMap) {
        this.rechtsMap = rechtsMap;
    }

    public String getHarness() {
        return harness;
    }

    public void setHarness(String harness) {
        this.harness = harness;
    }

    public byte[] getTest() {
        return test;
    }

    public void setTest(byte[] test) {
        this.test = test;
    }


}
