package com.leoni.data.manager;

import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.9.2014
 * Time: 8:48
 * To change this template use File | Settings | File Templates.
 */
public interface PdfManager {
   public File createMontageListPdf(Lpab62 harness, List<Lpab64> modulsList);
   public File createVariantScan (String variantName,String scanString, String user, int nrOfPieces);
}
